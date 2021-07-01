package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaiementRepo extends JpaRepository<Paiement, Long> {

    List<Paiement> findByEtatEqualsOrderById(int etat);

    List<Paiement> findByFactureAndEtatEqualsOrderById(Facture facture, int etat);

    List<Paiement> findByAuthorAndEtatEqualsOrderById(Utilisateur author, int etat);

    @Query(value = "select u from Paiement u where u.facture.contribuable.zone = :zone and u.etat = :etat")
    List<Paiement> findByZone(Zone zone, int etat);


    @Query(value = "select u from Paiement u where u.facture.contribuable.zone.agence = :agence and u.etat = :etat")
    List<Paiement> findByAgence(Agence agence, int etat);

    @Query(value = "select max(u.tranche) from Paiement u where u.facture = :facture")
    int getMaxTranche(Facture facture);

    @Query(value = "select count (u.id) from Paiement u")
    Long getCountId();

    @Query(value = "select count (u.id) from Paiement  u where u.author = :utilisateur and u.etat = :etat")
    Double getNombreTotalContribuableByUser(Utilisateur utilisateur, int etat);

    @Query(value = "select count (u.id) from Paiement  u where u.author = :utilisateur and u.date_save between :debut and :fin and u.etat = :etat")
    Long getNombreContribuableByUser(Utilisateur utilisateur, Date debut, Date fin, int etat);

    @Query(value = "select u from Paiement u where u.author = :utilisateur and u.date_save between :debut and :fin and u.etat = :etat")
    List<Paiement> getPaiementJourByAuthor(Utilisateur utilisateur, Date debut, Date fin, int etat);

    @Query(value = "select sum(u.montant) from Paiement u where u.author = :utilisateur and u.date_save between :debut and :fin and u.etat = :etat")
    Double getSoldeJourByAuthor(Utilisateur utilisateur, Date debut, Date fin, int etat);

    @Query(value = "select sum(u.montant) from Paiement u where u.author = :utilisateur  and u.etat = :etat")
    Double getSoldeByAuthor(Utilisateur utilisateur, int etat);


    // nombre de paiement total
    @Query(value = "select count (u.id) from Paiement u where u.etat = :etat")
    Long nombreFacture(int etat);

    // nombre de paiement par agence
    @Query(value = "select count (u.id) from Paiement u where u.facture.contribuable.zone.agence = :agence and u.etat = :etat")
    Long nombreFactureByAgence(Agence agence, int etat);

    // montant de paiement total;
    @Query(value = "select sum (u.montant) from Paiement u where u.etat = :etat")
    Double montantPaiement(int etat);

    // montant de paiement par agence
    @Query(value = "select sum (u.montant) from Paiement u where u.facture.contribuable.zone.agence = :agence and u.etat = :etat")
    Double montantPaiementByAgence(Agence agence, int etat);

}
