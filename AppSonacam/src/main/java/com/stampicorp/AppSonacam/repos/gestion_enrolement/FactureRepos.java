package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FactureRepos extends JpaRepository<Facture, Long> {

    List<Facture> findByEtatEqualsOrderById(int etat);

    List<Facture> findByContribuableAndEtatEqualsOrderById(Contribuable contribuable, int etat);

    @Query(value = "select u from Facture u where u.contribuable.zone = :zone and u.etat = :etat")
    List<Facture> findByZone(Zone zone, int etat);

    @Query(value = "select u from Facture u where u.contribuable.zone.agence = :agence and u.etat = :etat")
    List<Facture> findByAgence(Agence agence, int etat);

    Facture findByNumeroAndEtatEquals(String numero, int etat);

    @Query(value = "select u from Facture u where u.contribuable = :contribuable and u.date_save between :debut and :fin and u.etat =:etat")
    Facture findbyContribuableAndDate(Contribuable contribuable, Date debut, Date fin, int etat);

    @Query(value = "select count (u.id) from Facture u")
    Long getCountId();

    @Query(value = "select count (u.id) from Facture u where u.etat = :etat")
    Long nombreOrdre(int etat);

    @Query(value = "select count (u.id) from Facture u where u.contribuable.zone.agence = :agence and u.etat = :etat")
    Long nombreOrdreByAgence(Agence agence,  int etat);

    @Query(value = "select count (u.id) from Facture u where u.statut = :statut and u.etat = :etat")
    Long nombreOrdreByStatut(String statut, int etat);

    @Query(value = "select count (u.id) from Facture u where u.contribuable.zone.agence = :agence and u.statut = :statut and u.etat = :etat")
    Long nombreOrdreByStatutAndAgence(Agence agence, String statut, int etat);

    @Query(value = "select sum(u.montant) from Facture u where u.statut = :statut and u.etat = :etat")
    Double montantOrdreByStatut(String statut , int etat);

    @Query(value = "select sum(u.montant) from Facture u where u.contribuable.zone.agence = :agence and u.statut = :statut and u.etat = :etat")
    Double montantOrdreByStatut(Agence agence, String statut , int etat);

    @Query(value = "select sum(u.montant) from Facture u where u.etat = :etat")
    Double montantOrdre(int etat);

    @Query(value = "select sum(u.montant) from Facture u where u.contribuable.zone.agence = :agence and u.etat = :etat")
    Double montantOrdreByAgence(Agence agence, int etat);


}
