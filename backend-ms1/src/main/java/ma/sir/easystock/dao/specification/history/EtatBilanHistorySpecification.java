package  ma.sir.easystock.dao.specification.history;

import ma.sir.easystock.zynerator.specification.AbstractHistorySpecification;
import ma.sir.easystock.dao.criteria.history.EtatBilanHistoryCriteria;
import ma.sir.easystock.bean.history.EtatBilanHistory;


public class EtatBilanHistorySpecification extends AbstractHistorySpecification<EtatBilanHistoryCriteria, EtatBilanHistory> {

    public EtatBilanHistorySpecification(EtatBilanHistoryCriteria criteria) {
        super(criteria);
    }

    public EtatBilanHistorySpecification(EtatBilanHistoryCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
