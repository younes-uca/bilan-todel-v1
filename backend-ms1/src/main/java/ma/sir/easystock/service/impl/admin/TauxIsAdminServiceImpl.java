package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.TauxIs;
import ma.sir.easystock.bean.history.TauxIsHistory;
import ma.sir.easystock.dao.criteria.core.TauxIsCriteria;
import ma.sir.easystock.dao.criteria.history.TauxIsHistoryCriteria;
import ma.sir.easystock.dao.facade.core.TauxIsDao;
import ma.sir.easystock.dao.facade.history.TauxIsHistoryDao;
import ma.sir.easystock.dao.specification.core.TauxIsSpecification;
import ma.sir.easystock.service.facade.admin.TauxIsAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.TauxIsDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TauxIsAdminServiceImpl extends AbstractServiceImpl<TauxIs,TauxIsHistory, TauxIsCriteria, TauxIsHistoryCriteria, TauxIsDao,
TauxIsHistoryDao> implements TauxIsAdminService {
    public static final String TEMPLATE = "template/tauxIs.vm";
    public static final String FILE_NAME = "tauxIs.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(TauxIsDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }

    @Override
    public List<TauxIs> findByDateApplicationMaxIsNull(){
        return dao.findByDateApplicationMaxIsNull();
    };

    @Override
    public TauxIs findByResultatMaxAndResultatMinAndDateApplicationMax(BigDecimal resultatMax, BigDecimal resultatMin, LocalDateTime date){
        return dao.findByResultatMaxAndResultatMinAndDateApplicationMax(resultatMax,resultatMin,date);
    };

    @Override
    public List<TauxIs> findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(LocalDateTime date){
        return dao.findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(date,date);
    }

    @Override
    public TauxIs save(TauxIs tauxIs) {
        TauxIs ancienTaux = findByResultatMaxAndResultatMinAndDateApplicationMax(tauxIs.getResultatMax(),tauxIs.getResultatMin(),null);
        if(ancienTaux==null){
            tauxIs.setDateApplicationMin(LocalDateTime.now());
            tauxIs.setDateApplicationMax(null);
        }else {
            ancienTaux.setDateApplicationMax(LocalDateTime.now());
            tauxIs.setDateApplicationMin(LocalDateTime.now());
            tauxIs.setDateApplicationMax(null);
        }
        return dao.save(tauxIs);
    }

    public void configure() {
        super.configure(TauxIs.class,TauxIsHistory.class, TauxIsHistoryCriteria.class, TauxIsSpecification.class);
    }

    @Autowired
    private VelocityPdf velocityPdf;

    public TauxIsAdminServiceImpl(TauxIsDao dao, TauxIsHistoryDao historyDao) {
        super(dao, historyDao);
    }

}