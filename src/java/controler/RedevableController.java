package controler;

import bean.Local;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.RedevableFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("redevableController")
@SessionScoped
public class RedevableController implements Serializable {

    private List<Redevable> items = null;
    private Redevable selected;
    boolean personnePhysique = false;
    boolean personneMorale = true;

    List<Redevable> liste = null;
    private String cin;
    private String rc;
    private Secteur secteur;
    private Quartier quartier;
    private Rue rue;
    private Secteur secteurVide = null;
    private Quartier quartierVide = null;
    private Rue rueVide = null;

    @EJB
    private service.RedevableFacade redevableFacade;

    public Secteur getSecteurVide() {
        return secteurVide;
    }

    public void setSecteurVide(Secteur secteurVide) {
        this.secteurVide = secteurVide;
    }

    public Quartier getQuartierVide() {
        return quartierVide;
    }

    public void setQuartierVide(Quartier quartierVide) {
        this.quartierVide = quartierVide;
    }

    public Rue getRueVide() {
        return rueVide;
    }

    public RedevableController() {

    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public List<Redevable> getListe() {
        if (liste == null) {
            liste = getItems();
        }
        return liste;
    }

    public void setListe(List<Redevable> liste) {
        this.liste = liste;
    }

    public boolean isPersonnePhysique() {
        return personnePhysique;
    }

    public void setPersonnePhysique(boolean personnePhysique) {
        this.personnePhysique = personnePhysique;
    }

    public boolean isPersonneMorale() {
        return personneMorale;
    }

    public void setPersonneMorale(boolean personneMorale) {
        this.personneMorale = personneMorale;
    }

    // SecteurController secteurController = new SecteurController();
    public void selectRedevableByCritere() {
        setListe(redevableFacade.selectRedevableByCritere(cin, rc, secteur, quartier, rue, personnePhysique));
    }

    public void checkPersonnePhysique() {
        System.out.println("dkheeeeel");
        if (personnePhysique) {
            setPersonnePhysique(false);
            //setDisableRc("redevableController.rc");
            System.out.println("tbdlaat");

        } else {
            // setDisableRc("");
            setPersonnePhysique(true);
            System.out.println("tbdlaat");

        }
        if (personneMorale) {
            setPersonneMorale(false);
        } else {
            setPersonneMorale(true);
        }
    }

    public boolean checkPersonneMorale() {
        System.out.println("dkheeeeel");
        if (personneMorale) {
            setPersonneMorale(false);
            //setDisableCin("redevableController.cin");
            System.out.println("tbdlaat");

        } else {
            setPersonneMorale(true);
            //setDisableCin("");
            System.out.println("tbdlaat");

        }
        return personneMorale;
    }

    public Redevable getSelected() {
        if (selected == null) {
            selected = new Redevable();
        }
        return selected;
    }

    public void setSelected(Redevable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RedevableFacade getFacade() {
        return redevableFacade;
    }

    public Redevable prepareCreate() {
        selected = new Redevable();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(LocalController localController) {

        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RedevableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
            localController.getSelected().setRedevable(selected);
            localController.create();
        }

    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RedevableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RedevableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Redevable> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        try {
            if (persistAction != PersistAction.DELETE) {
                getFacade().edit(selected);
            } else {
                getFacade().remove(selected);
            }
            JsfUtil.addSuccessMessage(successMessage);
        } catch (EJBException ex) {
            String msg = "";
            Throwable cause = ex.getCause();
            if (cause != null) {
                msg = cause.getLocalizedMessage();
            }
            if (msg.length() > 0) {
                JsfUtil.addErrorMessage(msg);
            } else {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public Redevable getRedevable(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Redevable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Redevable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Redevable.class)
    public static class RedevableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RedevableController controller = (RedevableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "redevableController");
            return controller.getRedevable(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Redevable) {
                Redevable o = (Redevable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Redevable.class.getName()});
                return null;
            }
        }

    }

}
