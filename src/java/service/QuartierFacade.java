/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Quartier;
import bean.Secteur;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.emptyType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ayoub
 */
@Stateless
public class QuartierFacade extends AbstractFacade<Quartier> {

    @PersistenceContext(unitName = "projet_jeePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuartierFacade() {
        super(Quartier.class);
    }

    
    
    /**
     * Retourner la liste des quartiers qui appartiennent à un secteur donné
     * @param selected : le secteur choisi
     * @return 
     */
    public List<Quartier> getQuartierBySecteur(Secteur selected) {
        String requete = "SELECT q FROM Quartier q";
        if (selected != null) {
            System.out.println(selected.toString());
            requete = requete + " WHERE q.secteur.id = " + selected.getId();
        } else {
             requete = requete + " WHERE q.secteur.id = -1" ;
        }
        System.out.println(requete);
        System.out.println(em.createQuery(requete).getResultList().toString());

        return em.createQuery(requete).getResultList();
    }

}
