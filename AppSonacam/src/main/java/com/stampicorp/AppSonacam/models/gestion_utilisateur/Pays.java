package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pays")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pays implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;

    @Transient
    private String message;

    public Pays(Long id) {
        this.id = id;
    }

    public Pays(String message) {
        this.message = message;
    }

    public Long getId() {
        return id != null ? id : 0;
    }
}
