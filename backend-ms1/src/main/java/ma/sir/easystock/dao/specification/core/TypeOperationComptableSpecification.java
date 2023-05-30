package  ma.sir.easystock.dao.specification.core;

import ma.sir.easystock.zynerator.specification.AbstractSpecification;
import ma.sir.easystock.dao.criteria.core.TypeOperationComptableCriteria;
import ma.sir.easystock.bean.core.TypeOperationComptable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TypeOperationComptableSpecification extends  AbstractSpecification<TypeOperationComptableCriteria, TypeOperationComptable>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
    }

    public TypeOperationComptableSpecification(TypeOperationComptableCriteria criteria) {
        super(criteria);
    }

    public TypeOperationComptableSpecification(TypeOperationComptableCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
