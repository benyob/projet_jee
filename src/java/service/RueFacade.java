/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ayoub
 */
@Stateless
public class RueFacade extends AbstractFacade<Rue> {

    @PersistenceContext(unitName = "projet_jeePU")
    private EntityManager em;
    @EJB
    private QuartierFacade quartierFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RueFacade() {
        super(Rue.class);
    }

     public List<Rue> findRueByQuartier(Quartier quartier) {
         return em.createQuery("SELECT r FROM Rue r WHERE r.quartier.id = '"+quartier.getId()+"'").getResultList();
     }
     public void removeQuartierWithRue(Quartier quartier){
         em.createQuery("DELETE FROM Rue r WHERE r.quartier.id = '"+quartier.getId()+"'").executeUpdate();
         quartierFacade.remove(quartier);
     }
    /**
     * Retourner la liste des rues qui appartiennent à un quartier donné
     * @param selected
     * @return 
     */
    public List<Rue> getRueByQuartier(Quartier selected) {
        String requete = "SELECT r FROM Rue r";
        if (selected != null) {
            requete = requete + " WHERE r.quartier.id = " + selected.getId();
        }else{
            requete = requete + " WHERE r.quartier.id = -1" ;
        }
        System.out.println(requete);
        System.out.println(em.createQuery(requete).getResultList().toString());
        return em.createQuery(requete).getResultList();
    }

    /**
     * vider la liste des rues aprés le changement du secteur
     * @return 
     */
    public List<Rue> viderListe() {
        String requete = "SELECT r FROM Rue r where r.id = -1";
        System.out.println(requete);    
        System.out.println(em.createQuery(requete).getResultList().toString());
        return em.createQuery(requete).getResultList();
    }
}
