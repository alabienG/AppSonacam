package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.utils.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByLibelleAndEtatEquals(ERole libelle, int etat);

    Optional<Role> findByLibelle(ERole libelle);

    Optional<Role> findRoleById(Long id);

    List<Role> findByEtatEquals(int etat);
}
