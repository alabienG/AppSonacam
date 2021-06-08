package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Terminal;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerminalRepos extends JpaRepository<Terminal, Long> {

    List<Terminal> findByEtatEqualsOrderById(int etat);

    Terminal findByNumeroSerieAndEtatEquals(String numeroSerie, int etat);
}
