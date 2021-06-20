package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Delegation;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.DelegationRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DelegationService {

    @Autowired
    DelegationRepos repos;
    @Autowired
    AgenceService agenceService;

    public List<Delegation> all() {
        List<Delegation> list = repos.findByEtatEqualsOrderById(Constantes.ADD);
        return list;
    }

    public List<Delegation> allByPays(Long idPays) {
        return null;
    }

    @Transactional
    public Delegation create(Delegation delegation) {
        try {
            Delegation del = repos.findByLibelleAndEtatEquals(delegation.getLibelle(), Constantes.ADD);
            if (del != null ? del.getId() > 0 : false) {
                return new Delegation("Cette délégation existe déjà, Veuillez choisir un autre nom !");
            }

            delegation.setEtat(Constantes.ADD);
            delegation.setDateSave(new Date());
            delegation.setDateUpdate(new Date());

            return repos.save(delegation);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Delegation(e.getMessage());
        }
    }

    @Transactional
    public Delegation update(Delegation delegation) {
        try {
            Delegation del = repos.findByLibelleAndEtatEquals(delegation.getLibelle(), Constantes.ADD);
            if (del != null ? del.getId() != delegation.getId() : false) {
                return new Delegation("Cette délégation existe déjà, Veuillez choisir un autre nom !");
            }

            delegation.setDateUpdate(new Date());

            return repos.save(delegation);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Delegation(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            List<Agence> list = agenceService.findByDelegation(id);
            if (list != null ? list.size() > 0 : false) {
                return "Cette délégation ne peut pas être supprimer !";
            }
            Delegation delegation = repos.getOne(id);

            delegation.setEtat(Constantes.DELETE);
            delegation.setDateUpdate(new Date());
            repos.save(delegation);

            return "Suppression effectuée avec succès !";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
