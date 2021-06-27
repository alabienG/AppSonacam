package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.UserRoles;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepo extends JpaRepository<Utilisateur, Long> {

    List<Utilisateur> findByEtatEqualsOrderById(int etat);

//    List<Utilisateur> findByZoneAndEtatEqualsOrderById(Zone zone, int etat);

    @Query(value = "select count (u.id) from Utilisateur u")
    Long getCountId();

    Optional<Utilisateur> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "insert into user_roles (user_id, role_id) values ( :idUser, :idRole )", nativeQuery = true)
    boolean addRole(@Param("idUser") Long idUser, @Param("idRole") Long idRole);

    @Query(value = "select count (u.id) from Utilisateur u where u.agent = true and u.etat = :etat")
    Long nombreAgent(int etat);
}
