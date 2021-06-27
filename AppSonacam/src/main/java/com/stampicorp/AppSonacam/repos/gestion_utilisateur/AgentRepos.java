package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agent;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepos extends JpaRepository<Agent, Long> {

    Agent findByUser(Utilisateur user);

    @Query(value = "select count (u.id) from Agent u where u.zone.agence = :agence and u.etat = :etat")
    Long nombreAgent(Agence agence, int etat);
}
