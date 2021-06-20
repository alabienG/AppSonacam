package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "utilisateur")
    private Utilisateur user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "zone")
    private Zone zone;
    private int etat;
    private Date dateSave;
    private Date dateUpdate;

    public Long getId() {
        return id != null ? id : 0;
    }


}
