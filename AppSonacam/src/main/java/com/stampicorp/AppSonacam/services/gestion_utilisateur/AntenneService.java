package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AntenneRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AntenneService {

    @Autowired
    AntenneRepos repos;

    @Autowired
    ZoneService zoneService;

    public List<Antenne> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public List<Antenne> allByAgence(Long idAgence) {
        return repos.findByAgenceAndEtatEquals(new Agence(idAgence), Constantes.ADD);
    }

    @Transactional
    public Antenne create(Antenne antenne) {
        try {
            Antenne antenne1 = repos.findByLibelleAndEtatEquals(antenne.getLibelle(), Constantes.ADD);
            if (antenne1 != null ? antenne1.getId() > 0 : false) {
                return new Antenne("Cette antenne existe déjà !");
            }

            antenne.setEtat(Constantes.ADD);
            antenne.setDateSave(new Date());
            antenne.setDateUpdate(new Date());

            return repos.save(antenne);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Antenne(e.getMessage());
        }
    }

    @Transactional
    public Antenne update(Antenne antenne) {
        try {
            Antenne antenne1 = repos.findByLibelleAndEtatEquals(antenne.getLibelle(), Constantes.ADD);
            if (antenne1 != null ? antenne1.getId() != antenne.getId() : false) {
                return new Antenne("Cette antenne existe déjà !");
            }

            antenne.setDateUpdate(new Date());

            return repos.save(antenne);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Antenne(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            Antenne antenne = repos.getOne(id);
            antenne.setEtat(Constantes.DELETE);
            antenne.setDateUpdate(new Date());
            repos.save(antenne);
            return "Suppression effectué avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
