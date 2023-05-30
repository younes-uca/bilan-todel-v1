package  ma.sir.easystock.dao.specification.core;

import ma.sir.easystock.zynerator.specification.AbstractSpecification;
import ma.sir.easystock.dao.criteria.core.EtatBilanCriteria;
import ma.sir.easystock.bean.core.EtatBilan;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EtatBilanSpecification extends  AbstractSpecification<EtatBilanCriteria, EtatBilan>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
    }

    public EtatBilanSpecification(EtatBilanCriteria criteria) {
        super(criteria);
    }

    public EtatBilanSpecification(EtatBilanCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
