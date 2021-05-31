package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodePaiementRepo extends JpaRepository<PeriodePaiement, Long> {

    List<PeriodePaiement> findByEtatEquals(int etat);

    PeriodePaiement findByFactureAndEtatEquals(Facture facture, int etat);
}
