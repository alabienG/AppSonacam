package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DelegationRepos extends JpaRepository<Delegation, Long> {

    List<Delegation> findByEtatEqualsOrderById(int etat);

    Delegation findByLibelleAndEtatEquals(String libelle, int etat);
}
