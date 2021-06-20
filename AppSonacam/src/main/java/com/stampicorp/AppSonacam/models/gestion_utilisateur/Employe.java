package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agence")
    private Agence agence;

    private int etat;
    private Date dateSave;
    private Date dateUpdate;

    public Long getId() {
        return id != null ? id : 0;
    }

}
