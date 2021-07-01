package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersementRepos extends JpaRepository<Versement, Long> {

    List<Versement> findByEtatEqualsOrderById(int etat);

    @Query(value = "select u from Versement u where u.paiement.facture = :facture and u.etat = :etat order by u.id desc ")
    List<Versement> allByFacture(Facture facture, int etat);

    @Query(value = "select u from Versement u where u.paiement.facture.contribuable.zone = :zone and u.etat = :etat order by u.id desc ")
    List<Versement> findByZone(Zone zone, int etat);

    @Query(value = "select u from Versement u where u.paiement.facture.contribuable.zone.agence = :agence and u.etat = :etat order by u.id desc ")
    List<Versement> findByAgence(Agence agence, int etat);

    List<Versement> findByAuthorAndEtatEqualsOrderById(Utilisateur author, int etat);

    Versement findByNumeroAndEtatEquals(String numero, int etat);

    Versement findByPaiementAndEtatEquals(Paiement paiement, int etat);

    @Query(value = "select count (u.id) from Versement u")
    Long getCountId();

    @Query(value = "select count(u.id) from Versement u where u.statut = :statut and u.etat = :etat")
    Long countAllVersement(String statut, int etat);

    @Query(value = "select sum(u.montant) from Versement u where u.statut = :statut and u.etat = :etat")
    Double montantVersement(String statut, int etat);

    @Query(value = "select count(u.id) from Versement u where u.paiement.facture.contribuable.zone.agence = :agence and u.etat = :etat and u.statut = :statut")
    Long countVersementByAgence(Agence agence, String statut, int etat);

    @Query(value = "select sum(u.montant) from Versement u where u.paiement.facture.contribuable.zone.agence = :agence and u.etat = :etat and u.statut = :statut")
    Double montantVersementByAgence(Agence agence, String statut, int etat);


}
