package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepos extends JpaRepository<Facture, Long> {
}
