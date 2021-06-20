package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Delegation;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.PaysRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaysService {

    @Autowired
    PaysRepos repos;

    @Autowired
    DelegationService delegationService;

    public List<Pays> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public Pays create(Pays pays) {
        try {
            Pays p = repos.findByLibelleAndEtatEquals(pays.getLibelle(), Constantes.ADD);
            if (p != null ? p.getId() > 0 : false) {
                return new Pays("Ce pays existe déjà !");
            }

            pays.setEtat(Constantes.ADD);
            pays.setDateSave(new Date());
            pays.setDateUpdate(new Date());

            return repos.save(pays);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Pays(e.getMessage());
        }
    }

    public Pays update(Pays pays) {
        try {
            Pays p = repos.findByLibelleAndEtatEquals(pays.getLibelle(), Constantes.ADD);
            if (p != null ? p.getId() != pays.getId() : false) {
                return new Pays("Ce pays existe déjà !");
            }

            pays.setDateUpdate(new Date());

            return repos.save(pays);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Pays(e.getMessage());
        }
    }

    public String delete(Long id) {
        try {
            Pays pays = repos.getOne(id);
            List<Delegation> list = delegationService.allByPays(pays.getId());
            if (list != null ? list.size() > 0 : false) {
                return "Ce pays ne peut pas être supprimé  !";
            }
            pays.setEtat(Constantes.DELETE);
            pays.setDateUpdate(new Date());

            repos.save(pays);
            return "Suppression effectuée avec succès !";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
