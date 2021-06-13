package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepo extends JpaRepository<Paiement, Long> {

    List<Paiement> findByEtatEqualsOrderById(int etat);

    List<Paiement> findByFactureAndEtatEqualsOrderById(Facture facture, int etat);

    List<Paiement> findByAuthorAndEtatEqualsOrderById(Utilisateur author, int etat);

    @Query(value = "select max(u.tranche) from Paiement u where u.facture = :facture")
    int getMaxTranche(Facture facture);

    @Query(value = "select count (u.id) from Paiement u")
    Long getCountId();


}
