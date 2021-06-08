package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "agence")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Agence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_agence")
    private Utilisateur chef;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pays")
    private Pays pays;

    @Transient
    private String message;

    public Agence(String message) {
        this.message = message;
    }

    public Agence(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id != null ? id : 0;
    }
}
