package ma.sir.easystock.dao.facade.core;

import ma.sir.easystock.zynerator.repository.AbstractRepository;
import ma.sir.easystock.bean.core.TauxRetardTva;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TauxRetardTvaDao extends AbstractRepository<TauxRetardTva,Long>  {


    TauxRetardTva findByDateApplicationMaxGreaterThanEqualAndDateApplicationMinLessThanEqual(LocalDateTime date,LocalDateTime datee);

    TauxRetardTva findByDateApplicationMax(LocalDateTime date);
}
