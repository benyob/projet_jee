package controler;

import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.QuartierFacade;

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
import service.RueFacade;

@Named("quartierController")
@SessionScoped
public class QuartierController implements Serializable {

    @EJB
    private QuartierFacade quartierFacade;
    @EJB
    private RueFacade rueFacade;
    
    
    private List<Quartier> items = null;
    private Quartier selected;
    List<Quartier> quartiers = new ArrayList<>();
    List<Rue> rues = new ArrayList<>();
    private Quartier quartierVide = null;

    public Quartier getQuartierVide() {
        return quartierVide;
    }

    public void setQuartierVide(Quartier quartierVide) {
        this.quartierVide = quartierVide;
    }

    
    
    public List<Rue> getRues() {
        //if(rues == null) setRues(rueFacade.findAll());
        return rues;
    }

    public void setRues(List<Rue> rues) {
        this.rues = rues;
    }

    public QuartierController() {
    }

    public List<Rue> getRueByQuartier(Quartier selected) {
        setRues(rueFacade.getRueByQuartier(selected));
        return rues;

    }

    public  List<Rue>  viderListeRue(){
        setRues(rueFacade.viderListe());
        return rues;
    }

    public List<Quartier> getQuartiers() {
        return quartiers;
    }

    public void setQuartiers(List<Quartier> quartiers) {
        this.quartiers = quartiers;
    }

    public Quartier getSelected() {
        if (selected == null) {
            selected = new Quartier();
        }
        return selected;
    }

    public void setSelected(Quartier selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuartierFacade getFacade() {
        return quartierFacade;
    }

    public Quartier prepareCreate() {
        selected = new Quartier();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuartierCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuartierUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuartierDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Quartier> getItems() {
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

    public Quartier getQuartier(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Quartier> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Quartier> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Quartier.class)
    public static class QuartierControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuartierController controller = (QuartierController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "quartierController");
            return controller.getQuartier(getKey(value));
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
            if (object instanceof Quartier) {
                Quartier o = (Quartier) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Quartier.class.getName()});
                return null;
            }
        }

    }

}
