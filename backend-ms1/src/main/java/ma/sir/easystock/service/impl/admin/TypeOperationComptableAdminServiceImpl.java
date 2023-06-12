package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.TypeOperationComptable;
import ma.sir.easystock.bean.history.TypeOperationComptableHistory;
import ma.sir.easystock.dao.criteria.core.TypeOperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.TypeOperationComptableHistoryCriteria;
import ma.sir.easystock.dao.facade.core.TypeOperationComptableDao;
import ma.sir.easystock.dao.facade.history.TypeOperationComptableHistoryDao;
import ma.sir.easystock.dao.specification.core.TypeOperationComptableSpecification;
import ma.sir.easystock.service.facade.admin.TypeOperationComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.TypeOperationComptableDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TypeOperationComptableAdminServiceImpl extends AbstractServiceImpl<TypeOperationComptable,TypeOperationComptableHistory, TypeOperationComptableCriteria, TypeOperationComptableHistoryCriteria, TypeOperationComptableDao,
TypeOperationComptableHistoryDao> implements TypeOperationComptableAdminService {
    public static final String TEMPLATE = "template/typeOperationComptable.vm";
    public static final String FILE_NAME = "typeOperationComptable.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(TypeOperationComptableDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }



    @Override
    public TypeOperationComptable findByCode(String code) {
        return dao.findByCode(code) ;
    }
    @Override
    public TypeOperationComptable findByReferenceEntity(TypeOperationComptable typeOperationComptable) {
        return findByCode(typeOperationComptable.getCode());
    }



    public void configure() {
        super.configure(TypeOperationComptable.class,TypeOperationComptableHistory.class, TypeOperationComptableHistoryCriteria.class, TypeOperationComptableSpecification.class);
    }

    @Autowired
    private VelocityPdf velocityPdf;

    public TypeOperationComptableAdminServiceImpl(TypeOperationComptableDao dao, TypeOperationComptableHistoryDao historyDao) {
        super(dao, historyDao);
    }

}