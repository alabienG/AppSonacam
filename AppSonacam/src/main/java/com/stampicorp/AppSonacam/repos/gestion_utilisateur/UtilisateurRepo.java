package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByEtatEqualsOrderById(int etat);

    List<Utilisateur> findByZoneAndEtatEqualsOrderById(Zone zone, int etat);

    @Query(value = "select count (u.id) from Utilisateur u")
    Long getCountId();

}
