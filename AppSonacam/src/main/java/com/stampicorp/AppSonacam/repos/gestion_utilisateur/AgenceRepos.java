package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenceRepos extends JpaRepository<Agence, Long> {

    List<Agence> findByEtatEquals(int etat);

    List<Agence> findByPaysAndEtatEquals(Pays pays, int etat);

    Agence findByLibelleAndEtatEquals(String libelle, int etat);

}
