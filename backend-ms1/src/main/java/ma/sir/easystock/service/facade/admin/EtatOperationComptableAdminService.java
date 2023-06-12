package ma.sir.easystock.service.facade.admin;

import java.util.List;
import ma.sir.easystock.bean.core.EtatOperationComptable;
import ma.sir.easystock.dao.criteria.core.EtatOperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.EtatOperationComptableHistoryCriteria;
import ma.sir.easystock.zynerator.service.IService;

import ma.sir.easystock.ws.dto.EtatOperationComptableDto;
import org.springframework.http.HttpEntity;

public interface EtatOperationComptableAdminService extends  IService<EtatOperationComptable,EtatOperationComptableCriteria, EtatOperationComptableHistoryCriteria>  {


    HttpEntity<byte[]> createPdf(EtatOperationComptableDto dto) throws Exception;



    EtatOperationComptable findByCode(String code);

    EtatOperationComptable findByReferenceEntity(EtatOperationComptable etatOperationComptable);
}
