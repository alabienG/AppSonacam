package com.stampicorp.AppSonacam.models.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "versement")
public class Versement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double montant;
    private Utilisateur author;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "paiement")
    private Paiement paiement;
    private Date date_save;
    private Date date_update;
    private int etat;
}
