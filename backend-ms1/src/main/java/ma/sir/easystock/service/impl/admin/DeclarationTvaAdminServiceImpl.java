package ma.sir.easystock.service.impl.admin;

import ma.sir.easystock.bean.core.DeclarationTva;
import ma.sir.easystock.bean.core.Societe;
import ma.sir.easystock.bean.core.TauxRetardTva;
import ma.sir.easystock.bean.history.DeclarationTvaHistory;
import ma.sir.easystock.dao.criteria.core.DeclarationTvaCriteria;
import ma.sir.easystock.dao.criteria.history.DeclarationTvaHistoryCriteria;
import ma.sir.easystock.dao.facade.core.DeclarationTvaDao;
import ma.sir.easystock.dao.facade.history.DeclarationTvaHistoryDao;
import ma.sir.easystock.dao.specification.core.DeclarationTvaSpecification;
import ma.sir.easystock.service.facade.admin.DeclarationTvaAdminService;
import ma.sir.easystock.zynerator.service.AbstractServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import ma.sir.easystock.zynerator.util.VelocityPdf;
import ma.sir.easystock.ws.dto.DeclarationTvaDto;
import org.springframework.http.HttpEntity;

import org.springframework.beans.factory.annotation.Autowired;


import ma.sir.easystock.service.facade.admin.ComptableTraitantAdminService;
import ma.sir.easystock.service.facade.admin.SocieteAdminService;
import ma.sir.easystock.service.facade.admin.ComptableValidateurAdminService;
import ma.sir.easystock.service.facade.admin.TauxRetardTvaAdminService;

@Service
public class DeclarationTvaAdminServiceImpl extends AbstractServiceImpl<DeclarationTva, DeclarationTvaHistory, DeclarationTvaCriteria, DeclarationTvaHistoryCriteria, DeclarationTvaDao,
        DeclarationTvaHistoryDao> implements DeclarationTvaAdminService {
    public static final String TEMPLATE = "template/declarationTva.vm";
    public static final String FILE_NAME = "declarationTva.pdf";

    @Override
    public HttpEntity<byte[]> createPdf(DeclarationTvaDto dto) throws Exception {
        return velocityPdf.createPdf(FILE_NAME, TEMPLATE, dto);
    }


    public DeclarationTva findByReferenceEntity(DeclarationTva t) {
        return dao.findByReference(t.getReference());
    }

    public List<DeclarationTva> findBySocieteId(Long id) {
        return dao.findBySocieteId(id);
    }

    public int deleteBySocieteId(Long id) {
        return dao.deleteBySocieteId(id);
    }

    public List<DeclarationTva> findByTauxRetardTvaId(Long id) {
        return dao.findByTauxRetardTvaId(id);
    }

    public int deleteByTauxRetardTvaId(Long id) {
        return dao.deleteByTauxRetardTvaId(id);
    }

    public List<DeclarationTva> findByComptableTraitantId(Long id) {
        return dao.findByComptableTraitantId(id);
    }

    public int deleteByComptableTraitantId(Long id) {
        return dao.deleteByComptableTraitantId(id);
    }

    public List<DeclarationTva> findByComptableValidateurId(Long id) {
        return dao.findByComptableValidateurId(id);
    }

    public int deleteByComptableValidateurId(Long id) {
        return dao.deleteByComptableValidateurId(id);
    }


    public void configure() {
        super.configure(DeclarationTva.class, DeclarationTvaHistory.class, DeclarationTvaHistoryCriteria.class, DeclarationTvaSpecification.class);
    }

    @Override
    public DeclarationTva save(boolean simuler, DeclarationTva declarationTva) {
        LocalDateTime dateDeclaration = declarationTva.getDateDeclaration();
        Societe societe = societeService.findById(declarationTva.getSociete().getId());
        Integer annee = declarationTva.getAnnee();
        Integer trimestre = declarationTva.getTrimistre();
        BigDecimal totalTvaCollecte = declarationTva.getTotalTvaCollecte();
        BigDecimal totalTvaDue = declarationTva.getTotalTvaDue();
        BigDecimal differenceTva = totalTvaCollecte.subtract(totalTvaDue);
        BigDecimal montantTva = differenceTva;
        int nbrMoisdeRetard = calculeMoisRetardTrimestrielle(annee, trimestre);
        if (nbrMoisdeRetard > 0) {

            TauxRetardTva tauxRetardTva = tauxRetardTvaService.findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(annee, trimestre);
            if (tauxRetardTva == null) {
                tauxRetardTva = tauxRetardTvaService.findByDateApplicationMax(null);
            }
            declarationTva.setTauxRetardTva(tauxRetardTva);

            BigDecimal montantRetard = tauxRetardTva.getMontant();

            montantTva = differenceTva.add(montantRetard);

        }
        declarationTva.setDateDeclaration(LocalDateTime.now());
        declarationTva.setMontantTva(montantTva);
        declarationTva.setDifferenceTva(differenceTva);

        if (!simuler) {

            return this.create(declarationTva);

        }

        return dao.save(declarationTva);


    }

    public int calculeMoisRetardTrimestrielle(int annee, int trimestre) {
        LocalDateTime dateMax = getDateMax(annee, trimestre);
        if (dateMax == null) {
            return -1;
        } else
            return (int) ChronoUnit.MONTHS.between(dateMax, LocalDateTime.now());
    }

    public LocalDateTime getDateMax(int annee, int trimestre) {
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

    @Autowired
    private ComptableTraitantAdminService comptableTraitantService;
    @Autowired
    private SocieteAdminService societeService;
    @Autowired
    private ComptableValidateurAdminService comptableValidateurService;
    @Autowired
    private TauxRetardTvaAdminService tauxRetardTvaService;
    @Autowired
    private VelocityPdf velocityPdf;

    public DeclarationTvaAdminServiceImpl(DeclarationTvaDao dao, DeclarationTvaHistoryDao historyDao) {
        super(dao, historyDao);
    }

}