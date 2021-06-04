package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneRepo extends JpaRepository<Zone, Long> {
    Optional<Zone> findZoneById(Long id);

    List<Zone> findByEtatEquals(int etat);

    Zone findByLibelleAndEtatEquals(String libelle, int add);

    @Query(value = "select u from Zone u where u.arrondissement.id > 0 and u.etat = :etat order by u.id desc ")
    List<Zone> listeSecteur(int etat);

    @Query(value = "select u from Zone u where u.departement.id > 0 and u.arrondissement.id <1 and u.etat = :etat order by u.id desc ")
    List<Zone> listeArrondissement(int etat);

    @Query(value = "select u from Zone u where u.region.id > 0 and u.departement.id < 1 and u.etat = :etat order by u.id desc ")
    List<Zone> listeDepartement(int etat);

    @Query(value = "select u from Zone u where u.national.id > 0 and u.region.id < 1 and u.etat = :etat order by u.id desc ")
    List<Zone> listepays(int etat);
}
