package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.TauxRetardTva;
import ma.sir.easystock.bean.history.TauxRetardTvaHistory;
import ma.sir.easystock.dao.criteria.core.TauxRetardTvaCriteria;
import ma.sir.easystock.dao.criteria.history.TauxRetardTvaHistoryCriteria;
import ma.sir.easystock.dao.facade.core.TauxRetardTvaDao;
import ma.sir.easystock.dao.facade.history.TauxRetardTvaHistoryDao;
import ma.sir.easystock.dao.specification.core.TauxRetardTvaSpecification;
import ma.sir.easystock.service.facade.admin.TauxRetardTvaAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.TauxRetardTvaDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TauxRetardTvaAdminServiceImpl extends AbstractServiceImpl<TauxRetardTva, TauxRetardTvaHistory, TauxRetardTvaCriteria, TauxRetardTvaHistoryCriteria, TauxRetardTvaDao,
        TauxRetardTvaHistoryDao> implements TauxRetardTvaAdminService {
    public static final String TEMPLATE = "template/tauxRetardTva.vm";
    public static final String FILE_NAME = "tauxRetardTva.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(TauxRetardTvaDto dto) throws Exception {
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }

    @Override
    public TauxRetardTva save(TauxRetardTva tauxRetardTva) {

        TauxRetardTva ancienTauxRetardTva = findByDateApplicationMax(null);
        if (ancienTauxRetardTva == null) {
            tauxRetardTva.setDateApplicationMax(null);
            tauxRetardTva.setDateApplicationMin(LocalDateTime.now());
        } else {

            ancienTauxRetardTva.setDateApplicationMax(LocalDateTime.now());
            tauxRetardTva.setDateApplicationMin(LocalDateTime.now());
            tauxRetardTva.setDateApplicationMax(null);


        }
        return dao.save(tauxRetardTva);
    }

    @Override
    public TauxRetardTva findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(int annee, int trimestre) {
        return dao.findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(annee, trimestre);
    }

    @Override
    public TauxRetardTva findByDateApplicationMax(LocalDateTime date) {
        return dao.findByDateApplicationMax(date);
    }


    public void configure() {
        super.configure(TauxRetardTva.class, TauxRetardTvaHistory.class, TauxRetardTvaHistoryCriteria.class, TauxRetardTvaSpecification.class);
    }

    @Autowired
    private VelocityPdf velocityPdf;

    public TauxRetardTvaAdminServiceImpl(TauxRetardTvaDao dao, TauxRetardTvaHistoryDao historyDao) {
        super(dao, historyDao);
    }

}