package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "versement")
public class Versement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double montant;
    private Utilisateur author;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "paiement")
    private Paiement paiement;
    private Date date_save;
    private Date date_update;
    private int etat;
    private Double numero;

    public Versement(Long id) {
        this.id = id;
    }

    public Versement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Utilisateur getAuthor() {
        return author;
    }

    public void setAuthor(Utilisateur author) {
        this.author = author;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }

    public Date getDate_save() {
        return date_save;
    }

    public void setDate_save(Date date_save) {
        this.date_save = date_save;
    }

    public Date getDate_update() {
        return date_update;
    }

    public void setDate_update(Date date_update) {
        this.date_update = date_update;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }
}