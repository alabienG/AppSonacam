package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaysRepos extends JpaRepository<Pays, Long> {

    List<Pays> findByEtatEquals(int etat);

    Pays findByLibelleAndEtatEquals(String libelle, int etat);
}
