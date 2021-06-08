package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersementRepos extends JpaRepository<Versement, Long> {

    List<Versement> findByEtatEqualsOrderById(int etat);

    @Query(value = "select u from Versement u where u.paiement.facture = :facture and u.etat = :etat order by u.id desc ")
    List<Versement> allByFacture(Facture facture, int etat);

    List<Versement> findByAuthorAndEtatEqualsOrderById(Utilisateur author, int etat);

    Versement findByNumeroAndEtatEquals(String numero, int etat);

    Versement findByPaiementAndEtatEquals(Paiement paiement, int etat);

    @Query(value = "select count (u.id) from Versement u")
    Long getCountId();


}
