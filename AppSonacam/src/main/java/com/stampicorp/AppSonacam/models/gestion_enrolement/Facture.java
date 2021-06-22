package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "facture")
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String statut;
    private Double montant;
    private Date date_save;
    private Date date_update;
    private int etat;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contribuable")
    private Contribuable contribuable;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur author;
    @Transient
    public String message;
    @Transient
    private Date date_prochain;

    public Facture() {
    }

    public Facture(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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

    public Facture(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id != null ? id : 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Contribuable getContribuable() {
        return contribuable;
    }

    public void setContribuable(Contribuable contribuable) {
        this.contribuable = contribuable;
    }

    public Utilisateur getAuthor() {
        return author;
    }

    public void setAuthor(Utilisateur author) {
        this.author = author;
    }

    public Date getDate_prochain() {
        return date_prochain;
    }

    public void setDate_prochain(Date date_prochain) {
        this.date_prochain = date_prochain;
    }
}
