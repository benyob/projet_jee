package controler;

import bean.Quartier;
import bean.Secteur;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.SecteurFacade;

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
import service.QuartierFacade;

@Named("secteurController")
@SessionScoped
public class SecteurController implements Serializable {

    private List<Secteur> items = null;
    private Secteur selected;
    private Secteur selectone;
    QuartierController quartierController = new QuartierController();

    @EJB
    QuartierFacade quartierFacade;

    @EJB
    private service.SecteurFacade secteurFacade;
    private List<Quartier> quartiers = new ArrayList<Quartier>();
    
    
    private Secteur secteurVide = null;
    //quartierController.getItems();

    public Secteur getSecteurVide() {
        return secteurVide;
    }

    public void setSecteurVide(Secteur secteurVide) {
        this.secteurVide = secteurVide;
    }

    
    public List<Quartier> getQuartierBySecteur(Secteur secteur) {
        setQuartiers(quartierFacade.getQuartierBySecteur(secteur));
        return quartiers;
    }

    public void viderListeRue() {

    }

    public List<Quartier> getQuartiers() {
//        if (quartiers == null) {
//            System.out.println("raha nuuuuuuul");
//            quartiers = quartierFacade.findAll();
//        }

        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public SecteurController() {
    }

    public Secteur getSelected() {
        return selected;
    }

    public Secteur getSelectone() {
        return selectone;
    }

    public void setSelectone(Secteur selectone) {
        this.selectone = selectone;
    }

    public void setSelected(Secteur selected) {
        if (selected == null) {
            selected = new Secteur();
        }
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SecteurFacade getFacade() {
        return secteurFacade;
    }

    public Secteur prepareCreate() {
        selected = new Secteur();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SecteurCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SecteurUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SecteurDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Secteur> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
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
    }

    public Secteur getSecteur(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Secteur> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Secteur> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Secteur.class)
    public static class SecteurControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecteurController controller = (SecteurController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secteurController");
            return controller.getSecteur(getKey(value));
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
            if (object instanceof Secteur) {
                Secteur o = (Secteur) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Secteur.class.getName()});
                return null;
            }
        }

    }

}
