package ma.sir.easystock.bean.history;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.sir.easystock.zynerator.history.HistBusinessObject;
import javax.persistence.*;


@Entity
@Table(name = "etat_operation_comptable")
@JsonInclude(JsonInclude.Include.NON_NULL)
@SequenceGenerator(name="etat_operation_comptable_seq",sequenceName="etat_operation_comptable_seq",allocationSize=1, initialValue = 1)
public class EtatOperationComptableHistory extends HistBusinessObject  {


    public EtatOperationComptableHistory() {
    super();
    }

    public EtatOperationComptableHistory (Long id) {
    super(id);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="etat_operation_comptable_seq")
    public Long getId() {
    return id;
    }
}

