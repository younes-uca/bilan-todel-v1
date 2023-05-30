package ma.sir.easystock.service.facade.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import ma.sir.easystock.bean.core.TauxIs;
import ma.sir.easystock.dao.criteria.core.TauxIsCriteria;
import ma.sir.easystock.dao.criteria.history.TauxIsHistoryCriteria;
import ma.sir.easystock.zynerator.service.IService;

import ma.sir.easystock.ws.dto.TauxIsDto;
import org.springframework.http.HttpEntity;

public interface TauxIsAdminService extends  IService<TauxIs,TauxIsCriteria, TauxIsHistoryCriteria>  {


    HttpEntity<byte[]> createPdf(TauxIsDto dto) throws Exception;


    List<TauxIs> findByDateApplicationMaxIsNull();

    TauxIs findByResultatMaxAndResultatMinAndDateApplicationMax(BigDecimal resultatMax, BigDecimal resultatMin, LocalDateTime date);

    List<TauxIs> findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(LocalDateTime date);

    TauxIs save(TauxIs tauxIs);
}
