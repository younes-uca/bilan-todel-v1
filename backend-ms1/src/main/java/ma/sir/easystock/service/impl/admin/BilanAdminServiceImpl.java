package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.Bilan;
import ma.sir.easystock.bean.history.BilanHistory;
import ma.sir.easystock.dao.criteria.core.BilanCriteria;
import ma.sir.easystock.dao.criteria.history.BilanHistoryCriteria;
import ma.sir.easystock.dao.facade.core.BilanDao;
import ma.sir.easystock.dao.facade.history.BilanHistoryDao;
import ma.sir.easystock.dao.specification.core.BilanSpecification;
import ma.sir.easystock.service.facade.admin.BilanAdminService;
import ma.sir.easystock.service.facade.admin.OperationComptableAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import ma.sir.easystock.zynerator.service.Attribute;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.BilanDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.SocieteAdminService ;
import ma.sir.easystock.service.facade.admin.EtatBilanAdminService ;

@Service
public class BilanAdminServiceImpl extends AbstractServiceImpl<Bilan,BilanHistory, BilanCriteria, BilanHistoryCriteria, BilanDao,
BilanHistoryDao> implements BilanAdminService {
    public static final String TEMPLATE = "template/bilan.vm";
    public static final String FILE_NAME = "bilan.pdf";
public static final List<Attribute> ATTRIBUTES = new ArrayList();
    static{
    ATTRIBUTES.add(new Attribute("id","Long"));
    ATTRIBUTES.add(new Attribute("ref"));
    ATTRIBUTES.add(new Attribute("annee","Integer"));
    ATTRIBUTES.add(new Attribute("immobilisationsIncorporelles","BigDecimal"));
    ATTRIBUTES.add(new Attribute("immobilisationsCorporelles","BigDecimal"));
    ATTRIBUTES.add(new Attribute("immobilisationsFinancieres","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalActifImmobolise","BigDecimal"));
    ATTRIBUTES.add(new Attribute("stocks","BigDecimal"));
    ATTRIBUTES.add(new Attribute("creancesClients","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalActifCirculant","BigDecimal"));
    ATTRIBUTES.add(new Attribute("depotEnBanque","BigDecimal"));
    ATTRIBUTES.add(new Attribute("avoirEnCaisse","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalTresorieActif","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalActif","BigDecimal"));
    ATTRIBUTES.add(new Attribute("capitalPersonnel","BigDecimal"));
    ATTRIBUTES.add(new Attribute("emprunts","BigDecimal"));
    ATTRIBUTES.add(new Attribute("resultat","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalFinnancementPermanant","BigDecimal"));
    ATTRIBUTES.add(new Attribute("dettesFournisseurs","BigDecimal"));
    ATTRIBUTES.add(new Attribute("autresDettesCirculantes","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalPassifCirculant","BigDecimal"));
    ATTRIBUTES.add(new Attribute("totalPassif","BigDecimal"));
    }

    @Override
    public HttpEntity<byte[]> createPdf(BilanDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }

    @Override
    public Bilan save(Bilan bilan){
        bilan=operationComptableAdminService.createBilan(bilan);
        bilan.setTotalActifImmobolise(bilan.getImmobilisationsCorporelles().add(bilan.getImmobilisationsIncorporelles()).add(bilan.getImmobilisationsFinancieres()));
        bilan.setTotalActifCirculant(bilan.getStocks().add(bilan.getCreancesClients()));
        bilan.setTotalTresorieActif(bilan.getDepotEnBanque().add(bilan.getAvoirEnCaisse()));
        bilan.setTotalFinnancementPermanant(bilan.getCapitalPersonnel().add(bilan.getEmprunts()).add(bilan.getResultat()));
        bilan.setTotalPassifCirculant(bilan.getDettesFournisseurs().add(bilan.getAutresDettesCirculantes()));
        bilan.setTotalActif(bilan.getTotalActifImmobolise().add(bilan.getTotalActifCirculant()).add(bilan.getTotalTresorieActif()));
        bilan.setTotalPassif(bilan.getTotalFinnancementPermanant().add(bilan.getTotalPassifCirculant()));
        return dao.save(bilan);
    }

    public Bilan findByReferenceEntity(Bilan t){
        return  dao.findByRef(t.getRef());
    }

    public List<Bilan> findBySocieteId(Long id){
        return dao.findBySocieteId(id);
    }
    public int deleteBySocieteId(Long id){
        return dao.deleteBySocieteId(id);
    }
    public List<Bilan> findByEtatBilanId(Long id){
        return dao.findByEtatBilanId(id);
    }
    public int deleteByEtatBilanId(Long id){
        return dao.deleteByEtatBilanId(id);
    }


    @Override
    protected List<Attribute> getAttributes() {
        return ATTRIBUTES;
    }


    public void configure() {
        super.configure(Bilan.class,BilanHistory.class, BilanHistoryCriteria.class, BilanSpecification.class);
    }

    @Autowired
    private OperationComptableAdminService operationComptableAdminService ;
    @Autowired
    private SocieteAdminService societeService ;
    @Autowired
    private EtatBilanAdminService etatBilanService ;
    @Autowired
    private VelocityPdf velocityPdf;

    public BilanAdminServiceImpl(BilanDao dao, BilanHistoryDao historyDao) {
        super(dao, historyDao);
    }

}