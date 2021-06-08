package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "terminal")
@Data
@AllArgsConstructor
public class Terminal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String numeroSerie;
    private String couleur;
    private String systeme;
    private String version;
    private String modele;
    private String marque;
    private Date dateSave;
    private Date dateUpdate;
    private int etat;
    @Transient
    private String message;

    public Terminal() {
    }

    public Terminal(Long id) {
        this.id = id;
    }

    public Terminal(String message) {
        this.message = message;
    }

    public Long getId() {
        return id != null ? id : 0;
    }
}
