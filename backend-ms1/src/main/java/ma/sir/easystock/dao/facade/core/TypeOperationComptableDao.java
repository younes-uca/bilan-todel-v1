package ma.sir.easystock.dao.facade.core;

import org.springframework.data.jpa.repository.Query;
import ma.sir.easystock.zynerator.repository.AbstractRepository;
import ma.sir.easystock.bean.core.TypeOperationComptable;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TypeOperationComptableDao extends AbstractRepository<TypeOperationComptable,Long>  {


    @Query("SELECT NEW TypeOperationComptable(item.id,item.libelle) FROM TypeOperationComptable item")
    List<TypeOperationComptable> findAllOptimized();
}
