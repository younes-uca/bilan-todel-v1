package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.OperationComptable;
import ma.sir.easystock.bean.history.OperationComptableHistory;
import ma.sir.easystock.dao.criteria.core.OperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.OperationComptableHistoryCriteria;
import ma.sir.easystock.dao.facade.core.OperationComptableDao;
import ma.sir.easystock.dao.facade.history.OperationComptableHistoryDao;
import ma.sir.easystock.dao.specification.core.OperationComptableSpecification;
import ma.sir.easystock.service.facade.admin.OperationComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import ma.sir.easystock.zynerator.util.ListUtil;
import org.springframework.stereotype.Service;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.OperationComptableDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.SocieteAdminService ;
import ma.sir.easystock.service.facade.admin.EtatOperationComptableAdminService ;
import ma.sir.easystock.service.facade.admin.TypeOperationComptableAdminService ;
import ma.sir.easystock.service.facade.admin.CompteComptableAdminService ;


import java.util.List;
@Service
public class OperationComptableAdminServiceImpl extends AbstractServiceImpl<OperationComptable,OperationComptableHistory, OperationComptableCriteria, OperationComptableHistoryCriteria, OperationComptableDao,
OperationComptableHistoryDao> implements OperationComptableAdminService {
    public static final String TEMPLATE = "template/operationComptable.vm";
    public static final String FILE_NAME = "operationComptable.pdf";
public static final List<Attribute> ATTRIBUTES = new ArrayList();
    static{
    ATTRIBUTES.add(new Attribute("id","Long"));
    ATTRIBUTES.add(new Attribute("libelle"));
    ATTRIBUTES.add(new Attribute("annee","Integer"));
    ATTRIBUTES.add(new Attribute("mois","Integer"));
    ATTRIBUTES.add(new Attribute("montant","BigDecimal"));
    }

    @Override
    public HttpEntity<byte[]> createPdf(OperationComptableDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }



    public List<OperationComptable> findBySocieteId(Long id){
        return dao.findBySocieteId(id);
    }
    public int deleteBySocieteId(Long id){
        return dao.deleteBySocieteId(id);
    }
    public List<OperationComptable> findByCompteComptableId(Long id){
        return dao.findByCompteComptableId(id);
    }
    public int deleteByCompteComptableId(Long id){
        return dao.deleteByCompteComptableId(id);
    }
    public List<OperationComptable> findByTypeOperationComptableId(Long id){
        return dao.findByTypeOperationComptableId(id);
    }
    public int deleteByTypeOperationComptableId(Long id){
        return dao.deleteByTypeOperationComptableId(id);
    }
    public List<OperationComptable> findByEtatOperationComptableId(Long id){
        return dao.findByEtatOperationComptableId(id);
    }
    public int deleteByEtatOperationComptableId(Long id){
        return dao.deleteByEtatOperationComptableId(id);
    }


    @Override
    protected List<Attribute> getAttributes() {
        return ATTRIBUTES;
    }


    public void configure() {
        super.configure(OperationComptable.class,OperationComptableHistory.class, OperationComptableHistoryCriteria.class, OperationComptableSpecification.class);
    }

    @Autowired
    private SocieteAdminService societeService ;
    @Autowired
    private EtatOperationComptableAdminService etatOperationComptableService ;
    @Autowired
    private TypeOperationComptableAdminService typeOperationComptableService ;
    @Autowired
    private CompteComptableAdminService compteComptableService ;
    @Autowired
    private VelocityPdf velocityPdf;

    public OperationComptableAdminServiceImpl(OperationComptableDao dao, OperationComptableHistoryDao historyDao) {
        super(dao, historyDao);
    }

}