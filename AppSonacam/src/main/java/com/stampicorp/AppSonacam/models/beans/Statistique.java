package com.stampicorp.AppSonacam.models.beans;

import java.io.Serializable;

public class Statistique implements Serializable {
    private Long nombreVersement;
    private Double montantVersement;
    private Long nombreOrdre;
    private Long nombreOrdreTerminer;
    private Long nombreOrdreEncours;
    private Double montantOrdre;
    private Long nombreFacture;
    private Double montantFActure;
    private Double montantFactureTerminer;
    private Double montantFactureEncours;
    private Long nombrePaiement;
    private Double montantPaiement;
    private Long nombreZone;
    private Long nombreAgent;
    private Long nombreUsager;

    public Long getNombreUsager() {
        return nombreUsager;
    }

    public void setNombreUsager(Long nombreUsager) {
        this.nombreUsager = nombreUsager;
    }

    public Long getNombreZone() {
        return nombreZone;
    }

    public void setNombreZone(Long nombreZone) {
        this.nombreZone = nombreZone;
    }

    public Long getNombreAgent() {
        return nombreAgent;
    }

    public void setNombreAgent(Long nombreAgent) {
        this.nombreAgent = nombreAgent;
    }

    public Long getNombrePaiement() {
        return nombrePaiement;
    }

    public void setNombrePaiement(Long nombrePaiement) {
        this.nombrePaiement = nombrePaiement;
    }

    public Double getMontantPaiement() {
        return montantPaiement;
    }

    public void setMontantPaiement(Double montantPaiement) {
        this.montantPaiement = montantPaiement;
    }

    public Double getMontantFactureTerminer() {
        return montantFactureTerminer;
    }

    public void setMontantFactureTerminer(Double montantFactureTerminer) {
        this.montantFactureTerminer = montantFactureTerminer;
    }

    public Double getMontantFactureEncours() {
        return montantFactureEncours;
    }

    public void setMontantFactureEncours(Double montantFactureEncours) {
        this.montantFactureEncours = montantFactureEncours;
    }

    public Long getNombreVersement() {
        return nombreVersement;
    }

    public void setNombreVersement(Long nombreVersement) {
        this.nombreVersement = nombreVersement;
    }

    public Double getMontantVersement() {
        return montantVersement;
    }

    public void setMontantVersement(Double montantVersement) {
        this.montantVersement = montantVersement;
    }

    public Long getNombreOrdre() {
        return nombreOrdre;
    }

    public void setNombreOrdre(Long nombreOrdre) {
        this.nombreOrdre = nombreOrdre;
    }

    public Long getNombreOrdreTerminer() {
        return nombreOrdreTerminer;
    }

    public void setNombreOrdreTerminer(Long nombreOrdreTerminer) {
        this.nombreOrdreTerminer = nombreOrdreTerminer;
    }

    public Long getNombreOrdreEncours() {
        return nombreOrdreEncours;
    }

    public void setNombreOrdreEncours(Long nombreOrdreEncours) {
        this.nombreOrdreEncours = nombreOrdreEncours;
    }

    public Double getMontantOrdre() {
        return montantOrdre;
    }

    public void setMontantOrdre(Double montantOrdre) {
        this.montantOrdre = montantOrdre;
    }

    public Long getNombreFacture() {
        return nombreFacture;
    }

    public void setNombreFacture(Long nombreFacture) {
        this.nombreFacture = nombreFacture;
    }

    public Double getMontantFActure() {
        return montantFActure;
    }

    public void setMontantFActure(Double montantFActure) {
        this.montantFActure = montantFActure;
    }
}
