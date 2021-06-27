package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepo extends JpaRepository<Zone, Long> {
    Optional<Zone> findZoneById(Long id);

    List<Zone> findByEtatEquals(int etat);

    List<Zone> findByAgenceAndEtatEquals(Agence agence, int etat);

    Zone findByLibelleAndEtatEquals(String libelle, int add);

    @Query(value = "select count (u.id) from Zone u where u.etat = :etat")
    Long nombreZone(int etat);

    @Query(value = "select count (u.id) from Zone u where u.agence = :agence and u.etat = :etat")
    Long nombreZone(Agence agence, int etat);


}
