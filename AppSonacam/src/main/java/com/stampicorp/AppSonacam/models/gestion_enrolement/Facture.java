package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="facture")
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String numero;
    private String statut;
    private Double Montant;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "contribuable")
    private Contribuable contribuable;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur author;

    public Facture() {
    }

    public Facture(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
        return Montant;
    }

    public void setMontant(Double montant) {
        Montant = montant;
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
}
