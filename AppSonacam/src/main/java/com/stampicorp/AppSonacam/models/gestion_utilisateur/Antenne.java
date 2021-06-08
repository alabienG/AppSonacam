package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "antenne")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Antenne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agence")
    private Agence agence;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chef_antenne")
    private Utilisateur chef;

    @Transient
    private String message;

    public Antenne(Long id) {
        this.id = id;
    }

    public Antenne(String message) {
        this.message = message;
    }

    public Long getId() {
        return id != null ? id : 0;
    }
}
