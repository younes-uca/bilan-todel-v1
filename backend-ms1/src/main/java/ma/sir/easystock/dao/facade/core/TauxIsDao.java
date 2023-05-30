package ma.sir.easystock.dao.facade.core;

import ma.sir.easystock.zynerator.repository.AbstractRepository;
import ma.sir.easystock.bean.core.TauxIs;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TauxIsDao extends AbstractRepository<TauxIs,Long>  {

    List<TauxIs> findByDateApplicationMaxIsNull();

    TauxIs findByResultatMaxAndResultatMinAndDateApplicationMax(BigDecimal resultatMax, BigDecimal resultatMin, LocalDateTime date);

    List<TauxIs> findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(LocalDateTime date,LocalDateTime datee);


}
