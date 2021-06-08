package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntenneRepos extends JpaRepository<Antenne, Long> {

    List<Antenne> findByEtatEquals(int etat);

    List<Antenne> findByAgenceAndEtatEquals(Agence agence, int etat);

    Antenne findByLibelleAndEtatEquals(String libelle, int etat);

}
