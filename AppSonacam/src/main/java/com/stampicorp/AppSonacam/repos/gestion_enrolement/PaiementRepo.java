package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepo extends JpaRepository<Paiement, Long> {
}
