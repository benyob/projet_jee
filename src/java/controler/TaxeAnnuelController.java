package controler;

import bean.Local;
import bean.Redevable;
import bean.TauxTaxeRetardTrimestriel;
import bean.TauxTaxeTrimestriel;
import bean.TaxeAnnuel;
import bean.TaxeTrimestriel;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.TaxeAnnuelFacade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import service.LocalFacade;
import service.RedevableFacade;
import service.TauxTaxeRetardTrimestrielFacade;
import service.TauxTaxeTrimestrielFacade;
import service.TaxeTrimestrielFacade;

@Named("taxeAnnuelController")
@SessionScoped
public class TaxeAnnuelController implements Serializable {

    @EJB
    private service.TaxeAnnuelFacade ejbFacade;

    private List<TaxeAnnuel> items = null;
    private TaxeAnnuel selected;

    private String secteur;
    private String quartier;
    private String rue;
    private Integer anneMin;
    private Integer anneMax;

    @EJB
    private service.TaxeTrimestrielFacade taxeTrimestrielFacade;
    @EJB
    private service.RedevableFacade redevableFacade;
    @EJB
    private service.LocalFacade localFacade;
    @EJB
    private service.TauxTaxeTrimestrielFacade tauxTaxeTrimestrielFacade;
    @EJB
    private service.TauxTaxeRetardTrimestrielFacade tauxTaxeRetardTrimestrielFacade;

    private TaxeTrimestriel taxeTrimestriel;

    private boolean dejaPaye;

    
    public void searchTaxAnnuels() {
        items = ejbFacade.searchTaxeAnnuel(secteur, quartier, rue, anneMin, anneMax);

    }
    
    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Integer getAnneMin() {
        return anneMin;
    }

    public void setAnneMin(Integer anneMin) {
        this.anneMin = anneMin;
    }

    public Integer getAnneMax() {
        return anneMax;
    }

    public void setAnneMax(Integer anneMax) {
        this.anneMax = anneMax;
    }

    
    
    public TauxTaxeRetardTrimestrielFacade getTauxTaxeRetardTrimestrielFacade() {
        return tauxTaxeRetardTrimestrielFacade;
    }

    public void setTauxTaxeRetardTrimestrielFacade(TauxTaxeRetardTrimestrielFacade tauxTaxeRetardTrimestrielFacade) {
        this.tauxTaxeRetardTrimestrielFacade = tauxTaxeRetardTrimestrielFacade;
    }

    public TauxTaxeTrimestrielFacade getTauxTaxeTrimestrielFacade() {
        return tauxTaxeTrimestrielFacade;
    }

    public void setTauxTaxeTrimestrielFacade(TauxTaxeTrimestrielFacade tauxTaxeTrimestrielFacade) {
        this.tauxTaxeTrimestrielFacade = tauxTaxeTrimestrielFacade;
    }

    public LocalFacade getLocalFacade() {
        return localFacade;
    }

    public void setLocalFacade(LocalFacade localFacade) {
        this.localFacade = localFacade;
    }

    public boolean isDejaPaye() {
        return dejaPaye;
    }

    public void setDejaPaye(boolean dejaPaye) {
        this.dejaPaye = dejaPaye;
    }

    public TaxeAnnuelController() {
    }

    public void testDate() {
        System.out.println("tessst");
        LocalDate d1 = LocalDate.of(2016, 6, 07);
        LocalDate d2 = LocalDate.of(2017, 6, 07);

        System.out.println("Month  :  " + d1.getMonthValue());
    }

