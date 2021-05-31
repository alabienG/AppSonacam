package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="type_utilisateur")
public class TypeUtilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;
    private String description;

    public TypeUtilisateur() {
    }

    public TypeUtilisateur(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}