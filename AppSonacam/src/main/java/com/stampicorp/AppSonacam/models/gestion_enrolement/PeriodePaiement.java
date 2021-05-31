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
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "facture")
    private Facture facture;

    public PeriodePaiement() {
    }

    public PeriodePaiement(Long id) {
        this.id = id;
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
