package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "paiement")
public class Paiement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    private int tranche;
    private String numero;
    private Date date_save;
    private Date date_update;
    private int etat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facture")
    private Facture facture;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur author;
    @Transient
    private String message;

    public Paiement() {
    }

    public Paiement(String message) {
        this.message = message;
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

    public Paiement(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id != null ? id : 0;
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

    public int getTranche() {
        return tranche;
    }

    public void setTranche(int tranche) {
        this.tranche = tranche;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Utilisateur getAuthor() {
        return author;
    }

    public void setAuthor(Utilisateur author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
