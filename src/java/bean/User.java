package bean;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String login;

    private String password;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private boolean creationRedevable = true;      //0
    private boolean consultationRedevable = true;  //1

    private boolean creationTauxTaxe = true;      //2
    private boolean consultationTauxTaxe = true;  //3

    private boolean creationTarificationRetardTaxe = true;      //4
    private boolean consultationTarificationRetardTaxe = true;  //5

    private boolean creationTaxeBoisonTrimistrielle = true;      //6
    private boolean consultationTaxeBoisonTrimistrielle = true;  //7

    private boolean creationSecteure = true;      //8
    private boolean consultationSecteure = true;  //9

    private boolean creationUser = true;       //10
    private boolean consultationUser = true;   //11

//    private boolean aviser;       //14
//    private boolean cloturer;   //15
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.login);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    public boolean isCreationRedevable() {
        return creationRedevable;
    }

    public void setCreationRedevable(boolean creationRedevable) {
        this.creationRedevable = creationRedevable;
    }

    public boolean isConsultationRedevable() {
        return consultationRedevable;
    }

    public void setConsultationRedevable(boolean consultationRedevable) {
        this.consultationRedevable = consultationRedevable;
    }



    public boolean isCreationTaxeBoisonTrimistrielle() {
        return creationTaxeBoisonTrimistrielle;
    }

    public void setCreationTaxeBoisonTrimistrielle(boolean creationTaxeBoisonTrimistrielle) {
        this.creationTaxeBoisonTrimistrielle = creationTaxeBoisonTrimistrielle;
    }

    public boolean isConsultationTaxeBoisonTrimistrielle() {
        return consultationTaxeBoisonTrimistrielle;
    }

    public void setConsultationTaxeBoisonTrimistrielle(boolean consultationTaxeBoisonTrimistrielle) {
        this.consultationTaxeBoisonTrimistrielle = consultationTaxeBoisonTrimistrielle;
    }


    public boolean isCreationSecteure() {
        return creationSecteure;
    }

    public void setCreationSecteure(boolean creationSecteure) {
        this.creationSecteure = creationSecteure;
    }

    public boolean isConsultationSecteure() {
        return consultationSecteure;
    }

    public void setConsultationSecteure(boolean consultationSecteure) {
        this.consultationSecteure = consultationSecteure;
    }

    public boolean isCreationUser() {
        return creationUser;
    }

    public void setCreationUser(boolean creationUser) {
        this.creationUser = creationUser;
    }

    public boolean isConsultationUser() {
        return consultationUser;
    }

    public void setConsultationUser(boolean consultationUser) {
        this.consultationUser = consultationUser;
    }

    @Override
    public String toString() {
        return "bean.User[ login=" + login + " ]";
    }

}
