package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.FactureRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class FactureService {
    @Autowired
    FactureRepos repos;

    public List<Facture> list() {
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    public List<Facture> listByContribuable(Long idContribuable) {
        return repos.findByContribuableAndEtatEqualsOrderById(new Contribuable(idContribuable), Constantes.ADD);
    }

    public Facture findByNumero(String numero) {
        return repos.findByNumeroAndEtatEquals(numero, Constantes.ADD);
    }

    @Transactional
    public Facture create(Facture facture) {
        try {
            facture.setNumero(generatedNumero(facture));
            Facture f = repos.findByNumeroAndEtatEquals(facture.getNumero(), Constantes.ADD);
            if (f != null ? f.getId() > 0 : false) {
                return new Facture("Ce numéro de facture existe déjà !");
            }
            facture.setStatut(Constantes.STATUT_ATTENTE);
            facture.setEtat(Constantes.ADD);
            facture.setDate_save(new Date());
            facture.setDate_update(new Date());

            return repos.save(facture);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Facture(e.getMessage());
        }
    }

    @Transactional
    public Facture update(Facture facture) {
        try {
            Facture f = repos.findByNumeroAndEtatEquals(facture.getNumero(), Constantes.ADD);
            if (f != null ? f.getId() != facture.getId() : false) {
                return new Facture("Ce numéro de facture existe déjà !");
            }
            facture.setDate_update(new Date());

            return repos.save(facture);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Facture(e.getMessage());
        }
    }

    @Transactional
    public Facture valider(Facture facture) {
        try {
            facture.setDate_update(new Date());
            facture.setStatut(Constantes.STATUT_VALIDER);
            return repos.save(facture);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Facture(e.getMessage());
        }
    }

    @Transactional
    public Facture payer(Facture facture) {
        try {
            facture.setDate_update(new Date());
            facture.setStatut(Constantes.STATUT_PAYER);
            return repos.save(facture);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Facture(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            Facture facture = repos.getOne(id);
            facture.setEtat(Constantes.DELETE);
            facture.setDate_update(new Date());

            // on verifie si il y'a dejà des versements

            repos.save(facture);
            return "Suppression effectuée avec succès !";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }


    private String generatedNumero(Facture facture) {
        String numero = "SONACAM/FR/";
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