    public void getDernierTaxeTrimestriel() {
        setRedevable();

        if (selected.getLocal().getIdDernierTaxeTrimestrielPaye() == -1) {

            dejaPaye = false;

            Local local = selected.getLocal();
            Redevable redevable = selected.getRedevable();

            selected = new TaxeAnnuel();
            selected.setLocal(local);
            selected.setChiffreAffaireTotal(new BigDecimal(0));
            selected.setRedevable(redevable);
            selected.setTotalTaxes(new BigDecimal(0));

            taxeTrimestriel = new TaxeTrimestriel();
            taxeTrimestriel.setRedevable(local.getRedevable());
            taxeTrimestriel.setTaxeAnnuel(selected);

            System.out.println("rah nulllllllll");
        } else {
            TaxeAnnuel annuel = ejbFacade.getDernierTaxeTrimestriel(selected.getLocal());

            dejaPaye = true;
            taxeTrimestriel = new TaxeTrimestriel();

            if (annuel.getLocal().getIdDernierTaxeTrimestrielPaye() < 4) {
                selected = annuel;

                taxeTrimestriel.setNumeroTrimestre(annuel.getLocal().getIdDernierTaxeTrimestrielPaye() + 1);
                taxeTrimestriel.setRedevable(selected.getLocal().getRedevable());
                taxeTrimestriel.setTaxeAnnuel(selected);

                System.out.println("AVAAAANT ++++++++++" + selected.getLocal().getIdDernierTaxeTrimestrielPaye());
                selected.getLocal().setIdDernierTaxeTrimestrielPaye(taxeTrimestriel.getNumeroTrimestre());
                System.out.println("APREEEES ++++++++++" + selected.getLocal().getIdDernierTaxeTrimestrielPaye());

            } else if (annuel.getLocal().getIdDernierTaxeTrimestrielPaye() == 4) {
                selected = new TaxeAnnuel();

                selected.setAnnee(annuel.getAnnee() + 1);
                selected.setRedevable(annuel.getLocal().getRedevable());
                selected.setChiffreAffaireTotal(new BigDecimal(0));
                selected.setTotalTaxes(new BigDecimal(0));

                selected.setLocal(annuel.getLocal());
                selected.getLocal().setIdDernierTaxeTrimestrielPaye(1);

                taxeTrimestriel.setNumeroTrimestre(1);
                taxeTrimestriel.setRedevable(selected.getRedevable());
                taxeTrimestriel.setTaxeAnnuel(selected);
            }

            //taxeTrimestriel.setNumeroTrimestre(annuel.getTaxeTrimestriels().get(0).getNumeroTrimestre());
            System.out.println("machi nulllllll");
        }
    }

    public void appliqueTauxTaxe() {
        TauxTaxeTrimestriel ttt = tauxTaxeTrimestrielFacade.getTauxApplicable(selected.getAnnee(), taxeTrimestriel.getNumeroTrimestre());

        BigDecimal retardPremierMois = new BigDecimal(0);
        BigDecimal retardAutreMois = new BigDecimal(0);
        BigDecimal totalRetard = new BigDecimal(0);

        if (ttt == null) {
            System.out.println("Taux nuuuuuuuul");
        } else {
            BigDecimal taxe = taxeTrimestriel.getChiffreAffaire().multiply(ttt.getTauxTaxe().divide(new BigDecimal(100)));

            System.out.println("CA : " + taxeTrimestriel.getChiffreAffaire() + " taux : " + ttt.getTauxTaxe() + " = " + taxe);

            int nbrMois = getMoisRetard(taxeTrimestriel.getNumeroTrimestre(), selected.getAnnee(), taxeTrimestriel.getDatePresentation(), 1);
            System.out.println("nombre de mois de RETARD **********" + nbrMois);

            if (nbrMois > 0) {
                TauxTaxeRetardTrimestriel tauxRetard = getTauxRetard();

                int nbrMoisRetardAutreMois = 0;

                if (tauxRetard != null) {
                    System.out.println("taux retard app 1mois : " + tauxRetard.getTauxRetardPremierMois());

                    System.out.println("TAUX RETARD APPLIQUE //////***** " + tauxRetard);

                    retardPremierMois = taxe.multiply(tauxRetard.getTauxRetardPremierMois().divide(new BigDecimal(100)));

                    System.out.println("retard premier mois " + retardPremierMois);

                    int nbrMoisTemp = nbrMois;

                    nbrMoisRetardAutreMois = nbrMoisTemp--;

                    if (nbrMoisRetardAutreMois > 0) {
                        retardAutreMois = taxe.multiply(tauxRetard.getTauxRetardAutresMois().divide(new BigDecimal(100))).multiply(new BigDecimal(nbrMoisRetardAutreMois));

                    } else {
                        nbrMoisRetardAutreMois = 0;
                    }

                }
            } else {
                nbrMois = 0;
            }
            System.out.println("NOMBRE DE MOIS AVANT AFFECTATION : " + nbrMois);
            BigDecimal totalTaxe = taxe.add(retardPremierMois);
            totalTaxe = totalTaxe.add(retardAutreMois);

            totalRetard = totalRetard.add(retardPremierMois);
            totalRetard = totalRetard.add(retardAutreMois);

            selected.setChiffreAffaireTotal(selected.getChiffreAffaireTotal().add(taxeTrimestriel.getChiffreAffaire()));
            selected.setTotalTaxes(selected.getTotalTaxes().add(totalTaxe));

            taxeTrimestriel.setNbrMoisRetard(nbrMois);

            taxeTrimestriel.setTaxe(taxe);
            taxeTrimestriel.setRetard(totalRetard);
            taxeTrimestriel.setTotalTaxe(totalTaxe);

            //annuel CA total ete total taxe
            //private BigDecimal taxe;
            //private BigDecimal retard;
            //private BigDecimal totalTaxe;
            //private int numeroTrimestre;
            //private int nbrMoisRetard;
            System.out.println("Ha taxe applicable : " + ttt.getTauxTaxe());
        }
    }

