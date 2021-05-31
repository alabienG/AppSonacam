package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.TypeUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeUtilisateurRepo extends JpaRepository<TypeUtilisateur, Long> {
    List<TypeUtilisateur> findByEtatEquals(int add);

    TypeUtilisateur findByLibelleAndEtatEquals(String libelle, int add);
}
