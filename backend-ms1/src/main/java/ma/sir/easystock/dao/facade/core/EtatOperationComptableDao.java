package ma.sir.easystock.dao.facade.core;

import org.springframework.data.jpa.repository.Query;
import ma.sir.easystock.zynerator.repository.AbstractRepository;
import ma.sir.easystock.bean.core.EtatOperationComptable;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EtatOperationComptableDao extends AbstractRepository<EtatOperationComptable,Long>  {


    @Query("SELECT NEW EtatOperationComptable(item.id,item.libelle) FROM EtatOperationComptable item")
    List<EtatOperationComptable> findAllOptimized();
}
