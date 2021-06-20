package com.stampicorp.AppSonacam.repos.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeRepos extends JpaRepository<Employe, Long> {

    Employe findByUser(Utilisateur user);
}
