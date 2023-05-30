package  ma.sir.easystock.dao.specification.history;

import ma.sir.easystock.zynerator.specification.AbstractHistorySpecification;
import ma.sir.easystock.dao.criteria.history.TypeOperationComptableHistoryCriteria;
import ma.sir.easystock.bean.history.TypeOperationComptableHistory;


public class TypeOperationComptableHistorySpecification extends AbstractHistorySpecification<TypeOperationComptableHistoryCriteria, TypeOperationComptableHistory> {

    public TypeOperationComptableHistorySpecification(TypeOperationComptableHistoryCriteria criteria) {
        super(criteria);
    }

    public TypeOperationComptableHistorySpecification(TypeOperationComptableHistoryCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
