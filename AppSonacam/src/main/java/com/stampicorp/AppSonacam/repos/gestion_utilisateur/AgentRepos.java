package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agent;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepos extends JpaRepository<Agent, Long> {
    Agent findByUser(Utilisateur user);
}
