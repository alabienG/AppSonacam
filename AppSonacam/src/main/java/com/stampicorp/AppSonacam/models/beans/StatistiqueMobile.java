package com.stampicorp.AppSonacam.models.beans;

import java.io.Serializable;

public class StatistiqueMobile implements Serializable {

    private Long nombre_paiement;
    private Long nombre_usager;
    private Double montant_jour;
    private Double montant_total;

    public Long getNombre_paiement() {
        return nombre_paiement;
    }

    public void setNombre_paiement(Long nombre_paiement) {
        this.nombre_paiement = nombre_paiement;
    }

    public Long getNombre_usager() {
        return nombre_usager;
    }

    public void setNombre_usager(Long nombre_usager) {
        this.nombre_usager = nombre_usager;
    }

    public Double getMontant_jour() {
        return montant_jour;
    }

    public void setMontant_jour(Double montant_jour) {
        this.montant_jour = montant_jour;
    }

    public Double getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(Double montant_total) {
        this.montant_total = montant_total;
    }
}
