package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ContribuableRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ContribuableService {
    @Autowired
    ContribuableRepos repos;

    public List<Contribuable> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public List<Contribuable> allByActivite(Long idActivite) {
        return repos.findByActiviteAndEtatEquals(new Activite(idActivite), Constantes.ADD);
    }

    public Contribuable findByNumero(String numero) {
        try {
            return repos.findByNumeroAndEtatEquals(numero, Constantes.ADD);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    public Contribuable getOne(Long id) {
        return repos.getOne(id);
    }

    @Transactional
    public Contribuable create(Contribuable contribuable) {
        try {
            contribuable.setNumero(generatedNumero(contribuable));
            Contribuable c = repos.findByNumeroAndEtatEquals(contribuable.getNumero(), Constantes.ADD);
            if (c != null ? c.getId() > 0 : false) {
                return new Contribuable("Un contribuable existe déjà avec ce numéro !");
            }
            if (contribuable.getCni() != null ? contribuable.getCni().length() > 0 : false) {
                c = repos.findByCniAndEtatEquals(contribuable.getCni(), Constantes.ADD);
                if (c != null ? c.getId() > 0 : false) {
                    return new Contribuable("Un contribuable existe déjà avec ce numéro de CNI !");
                }
            }
            contribuable.setEtat(Constantes.ADD);
            contribuable.setDate_save(new Date());
            contribuable.setDate_update(new Date());
            return repos.save(contribuable);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    @Transactional
    public Contribuable update(Contribuable contribuable) {
        try {
            Contribuable c = repos.findByNumeroAndEtatEquals(contribuable.getNumero(), Constantes.ADD);
            // on verifie que le numéro n'appartient pas déjà à un contribuable
            if (c != null ? c.getId() != contribuable.getId() : false) {
                return new Contribuable("Un contribuable existe déjà avec ce numéro !");
            }
            // on verifie que le numero de cni n'appartient pas déjà à un contribuable
            if (contribuable.getCni() != null ? contribuable.getCni().length() > 0 : false) {
                c = repos.findByCniAndEtatEquals(contribuable.getCni(), Constantes.ADD);
                if (c != null ? c.getId() != contribuable.getId() : false) {
                    return new Contribuable("Un contribuable existe déjà avec ce numéro de CNI !");
                }
            }
            contribuable.setDate_update(new Date());
            return repos.save(contribuable);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            // on cherche les factures du contribuable

            Contribuable contribuable = repos.getOne(id);
            contribuable.setEtat(Constantes.DELETE);
            contribuable.setDate_update(new Date());
            repos.save(contribuable);
            return "Suppression effectuée avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }


    private String generatedNumero(Contribuable contribuable) {
        String numero = "SONACAM/";
        Long id = repos.getCountId() + 1;
        if (id < 10) {
            return numero + "00" + id;
        } else if (id > 10 && id < 100) {
            return numero + "0" + id;
        } else {
            return numero + id.toString();
        }
    }
}
