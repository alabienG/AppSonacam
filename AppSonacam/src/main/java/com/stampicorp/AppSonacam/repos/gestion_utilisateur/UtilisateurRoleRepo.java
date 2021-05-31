package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.UtilisateurRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRoleRepo extends JpaRepository<UtilisateurRole, Long> {
    UtilisateurRole findByRoleAndUtilisateurAndEtatEquals(Role role, Utilisateur utilisateur, int add);
}
