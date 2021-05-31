package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActiviteRepo extends JpaRepository<Activite, Long> {

    List<Activite> findByEtatEquals(int etat);

    Activite findByLibelleAndEtatEquals(String libelle, int etat);
}
