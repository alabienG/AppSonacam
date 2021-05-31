package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZoneRepo extends JpaRepository<Zone, Long> {
    Optional<Zone> findZoneById(Long id);

    List<Zone> findByEtatEquals(int etat);

    Zone findByLibelleAndEtatEquals(String libelle, int add);
}
