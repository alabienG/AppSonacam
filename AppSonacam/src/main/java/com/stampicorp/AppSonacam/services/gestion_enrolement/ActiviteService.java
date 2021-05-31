package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ActiviteRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ActiviteService {
    @Autowired
    ActiviteRepo repos;

    @Autowired
    ContribuableService contribuableService;

    public List<Activite> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    @Transactional
    public Activite create(Activite activite) {
        try {
            Activite a = repos.findByLibelleAndEtatEquals(activite.getLibelle(), Constantes.ADD);
            if (a != null ? a.getId() > 0 : false) {
                return new Activite("Cette activité existe déjà !");
            } else {
                activite.setEtat(Constantes.ADD);
                activite.setDate_save(new Date());
                activite.setDate_update(new Date());
                return repos.save(activite);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Activite(e.getMessage());
        }
    }

    @Transactional
    public Activite update(Activite activite) {
        try {
            Activite a = repos.findByLibelleAndEtatEquals(activite.getLibelle(), Constantes.ADD);
            if (a != null ? a.getId() != activite.getId() : false) {
                return new Activite("Cette activité existe déjà !");
            } else {
                activite.setDate_update(new Date());
                return repos.save(activite);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Activite(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {

             /*
                     on doit verifier que cette activite n'est pas encore attribuer à un contribuable
              */
            Activite activite = repos.getOne(id);
            List<Contribuable> list = contribuableService.allByActivite(activite.getId());
            if (list != null ? !list.isEmpty() : false) {
                // cette activité est déjà utilisé !
                return "cette activité est déjà utilisée";
            } else {
                activite.setEtat(Constantes.DELETE);
                activite.setDate_update(new Date());
                repos.save(activite);
                return "Suppression effectuée avec succès";
            }

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
