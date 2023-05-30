package  ma.sir.easystock.dao.specification.core;

import ma.sir.easystock.zynerator.specification.AbstractSpecification;
import ma.sir.easystock.dao.criteria.core.EtatAchatCriteria;
import ma.sir.easystock.bean.core.EtatAchat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EtatAchatSpecification extends  AbstractSpecification<EtatAchatCriteria, EtatAchat>  {

    @Override
    public void constructPredicates() {
        addPredicateId("id", criteria);
        addPredicate("libelle", criteria.getLibelle(),criteria.getLibelleLike());
        addPredicate("code", criteria.getCode(),criteria.getCodeLike());
        addPredicate("style", criteria.getStyle(),criteria.getStyleLike());
    }

    public EtatAchatSpecification(EtatAchatCriteria criteria) {
        super(criteria);
    }

    public EtatAchatSpecification(EtatAchatCriteria criteria, boolean distinct) {
        super(criteria, distinct);
    }

}
