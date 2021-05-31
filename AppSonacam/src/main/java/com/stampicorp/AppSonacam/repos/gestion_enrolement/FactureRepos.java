package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactureRepos extends JpaRepository<Facture, Long> {

    List<Facture> findByEtatEquals(int etat);

    List<Facture> findByContribuableAndEtatEquals(Contribuable contribuable, int etat);

    Facture findByNumeroAndEtatEquals(String numero, int etat);

    @Query(value = "select count (u.id) from Facture u")
    Long getCountId();
}
