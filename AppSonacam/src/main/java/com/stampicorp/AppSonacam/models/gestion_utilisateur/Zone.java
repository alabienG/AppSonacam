package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Zone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "national")
    private Zone national;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "region")
    private Zone region;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "departement")
    private Zone departement;
    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name= "arrondissement")
    private Zone arrondissement;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;

    public Zone() {
    }

    public Zone(String libelle) {
        this.libelle = libelle;
    }

    public Zone(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Zone getNational() {
        return national;
    }

    public void setNational(Zone national) {
        this.national = national;
    }

    public Zone getRegion() {
        return region;
    }

    public void setRegion(Zone region) {
        this.region = region;
    }

    public Zone getDepartement() {
        return departement;
    }

    public void setDepartement(Zone departement) {
        this.departement = departement;
    }

    public Zone getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(Zone arrondissement) {
        this.arrondissement = arrondissement;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Date getDateSave() {
        return dateSave;
    }

    public void setDateSave(Date dateSave) {
        this.dateSave = dateSave;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
