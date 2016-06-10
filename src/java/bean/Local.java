
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author MarouaneKH
 */
@Entity
public class Local implements Serializable {

    @OneToMany(mappedBy = "local")
    private List<TaxeAnnuel> taxeAnnuels;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patente;
    private String fax;
    private String tel;
    private int idDernierTaxeTrimestrielPaye;// -1 : si ila n'as jamais paye, sinon id de taxe dernier Trimestre paye

    @ManyToOne
    Rue rue;

    @ManyToOne
    Redevable redevable;

    public int getIdDernierTaxeTrimestrielPaye() {
        return idDernierTaxeTrimestrielPaye;
    }

    public void setIdDernierTaxeTrimestrielPaye(int idDernierTaxeTrimestrielPaye) {
        this.idDernierTaxeTrimestrielPaye = idDernierTaxeTrimestrielPaye;
    }

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public List<TaxeAnnuel> getTaxeAnnuels() {
        return taxeAnnuels;
    }

    public void setTaxeAnnuels(List<TaxeAnnuel> taxeAnnuels) {
        this.taxeAnnuels = taxeAnnuels;
    }

    public Redevable getRedevable() {
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public Long getId() {
        return id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Local)) {
            return false;
        }
        Local other = (Local) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Local{" + "taxeAnnuels=" + taxeAnnuels + ", id=" + id + ", patente=" + patente + ", fax=" + fax + ", tel=" + tel + ", idDernierTaxeTrimestrielPaye=" + idDernierTaxeTrimestrielPaye + ", rue=" + rue + ", redevable=" + redevable + '}';
    }

    
    
    

}
