/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author MarouaneKH
 */
@Entity
public class TauxTaxeRetardTrimestriel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal tauxRetardPremierMois;
    private BigDecimal tauxRetardAutresMois;

    @OneToOne
    private User user;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateApplication;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateApplication() {
        return dateApplication;
    }

    public BigDecimal getTauxRetardPremierMois() {
        return tauxRetardPremierMois;
    }

    public void setTauxRetardPremierMois(BigDecimal tauxRetardPremierMois) {
        this.tauxRetardPremierMois = tauxRetardPremierMois;
    }

    public BigDecimal getTauxRetardAutresMois() {
        return tauxRetardAutresMois;
    }

    public void setTauxRetardAutresMois(BigDecimal tauxRetardAutresMois) {
        this.tauxRetardAutresMois = tauxRetardAutresMois;
    }

    public void setDateApplication(Date dateApplication) {
        this.dateApplication = dateApplication;
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
        if (!(object instanceof TauxTaxeRetardTrimestriel)) {
            return false;
        }
        TauxTaxeRetardTrimestriel other = (TauxTaxeRetardTrimestriel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.TauxRetardTrimestriel[ id=" + id + " ]";
    }

}
