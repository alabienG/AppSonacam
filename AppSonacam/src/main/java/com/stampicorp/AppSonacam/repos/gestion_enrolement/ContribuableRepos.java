package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContribuableRepos extends JpaRepository<Contribuable, Long> {

    List<Contribuable> findByEtatEqualsOrderByIdDesc(int etat);

    List<Contribuable> findByActiviteAndEtatEqualsOrderByIdDesc(Activite activite, int etat);

    @Query(value = "select u from Contribuable u where u.zone.agence = :agence and u.etat = :etat")
    List<Contribuable> findByAgence(Agence agence, int etat);

    Contribuable findByNumeroAndEtatEquals(String numero, int etat);

    Contribuable findByCniAndEtatEquals(String cni, int etat);

    List<Contribuable> findByAuthorAndEtatEquals(Utilisateur author, int etat);

    @Query(value = "select count (u.id) from Contribuable u")
    Long getCountId();
}
