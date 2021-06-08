package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AgenceRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AgenceService {

    @Autowired
    AgenceRepos repos;

    @Autowired
    AntenneService antenneService;

    public List<Agence> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public List<Agence> findByPays(Long idPays) {
        return repos.findByPaysAndEtatEquals(new Pays(idPays), Constantes.ADD);
    }

    @Transactional
    public Agence create(Agence agence) {
        try {
            Agence agence1 = repos.findByLibelleAndEtatEquals(agence.getLibelle(), Constantes.ADD);
            if (agence1 != null ? agence1.getId() > 0 : false) {
                return new Agence("Cette agence existe déjà !");
            }

            agence.setEtat(Constantes.ADD);
            agence.setDateSave(new Date());
            agence.setDateUpdate(new Date());

            return repos.save(agence);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Agence(e.getMessage());
        }
    }

    @Transactional
    public Agence update(Agence agence) {
        try {
            Agence agence1 = repos.findByLibelleAndEtatEquals(agence.getLibelle(), Constantes.ADD);
            if (agence1 != null ? agence1.getId() != agence.getId() : false) {
                return new Agence("Cette agence existe déjà !");
            }

            agence.setDateUpdate(new Date());

            return repos.save(agence);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Agence(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            Agence agence = repos.getOne(id);

            List<Antenne> list = antenneService.allByAgence(agence.getId());
            if (list != null ? list.size() > 0 : false) {
                return "Cette agence ne peut pas être supprimer !";
            }
            agence.setEtat(Constantes.DELETE);
            agence.setDateUpdate(new Date());
            repos.save(agence);
            return "Suppression effectué avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
