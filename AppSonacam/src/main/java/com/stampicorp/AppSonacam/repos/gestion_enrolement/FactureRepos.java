package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FactureRepos extends JpaRepository<Facture, Long> {

    List<Facture> findByEtatEqualsOrderById(int etat);

    List<Facture> findByContribuableAndEtatEqualsOrderById(Contribuable contribuable, int etat);

    Facture findByNumeroAndEtatEquals(String numero, int etat);

    @Query(value = "select u from Facture u where u.contribuable = :contribuable and u.date_save between :debut and :fin and u.etat =:etat")
    Facture findbyContribuableAndDate(Contribuable contribuable, Date debut, Date fin, int etat);

    @Query(value = "select count (u.id) from Facture u")
    Long getCountId();
}
