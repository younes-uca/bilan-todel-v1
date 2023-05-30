package ma.sir.easystock.dao.facade.core;

import org.springframework.data.jpa.repository.Query;
import ma.sir.easystock.zynerator.repository.AbstractRepository;
import ma.sir.easystock.bean.core.EtatBilan;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EtatBilanDao extends AbstractRepository<EtatBilan,Long>  {


    @Query("SELECT NEW EtatBilan(item.id,item.libelle) FROM EtatBilan item")
    List<EtatBilan> findAllOptimized();
}
