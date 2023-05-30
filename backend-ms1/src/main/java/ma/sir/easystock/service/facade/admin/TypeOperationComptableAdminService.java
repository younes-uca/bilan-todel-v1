package ma.sir.easystock.service.facade.admin;

import java.util.List;
import ma.sir.easystock.bean.core.TypeOperationComptable;
import ma.sir.easystock.dao.criteria.core.TypeOperationComptableCriteria;
import ma.sir.easystock.dao.criteria.history.TypeOperationComptableHistoryCriteria;
import ma.sir.easystock.zynerator.service.IService;

import ma.sir.easystock.ws.dto.TypeOperationComptableDto;
import org.springframework.http.HttpEntity;

public interface TypeOperationComptableAdminService extends  IService<TypeOperationComptable,TypeOperationComptableCriteria, TypeOperationComptableHistoryCriteria>  {


    HttpEntity<byte[]> createPdf(TypeOperationComptableDto dto) throws Exception;


}
