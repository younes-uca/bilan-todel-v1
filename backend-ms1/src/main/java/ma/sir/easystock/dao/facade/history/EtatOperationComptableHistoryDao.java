package ma.sir.easystock.dao.facade.history;

import ma.sir.easystock.zynerator.repository.AbstractHistoryRepository;
import ma.sir.easystock.bean.history.EtatOperationComptableHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatOperationComptableHistoryDao extends AbstractHistoryRepository<EtatOperationComptableHistory,Long> {

}
