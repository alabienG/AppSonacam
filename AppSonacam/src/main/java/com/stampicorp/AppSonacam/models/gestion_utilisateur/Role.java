package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import com.stampicorp.AppSonacam.utils.ERole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="role")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole libelle;
    private String description;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;
    @Transient
    private String message;

    public Role() {
    }

    public Role(ERole libelle) {
        this.libelle = libelle;
    }

    public Role(ERole libelle, String description, int etat, Date dateSave, Date dateUpdate) {
        this.libelle = libelle;
        this.description = description;
        this.etat = etat;
        this.dateSave = dateSave;
        this.dateUpdate = dateUpdate;
    }

    public Role(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id != null ? id : 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getLibelle() {
        return libelle;
    }

    public void setLibelle(ERole libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDateSave() {
        return dateSave;
    }

    public void setDateSave(Date dateSave) {
        this.dateSave = dateSave;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return libelle == role.libelle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(libelle);
    }
}
