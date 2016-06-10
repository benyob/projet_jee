/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Local;
import bean.TaxeAnnuel;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author MarouaneKH
 */
@Stateless
public class TaxeAnnuelFacade extends AbstractFacade<TaxeAnnuel> {

    @PersistenceContext(unitName = "projet_jeePU")
    private EntityManager em;
    
    
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeAnnuelFacade() {
        super(TaxeAnnuel.class);
    }

    /* public List<TaxeAnnuel> chercherTaxeAnunuel(String secteur,String quartier,String rue,int anneMin,int anneMax){
    
    
    
    
    
    
    
    }*/
    public TaxeAnnuel getDernierTaxeTrimestriel(Local local) {
        List<TaxeAnnuel> res = em.createQuery("SELECT TA from TaxeAnnuel TA WHERE TA.local.id = :id order by TA.annee DESC").setParameter("id", local.getId()).getResultList();

        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    public List<TaxeAnnuel> searchTaxAnnuelBySecteur(String secteur, List<TaxeAnnuel> annuels) {

        List<TaxeAnnuel> myAnnuels = new ArrayList<>();
        for (TaxeAnnuel loadedTaxe : annuels) {
            if (loadedTaxe.getLocal().getRue().getQuartier().getSecteur().getNom().equals(secteur)) {
                myAnnuels.add(loadedTaxe);
            }
        }

        return myAnnuels;
    }

    public List<TaxeAnnuel> searchTaxAnnuelByQuartier(String quartier, List<TaxeAnnuel> annuels) {

        List<TaxeAnnuel> myAnnuels = new ArrayList<>();
        for (TaxeAnnuel loadedTaxe : annuels) {
            if (loadedTaxe.getLocal().getRue().getQuartier().getNom().equals(quartier)) {
                myAnnuels.add(loadedTaxe);
            }
        }

        return myAnnuels;
    }

    public List<TaxeAnnuel> searchTaxAnnuelByRue(String rue, List<TaxeAnnuel> annuels) {

        List<TaxeAnnuel> myAnnuels = new ArrayList<>();
        for (TaxeAnnuel loadedTaxe : annuels) {
            if (loadedTaxe.getLocal().getRue().getNom().equals(rue)) {
                myAnnuels.add(loadedTaxe);
            }
        }

        return myAnnuels;
    }

    public List<TaxeAnnuel> searchTaxeAnnuel(String secteur, String quartier, String rue, Integer anneMin, Integer anneMax) {
        List<TaxeAnnuel> taxeAnnuels = findAll();
        if (secteur != null && !secteur.equals("")) {
            taxeAnnuels = searchTaxAnnuelBySecteur(secteur, taxeAnnuels);

        }
        if (quartier != null && !quartier.equals("")) {
            taxeAnnuels = searchTaxAnnuelByQuartier(quartier, taxeAnnuels);

        }
        if (rue != null && !rue.equals("")) {
            taxeAnnuels = searchTaxAnnuelByRue(rue, taxeAnnuels);

        }
        if (anneMax != null) {
            taxeAnnuels = searchTaxAnnuelByAnneMax(anneMax, taxeAnnuels);
        }
        if (anneMin != null) {
            taxeAnnuels = searchTaxAnnuelByAnneMin(anneMin, taxeAnnuels);
        }

        return taxeAnnuels;
    }

    public List<TaxeAnnuel> searchTaxAnnuelByAnneMin(Integer anneMin, List<TaxeAnnuel> annuels) {
        List<TaxeAnnuel> taxeAnnuels = new ArrayList<>();

        for (TaxeAnnuel loadedtaxeAnnuel : annuels) {
            if (loadedtaxeAnnuel.getAnnee() >= anneMin) {
                taxeAnnuels.add(loadedtaxeAnnuel);
            }
        }

        return taxeAnnuels;
    }

    public List<TaxeAnnuel> searchTaxAnnuelByAnneMax(Integer anneMax, List<TaxeAnnuel> annuels) {
        List<TaxeAnnuel> taxeAnnuels = new ArrayList<>();

        for (TaxeAnnuel loadedtaxeAnnuel : annuels) {
            if (loadedtaxeAnnuel.getAnnee() <= anneMax) {
                taxeAnnuels.add(loadedtaxeAnnuel);
            }
        }

        return taxeAnnuels;
    }

}
