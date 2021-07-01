package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContribuableRepos extends JpaRepository<Contribuable, Long> {

    List<Contribuable> findByEtatEqualsOrderByIdDesc(int etat);

    List<Contribuable> findByActiviteAndEtatEqualsOrderByIdDesc(Activite activite, int etat);

    @Query(value = "select u from Contribuable u where u.zone.agence = :agence and u.etat = :etat")
    List<Contribuable> findByAgence(Agence agence, int etat);

    Contribuable findByNumeroAndEtatEquals(String numero, int etat);

    Contribuable findByCniAndEtatEquals(String cni, int etat);

    List<Contribuable> findByAuthorAndEtatEqualsOrderById(Utilisateur author, int etat);

    @Query(value = "select count (u.id) from Contribuable  u where u.author = :utilisateur and u.etat = :etat")
    Long getNombreTotalContribuableByUser(Utilisateur utilisateur, int etat);

    @Query(value = "select count (u.id) from Contribuable  u where u.author = :utilisateur and u.date_save between :debut and :fin and u.etat = :etat")
    Double getNombreContribuableByUser(Utilisateur utilisateur, Date debut, Date fin, int etat);

    @Query(value = "select count (u.id) from Contribuable u")
    Long getCountId();

    @Query(value = "select count (u.id) from Contribuable u where u.etat = :etat")
    Long nombreUsager(int etat);

    @Query(value = "select count (u.id) from Contribuable u where u.zone.agence = :agence and u.etat = :etat")
    Long nombreUsagerByAgence(Agence agence, int etat);

    //    @Query(value = "select u from Contribuable u where u.montant is null and u.zone = :zone and u.etat= :etat")
    List<Contribuable> findByMontantIsNullAndZoneAndEtatEquals(Zone zone, int etat);

    @Query(value = "select u.image1 from Contribuable u where u.id = :id")
    String getImage1(Long id);

    @Query(value = "select u.image2 from Contribuable u where u.id = :id")
    String getImage2(Long id);

    @Query(value = "select u.image3 from Contribuable u where u.id = :id")
    String getImage3(Long id);
}
