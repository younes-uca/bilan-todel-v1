package ma.sir.easystock.service.facade.admin;

import java.util.List;
import ma.sir.easystock.bean.core.EtatBilan;
import ma.sir.easystock.dao.criteria.core.EtatBilanCriteria;
import ma.sir.easystock.dao.criteria.history.EtatBilanHistoryCriteria;
import ma.sir.easystock.zynerator.service.IService;

import ma.sir.easystock.ws.dto.EtatBilanDto;
import org.springframework.http.HttpEntity;

public interface EtatBilanAdminService extends  IService<EtatBilan,EtatBilanCriteria, EtatBilanHistoryCriteria>  {


    HttpEntity<byte[]> createPdf(EtatBilanDto dto) throws Exception;


}
