package com.stampicorp.AppSonacam.models.gestion_utilisateur;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "terminal_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;
    @ManyToOne(fetch = FetchType.EAGER)
    private Terminal terminal;

    @Transient
    private String message;

    public TerminalUser(String message) {
        this.message = message;
    }
}
