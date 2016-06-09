/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Ayoub
 */
@Stateless
public class RedevableFacade extends AbstractFacade<Redevable> {

    @PersistenceContext(unitName = "projet_jeePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RedevableFacade() {
        super(Redevable.class);
    }

//    public void saveRedevable(Redevable selected) {
//        if (selected.getPersonnePhysique()) {
//            selected.setRaisonSociale(null);
//            create(selected);
//        }
//    }
    /**
     * Retourner la liste des redevables selon les critères de recherches
     * choisis pour les afficher dans le datatable
     *
     * @param cin : cin entré par l'utilisateur
     * @param rc : rc entré par l'utilisateur
     * @param secteur : le secteur choisi par l'utilisateur
     * @param quartier : le quartier choisi par l'utilisateur
     * @param rue : la rue choisie par l'utilisateur
     * @param physique : personne physique ou non
     * @return
     */
    public List<Redevable> selectRedevableByCritere(String cin, String rc, Secteur secteur, Quartier quartier, Rue rue, Boolean physique) {
        //  String requete = "select r from Redevable r, Rue rue, Quartier q, Local l, Secteur s where r.id != -1";
        String requete = "";
        if (physique) {
            rc = null;
        } else {
            cin = null;
        }

//        if (cin != null) {
//            if (cin.equals("")) {
//                System.out.println("ne fait rien");
//            } else {
//                requete += " and r.cin = '" + cin + "'";
//            }
//        }
//
//        if (rc != null) {
//            if (rc.equals("")) {
//                System.out.println("ne fait rien");
//            } else {
//                requete += " and r.rc = '" + rc + "'";
//            }
//
//        }
        if ((secteur == null) && (quartier == null) && (rue == null)) {
            if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                    requete = "SELECT r from Redevable r where r.id != -1";
                } else {
                    requete = "SELECT r from Redevable r where r.cin = '" + cin + "'";
                }
            } else if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                    requete = "SELECT r from Redevable r where r.id != -1";

                } else {
                    requete = "SELECT r from Redevable r where r.rc = '" + rc + "'";
                }

            } else {
                requete = "SELECT r from Redevable r where r.id != -1";
            }

        }

        if ((secteur != null) && (quartier != null) && (rue != null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l, Secteur s where";
            requete += " s.nom ='" + secteur.getNom() + "' and q.secteur.id = s.id  and rue.quartier.id = q.id and l.rue.id = rue.id and l.redevable.id = r.id ";
            requete += " and q.nom ='" + quartier.getNom() + "'";
            requete += " and rue.nom ='" + rue.getNom() + "'";
            if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        } else if ((secteur == null) && (quartier != null) && (rue != null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l where";

            requete += " q.nom ='" + quartier.getNom() + "'and rue.nom ='" + rue.getNom() + "' and rue.quartier.id= q.id and l.rue.id = rue.id and l.redevale.id = r.id";
            // requete += " and rue.nom='" + rue.getNom() + "' and rue.id = l.rue.id and l.redevable.id = r.id";
           if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        } else if ((secteur == null) && (quartier == null) && (rue != null)) {
            requete = "select r from Redevable r, Rue rue, Local l where";
            requete += " rue.nom='" + rue.getNom() + "' and rue.id = l.rue.id and l.redevable.id = r.id";
           if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        } else if ((secteur != null) && (quartier == null) && (rue != null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l, Secteur s where";
            requete += " s.nom ='" + secteur.getNom() + "' and q.secteur.id = s.id  and  q.id = rue.quartier.id and l.rue.id = rue.id and l.redevable.id = r.id ";
            requete += " and rue.nom ='" + rue.getNom() + "'";
            if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        } else if ((secteur != null) && (quartier == null) && (rue == null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l, Secteur s where";
            requete += " s.nom ='" + secteur.getNom() + "' and q.secteur.id = s.id  and  rue.quartier.id = q.id and l.rue.id = rue.id and l.redevable.id = r.id ";
           if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        } else if ((secteur != null) && (quartier != null) && (rue == null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l, Secteur s where";
            requete += " s.nom ='" + secteur.getNom() + "' and q.secteur.id = s.id and rue.quartier.id = q.id and l.rue.id = rue.id and l.redevable.id = r.id ";
            requete += " and q.nom ='" + quartier.getNom() + "'";
           if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        }

        if ((secteur == null) && (quartier != null) && (rue == null)) {
            requete = "select r from Redevable r, Rue rue, Quartier q, Local l where";
            requete += " q.nom = '" + quartier.getNom() + "' and rue.quartier.id = q.id and rue.id = l.rue.id and l.redevable.id = r.id";
            if (rc != null) {
                if (rc.equals("")) {
                    System.out.println("ne fait rien");
                } else {
                    requete += " and r.rc = '" + rc + "'";
                }

            }else   if (cin != null) {
                if (cin.equals("")) {
                    System.out.println("ne fait rien");
                  
                } else {
                    requete += " and r.cin = '" + cin + "'";
                }
            }

        }
        System.out.println(requete);
        System.out.println("haa resultat: " + em.createQuery(requete).getResultList().toString());
        return em.createQuery(requete).getResultList();

    }

}
