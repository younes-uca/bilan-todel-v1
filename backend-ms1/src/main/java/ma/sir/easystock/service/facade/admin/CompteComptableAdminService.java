package ma.sir.easystock.service.facade.admin;

import java.util.List;

import ma.sir.easystock.bean.core.ClassComptable;
import ma.sir.easystock.bean.core.CompteComptable;
import ma.sir.easystock.dao.criteria.core.CompteComptableCriteria;
import ma.sir.easystock.dao.criteria.history.CompteComptableHistoryCriteria;
import ma.sir.easystock.zynerator.service.IService;

import ma.sir.easystock.ws.dto.CompteComptableDto;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CompteComptableAdminService extends  IService<CompteComptable,CompteComptableCriteria, CompteComptableHistoryCriteria>  {



    List<CompteComptable> findBySousClassComptableId(Long id);
    int deleteBySousClassComptableId(Long id);

    void saveToDatabase(MultipartFile file);


    CompteComptable findByCode(String code);

    CompteComptable findByReferenceEntity(CompteComptable compteComptable);
    List<CompteComptable> getCompteComptables();

    HttpEntity<byte[]> createPdf(CompteComptableDto dto) throws Exception;


}
