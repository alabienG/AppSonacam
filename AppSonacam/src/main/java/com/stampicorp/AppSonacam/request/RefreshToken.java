package com.stampicorp.AppSonacam.request;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Utilisateur user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
