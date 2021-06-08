package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepo extends JpaRepository<Zone, Long> {
    Optional<Zone> findZoneById(Long id);

    List<Zone> findByEtatEquals(int etat);

    List<Zone> findByAntenneAndEtatEquals(Antenne antenne, int etat);

    Zone findByLibelleAndEtatEquals(String libelle, int add);


}
