package com.stampicorp.AppSonacam.models.gestion_enrolement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="periode_paiement")
public class PeriodePaiement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date dateDebut;
    private Date dateFin;
    private Date dateProchainPaiement;
    private int nombreTranche;
    private Date date_save;
    private Date date_update;
    private int etat;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "facture")
    private Facture facture;

    public PeriodePaiement() {
    }

    public PeriodePaiement(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateProchainPaiement() {
        return dateProchainPaiement;
    }

    public void setDateProchainPaiement(Date dateProchainPaiement) {
        this.dateProchainPaiement = dateProchainPaiement;
    }

    public int getNombreTranche() {
        return nombreTranche;
    }

    public void setNombreTranche(int nombreTranche) {
        this.nombreTranche = nombreTranche;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}
