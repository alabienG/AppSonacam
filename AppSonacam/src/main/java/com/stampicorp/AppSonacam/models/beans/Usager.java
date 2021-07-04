package com.stampicorp.AppSonacam.models.beans;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;

public class Usager {
    Contribuable contribuable;
    Facture facture;
    PeriodePaiement periode;

    public Contribuable getContribuable() {
        return contribuable;
    }

    public void setContribuable(Contribuable contribuable) {
        this.contribuable = contribuable;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public PeriodePaiement getPeriode() {
        return periode;
    }

    public void setPeriode(PeriodePaiement periode) {
        this.periode = periode;
    }
}
