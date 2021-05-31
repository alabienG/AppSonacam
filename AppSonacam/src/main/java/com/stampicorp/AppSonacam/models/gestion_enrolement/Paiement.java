package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="paiement")
public class Paiement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double Montant;
    private int tranche;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "facture")
    private Facture facture;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur author;

    public Paiement() {
    }

    public Paiement(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return Montant;
    }

    public void setMontant(Double montant) {
        Montant = montant;
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
}
