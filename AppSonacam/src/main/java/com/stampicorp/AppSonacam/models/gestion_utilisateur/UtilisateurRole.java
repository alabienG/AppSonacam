package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name= "utilisateur_role")
public class UtilisateurRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="utilisateur")
    private Utilisateur utilisateur;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role")
    private Role role;

    public UtilisateurRole() {
    }

    public UtilisateurRole(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
