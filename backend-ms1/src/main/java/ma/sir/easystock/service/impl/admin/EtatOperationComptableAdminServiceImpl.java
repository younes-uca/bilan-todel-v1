package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.EtatOperationComptable;
import ma.sir.easystock.bean.history.EtatOperationComptableHistory;
import ma.sir.easystock.dao.criteria.core.EtatOperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.EtatOperationComptableHistoryCriteria;
import ma.sir.easystock.dao.facade.core.EtatOperationComptableDao;
import ma.sir.easystock.dao.facade.history.EtatOperationComptableHistoryDao;
import ma.sir.easystock.dao.specification.core.EtatOperationComptableSpecification;
import ma.sir.easystock.service.facade.admin.EtatOperationComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.EtatOperationComptableDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EtatOperationComptableAdminServiceImpl extends AbstractServiceImpl<EtatOperationComptable,EtatOperationComptableHistory, EtatOperationComptableCriteria, EtatOperationComptableHistoryCriteria, EtatOperationComptableDao,
EtatOperationComptableHistoryDao> implements EtatOperationComptableAdminService {
    public static final String TEMPLATE = "template/etatOperationComptable.vm";
    public static final String FILE_NAME = "etatOperationComptable.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(EtatOperationComptableDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }


    @Override
    public EtatOperationComptable findByCode(String code) {
return dao.findByCode(code);
    }
    @Override
    public EtatOperationComptable findByReferenceEntity(EtatOperationComptable etatOperationComptable) {
        return findByCode(etatOperationComptable.getCode());
    }





    public void configure() {
        super.configure(EtatOperationComptable.class,EtatOperationComptableHistory.class, EtatOperationComptableHistoryCriteria.class, EtatOperationComptableSpecification.class);
    }

    @Autowired
    private VelocityPdf velocityPdf;

    public EtatOperationComptableAdminServiceImpl(EtatOperationComptableDao dao, EtatOperationComptableHistoryDao historyDao) {
        super(dao, historyDao);
    }

}