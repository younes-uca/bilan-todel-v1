package ma.sir.easystock.dao.facade.history;

import ma.sir.easystock.zynerator.repository.AbstractHistoryRepository;
import ma.sir.easystock.bean.history.TypeOperationComptableHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOperationComptableHistoryDao extends AbstractHistoryRepository<TypeOperationComptableHistory,Long> {

}
