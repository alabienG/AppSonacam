package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "delegation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delegation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pays")
    private Pays pays;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;

    @Transient
    private String message;


    public Delegation(String message) {
        this.message = message;
    }

    public Delegation(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id != null ? id : 0;
    }
}
