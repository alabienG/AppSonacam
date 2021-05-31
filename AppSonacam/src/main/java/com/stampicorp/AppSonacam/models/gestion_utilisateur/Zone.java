package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import javax.persistence.*;
import java.io.Serializable;
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

    public Zone() {
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
}
