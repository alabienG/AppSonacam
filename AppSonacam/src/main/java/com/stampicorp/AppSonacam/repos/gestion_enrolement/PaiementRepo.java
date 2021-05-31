package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepo extends JpaRepository<Paiement, Long> {

    List<Paiement> findByEtatEquals(int etat);

    List<Paiement> findByFactureAndEtatEquals(Facture facture, int etat);

    List<Paiement> findByAuthorAndEtatEquals(Utilisateur author, int etat);
}
