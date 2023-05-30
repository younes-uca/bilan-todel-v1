package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.DeclarationIs;
import ma.sir.easystock.bean.core.Societe;
import ma.sir.easystock.bean.core.TauxIs;
import ma.sir.easystock.bean.history.DeclarationIsHistory;
import ma.sir.easystock.dao.criteria.core.DeclarationIsCriteria;
import ma.sir.easystock.dao.criteria.history.DeclarationIsHistoryCriteria;
import ma.sir.easystock.dao.facade.core.DeclarationIsDao;
import ma.sir.easystock.dao.facade.history.DeclarationIsHistoryDao;
import ma.sir.easystock.dao.specification.core.DeclarationIsSpecification;
import ma.sir.easystock.service.facade.admin.DeclarationIsAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.DeclarationIsDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.ComptableTraitantAdminService ;
import ma.sir.easystock.service.facade.admin.SocieteAdminService ;
import ma.sir.easystock.service.facade.admin.TauxIsAdminService ;
import ma.sir.easystock.service.facade.admin.ComptableValidateurAdminService ;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeclarationIsAdminServiceImpl extends AbstractServiceImpl<DeclarationIs,DeclarationIsHistory, DeclarationIsCriteria, DeclarationIsHistoryCriteria, DeclarationIsDao,
DeclarationIsHistoryDao> implements DeclarationIsAdminService {
    public static final String TEMPLATE = "template/declarationIs.vm";
    public static final String FILE_NAME = "declarationIs.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(DeclarationIsDto dto) throws Exception{
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }

    public void setDernierTrimestreEtAnneePaye(Societe societe, int annee, int trimestre){
        int year,trim;
        if (trimestre==4){
            trim=1;
            year=annee+1;
        }else {
            trim=trimestre+1;
            year=annee;
        }
        if(findBySocieteIceAndAnneeAndTrimistre(societe.getIce(),year,trim)!=null){
            setDernierTrimestreEtAnneePaye(societe,year,trim);
        }else {
            societe.setDernierAnneePayerIs(annee);
            societe.setDernierTrimestrePayerIs(trimestre);
        }
    }
    @Override
    public DeclarationIs save(boolean simuler, DeclarationIs declarationIs) {
        Societe societe = societeService.findById(declarationIs.getSociete().getId());
        BigDecimal resusltat= declarationIs.getTotalProduit().subtract(declarationIs.getTotalCharge());
        List<TauxIs> tauxIss = tauxIsService.findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(getDateMaxDeclaration(declarationIs.getAnnee(),declarationIs.getTrimistre()));
        if(tauxIss.size()==0){
            tauxIss= tauxIsService.findByDateApplicationMaxIsNull();
        }
        BigDecimal montantImpot =BigDecimal.ZERO;
        for (TauxIs taux: tauxIss) {
            if (resusltat.compareTo(taux.getResultatMin()) >= 0 && resusltat.compareTo(taux.getResultatMax()) <= 0){
                montantImpot = resusltat.multiply(taux.getPourcentage().divide(BigDecimal.valueOf(100)));
                declarationIs.setResultatAvantImpot(resusltat);
                declarationIs.setTauxIs(taux);
                declarationIs.setMontantImpot(montantImpot);
                declarationIs.setResultatApresImpot(resusltat.subtract(montantImpot));
                declarationIs.setSociete(societe);
                declarationIs.setDateDeclaration(LocalDateTime.now());
                break;
            }
        }
        if(!simuler){
            setDernierTrimestreEtAnneePaye(declarationIs.getSociete(),declarationIs.getAnnee(),declarationIs.getTrimistre());
            declarationIs=create(declarationIs);
        }
        return declarationIs;
    }

    public LocalDateTime getDateMaxDeclaration(int annee, int trimestre) {
        LocalDateTime dateMax = null;
        if (trimestre == 1) {
            dateMax = LocalDateTime.of(annee, 3, 31, 0, 0);
        }
        if (trimestre == 2) {
            dateMax = LocalDateTime.of(annee, 6, 30, 0, 0);
        }
        if (trimestre == 3) {
            dateMax = LocalDateTime.of(annee, 9, 30, 0, 0);
        }
        if (trimestre == 4) {
            dateMax = LocalDateTime.of(annee, 12, 31, 0, 0);
        }
        return dateMax;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        DeclarationIs declarationIs=findById(id);
        Societe societe=declarationIs.getSociete();
        if(getDateMaxDeclaration(declarationIs.getAnnee(), declarationIs.getTrimistre()).isBefore(getDateMaxDeclaration(societe.getDernierAnneePayerIs(), societe.getDernierTrimestrePayerIs()))|| getDateMaxDeclaration(declarationIs.getAnnee(), declarationIs.getTrimistre()).isEqual(getDateMaxDeclaration(societe.getDernierAnneePayerIs(), societe.getDernierTrimestrePayerIs()))){
            if (declarationIs.getTrimistre()==1){
                societe.setDernierTrimestrePayerIs(4);
                societe.setDernierAnneePayerIs(declarationIs.getAnnee()-1);
            }else {
                societe.setDernierTrimestrePayerIs(declarationIs.getTrimistre()-1);
                societe.setDernierAnneePayerIs(declarationIs.getAnnee());
            }
        }
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(List<DeclarationIs> declarationIss){
        for(DeclarationIs declarationIs :declarationIss){
            deleteById(declarationIs.getId());
        }
    }

    public DeclarationIs findBySocieteIceAndAnneeAndTrimistre(String ice,int annee,int trimistre){ return dao.findBySocieteIceAndAnneeAndTrimistre(ice,annee,trimistre); }
    public DeclarationIs findByReferenceEntity(DeclarationIs t){
        return  dao.findByReference(t.getReference());
    }

    public List<DeclarationIs> findBySocieteId(Long id){
        return dao.findBySocieteId(id);
    }
    public int deleteBySocieteId(Long id){
        return dao.deleteBySocieteId(id);
    }
    public List<DeclarationIs> findByTauxIsId(Long id){
        return dao.findByTauxIsId(id);
    }
    public int deleteByTauxIsId(Long id){
        return dao.deleteByTauxIsId(id);
    }
    public List<DeclarationIs> findByComptableTraitantId(Long id){
        return dao.findByComptableTraitantId(id);
    }
    public int deleteByComptableTraitantId(Long id){
        return dao.deleteByComptableTraitantId(id);
    }
    public List<DeclarationIs> findByComptableValidateurId(Long id){
        return dao.findByComptableValidateurId(id);
    }
    public int deleteByComptableValidateurId(Long id){
        return dao.deleteByComptableValidateurId(id);
    }




    public void configure() {
        super.configure(DeclarationIs.class,DeclarationIsHistory.class, DeclarationIsHistoryCriteria.class, DeclarationIsSpecification.class);
    }

    @Autowired
    private ComptableTraitantAdminService comptableTraitantService ;
    @Autowired
    private SocieteAdminService societeService ;
    @Autowired
    private TauxIsAdminService tauxIsService ;
    @Autowired
    private ComptableValidateurAdminService comptableValidateurService ;
    @Autowired
    private VelocityPdf velocityPdf;

    public DeclarationIsAdminServiceImpl(DeclarationIsDao dao, DeclarationIsHistoryDao historyDao) {
        super(dao, historyDao);
    }

}