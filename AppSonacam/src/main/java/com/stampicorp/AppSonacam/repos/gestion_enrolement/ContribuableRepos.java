package com.stampicorp.AppSonacam.repos.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContribuableRepos extends JpaRepository<Contribuable, Long> {
}