    public BigDecimal getCATotal() {
        BigDecimal ca = new BigDecimal(0);

        for (TaxeAnnuel annuel : items) {
            ca = ca.add(annuel.getChiffreAffaireTotal());
        }
        return ca;
    }

    public BigDecimal getTaxesTotal() {
        BigDecimal ca = new BigDecimal(0);

        for (TaxeAnnuel annuel : items) {
            if (annuel.getTotalTaxes() != null) {
                ca = ca.add(annuel.getTotalTaxes());
            }
        }
        return ca;
    }

    public BigDecimal getCATotalTaxeSelected() {
        BigDecimal ca = new BigDecimal(0);

        for (TaxeTrimestriel trimestriel : getSelected().getTaxeTrimestriels()) {
            if (trimestriel.getTotalTaxe() != null) {
                ca = ca.add(trimestriel.getTotalTaxe());
            }
        }
        return ca;
    }

    public TauxTaxeRetardTrimestriel getTauxRetard() {
        TauxTaxeRetardTrimestriel ttrt = tauxTaxeRetardTrimestrielFacade.getTauxRetardApplicable(selected.getAnnee(), taxeTrimestriel.getNumeroTrimestre());

        if (ttrt == null) {
            System.out.println("RETAAAARD NULLL");
        } else {
            //System.out.println("Ha RETARD Applique " + ttrt.getTauxRetardPremierMois());
        }
        return ttrt;
    }

    public void getTauxApplicable() {

    }

    public void setRedevable() {
        System.out.println("teeeeest " + selected.getLocal().getPatente());
        if (selected.getLocal() != null) {
            selected.setRedevable(selected.getLocal().getRedevable());
        }
    }

    public TaxeAnnuel getSelected() {
        if (selected == null) {
            selected = new TaxeAnnuel();
        }
        //System.out.println("--->" + selected.getLocal().getTel());
        return selected;
    }

    public void getTaxeTrimByTaxeAnnuel() {
        System.out.println("mmmmmm");
        getSelected().setTaxeTrimestriels(taxeTrimestrielFacade.getTaxeTrimByTaxeAnnuel(selected));

    }

    public TaxeTrimestrielFacade getTaxeTrimestrielFacade() {
        return taxeTrimestrielFacade;
    }

    public void setTaxeTrimestrielFacade(TaxeTrimestrielFacade taxeTrimestrielFacade) {
        this.taxeTrimestrielFacade = taxeTrimestrielFacade;
    }

    public RedevableFacade getRedevableFacade() {
        return redevableFacade;
    }

