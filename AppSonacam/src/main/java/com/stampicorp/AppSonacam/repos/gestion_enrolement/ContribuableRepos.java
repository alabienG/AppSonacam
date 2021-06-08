package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContribuableRepos extends JpaRepository<Contribuable, Long> {

    List<Contribuable> findByEtatEqualsOrderByIdDesc(int etat);

    List<Contribuable> findByActiviteAndEtatEqualsOrderByIdDesc(Activite activite, int etat);

    Contribuable findByNumeroAndEtatEquals(String numero, int etat);

    Contribuable findByCniAndEtatEquals(String cni, int etat);

    @Query(value = "select count (u.id) from Contribuable u")
    Long getCountId();
}
