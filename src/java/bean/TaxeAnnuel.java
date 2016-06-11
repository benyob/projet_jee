/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author MarouaneKH
 */
@Entity
public class TaxeAnnuel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal totalTaxes;
  
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePresentation;

    private int nbrMoisRetard;
    private int annee;
    private BigDecimal chiffreAffaireTotal;

    @OneToMany(mappedBy = "taxeAnnuel")
    private List<TaxeTrimestriel> taxeTrimestriels;

    @ManyToOne
    Local local;
    @ManyToOne
    Redevable redevable;

    public Local getLocal() {
        //System.out.println("getttttttttttter " + local.getPatente());
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Redevable getRedevable() {
        if (redevable == null) {
            redevable = new Redevable();
        }
        //System.out.println("redevable " + redevable.getNom());
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
        //System.out.println("redevable " + redevable.getNom());

    }


    public BigDecimal getTotalTaxes() {
        return totalTaxes;
    }

    public List<TaxeTrimestriel> getTaxeTrimestriels() {
        if (taxeTrimestriels == null) {
            taxeTrimestriels = new ArrayList();
        }
        return taxeTrimestriels;
    }

    public void setTaxeTrimestriels(List<TaxeTrimestriel> taxeTrimestriels) {
        this.taxeTrimestriels = taxeTrimestriels;
    }

    public void setTotalTaxes(BigDecimal totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public Date getDatePresentation() {
        return datePresentation;
    }

    public void setDatePresentation(Date datePresentation) {
        this.datePresentation = datePresentation;
    }

    public int getNbrMoisRetard() {
        return nbrMoisRetard;
    }

    public void setNbrMoisRetard(int nbrMoisRetard) {
        this.nbrMoisRetard = nbrMoisRetard;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public BigDecimal getChiffreAffaireTotal() {
        return chiffreAffaireTotal;
    }

    public void setChiffreAffaireTotal(BigDecimal chiffreAffaireTotal) {
        this.chiffreAffaireTotal = chiffreAffaireTotal;
    }

    public Long getId() {
        return id;
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
        if (!(object instanceof TaxeAnnuel)) {
            return false;
        }
        TaxeAnnuel other = (TaxeAnnuel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.TaxeAnnuel[ id=" + id + " ]";
    }

}
