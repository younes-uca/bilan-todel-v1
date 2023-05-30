package  ma.sir.easystock.dao.specification.history;

import ma.sir.easystock.zynerator.specification.AbstractHistorySpecification;
import ma.sir.easystock.dao.criteria.history.EtatOperationComptableHistoryCriteria;
import ma.sir.easystock.bean.history.EtatOperationComptableHistory;


public class EtatOperationComptableHistorySpecification extends AbstractHistorySpecification<EtatOperationComptableHistoryCriteria, EtatOperationComptableHistory> {

    public EtatOperationComptableHistorySpecification(EtatOperationComptableHistoryCriteria criteria) {
        super(criteria);
    }

    public EtatOperationComptableHistorySpecification(EtatOperationComptableHistoryCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
