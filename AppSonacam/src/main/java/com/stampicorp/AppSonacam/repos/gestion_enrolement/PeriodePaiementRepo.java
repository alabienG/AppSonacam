package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PeriodePaiementRepo extends JpaRepository<PeriodePaiement, Long> {

    List<PeriodePaiement> findByEtatEquals(int etat);

    PeriodePaiement findByFactureAndEtatEquals(Facture facture, int etat);

    @Query(value = "select u.facture from PeriodePaiement u where u.dateProchainPaiement between :debut and :fin and u.facture.etat = :etat and u.facture.statut = :statut")
    List<Facture> getFactureByDate(Date debut, Date fin, int etat,String statut);

    @Query(value = "select u.facture from PeriodePaiement u where u.dateProchainPaiement between :debut and :fin  and u.facture.contribuable.zone.agence = :agence" +
            " and u.facture.etat = :etat and u.facture.statut = :statut" )
    List<Facture> getFactureByDate(Date debut, Date fin, int etat, Agence agence, String statut);
}
