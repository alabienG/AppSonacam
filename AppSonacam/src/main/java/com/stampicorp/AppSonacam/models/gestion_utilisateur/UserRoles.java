package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

//@Entity
//@Table(name = "user_roles")
public class UserRoles implements Serializable {
    private Long idUser;
    private Long idRole;

    public UserRoles(Long idUser, Long idRole) {
        this.idUser = idUser;
        this.idRole = idRole;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
}