    public void setRedevableFacade(RedevableFacade redevableFacade) {
        this.redevableFacade = redevableFacade;
    }

    public TaxeTrimestriel getTaxeTrimestriel() {
        if (taxeTrimestriel == null) {
            taxeTrimestriel = new TaxeTrimestriel();
        }
        return taxeTrimestriel;
    }

    public void setTaxeTrimestriel(TaxeTrimestriel taxeTrimestriel) {
        this.taxeTrimestriel = taxeTrimestriel;
    }

    public void setSelected(TaxeAnnuel selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeAnnuelFacade getFacade() {
        return ejbFacade;
    }

    public TaxeAnnuel prepareCreate() {
        selected = new TaxeAnnuel();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TaxeAnnuel> getItems() {
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

                    appliqueTauxTaxe();

                    getFacade().edit(selected);
                    taxeTrimestriel.setTaxeAnnuel(ejbFacade.getDernierTaxeTrimestriel(selected.getLocal()));

                    taxeTrimestrielFacade.create(taxeTrimestriel);

                    selected.getLocal().setIdDernierTaxeTrimestrielPaye(taxeTrimestriel.getNumeroTrimestre());

                    System.out.println("haaaaaaaaaaaaaaaaaaa local id :: " + selected.getLocal().getId());

                    localFacade.edit(selected.getLocal());

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

    public TaxeAnnuel getTaxeAnnuel(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TaxeAnnuel> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TaxeAnnuel> getItemsAvailableSelectOne() {
        return getFacade().findAll();

    }

    @FacesConverter(forClass = TaxeAnnuel.class)
    public static class TaxeAnnuelControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeAnnuelController controller = (TaxeAnnuelController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeAnnuelController");
            return controller.getTaxeAnnuel(getKey(value));
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
            if (object instanceof TaxeAnnuel) {
                TaxeAnnuel o = (TaxeAnnuel) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeAnnuel.class.getName()});
                return null;
            }
        }

    }

    public Date getMyDate(int numeroTrimistre, int anne) {
        Date date = null;
        try {
            String mydate = "";
            if (numeroTrimistre == 1) {
                mydate = "01/04/" + anne;

            } else if (numeroTrimistre == 2) {
                mydate = "01/07/" + anne;
            } else if (numeroTrimistre == 3) {
                mydate = "01/10/" + anne;
            } else if (numeroTrimistre == 4) {
                anne++;
                mydate = "01/01/" + anne;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = simpleDateFormat.parse(mydate);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return date;

        }

    }

    public int getMoisRetard(int numeroTrimestre, int anne, Date datePresentation, int nbrmoisignorer) {

        java.sql.Date dd = new java.sql.Date(datePresentation.getTime());

        System.out.println("------> date presentation : " + dd);

        int moisRetard = 0;
        Date myDate = getMyDate(numeroTrimestre, anne);

        Calendar trimestriel = Calendar.getInstance();
        Calendar presentation = Calendar.getInstance();
        trimestriel.setTime(myDate);
        presentation.setTime(datePresentation);

        int presantationYear = presentation.get(Calendar.YEAR);
        int presentationMonth = presentation.get(Calendar.MONTH);
        int trimestreYear = trimestriel.get(Calendar.YEAR);
        int trimestreMonth = trimestriel.get(Calendar.MONTH);

        System.out.println("--------------");

        System.out.println("presentationYear : " + presantationYear);
        System.out.println("presentationMonth : " + presentationMonth);

        System.out.println("trim year : " + trimestreYear);
        System.out.println("trim Month : " + trimestreMonth);

        System.out.println("--------------");

        if (presantationYear - trimestreYear == 0) {

            moisRetard = presentationMonth - trimestreMonth;

        } else {
            moisRetard = presentationMonth;
            presantationYear--;
            while (presantationYear - trimestreYear > 0) {
                moisRetard += 12;
                presantationYear--;

            }
            int retatd = 12 - trimestreMonth;
            moisRetard += retatd;

        }

        return moisRetard - nbrmoisignorer;
    }

}
