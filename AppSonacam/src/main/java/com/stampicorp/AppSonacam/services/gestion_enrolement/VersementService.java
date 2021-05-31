package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.VersementRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class VersementService {

    @Autowired
    VersementRepos repos;

    public List<Versement> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public List<Versement> allByFacture(Long idFacture) {
        return repos.allByFacture(new Facture(idFacture), Constantes.ADD);
    }

    public List<Versement> allByUtilisateur(Long idUser) {
        return repos.findByAuthorAndEtatEquals(new Utilisateur(idUser), Constantes.ADD);
    }

    @Transactional
    public Versement create(Versement versement) {
        try {
            versement.setNumero(generatedNumero());
            Versement v = repos.findByNumeroAndEtatEquals(versement.getNumero(), Constantes.ADD);
            if (v != null ? v.getId() > 0 : false) {
                return new Versement("Ce numéro est déjà utilisé par une autre pièce !");
            }

            v = repos.findByPaiementAndEtatEquals(versement.getPaiement(), Constantes.ADD);
            if (v != null ? v.getId() > 0 : false) {
                return new Versement("Ce paiement est déjà encaisser !");
            }
            versement.setEtat(Constantes.ADD);
            versement.setStatut(Constantes.STATUT_ATTENTE);
            versement.setDate_save(new Date());
            versement.setDate_update(new Date());

            return repos.save(versement);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Versement(e.getMessage());
        }
    }

    @Transactional
    public Versement valider(Versement versement) {
        try {
            versement.setStatut(Constantes.STATUT_VALIDER);
            versement.setDate_update(new Date());
            return repos.save(versement);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Versement(e.getMessage());
        }
    }

    @Transactional
    public Versement update(Versement versement) {
        try {
            Versement v = repos.findByNumeroAndEtatEquals(versement.getNumero(), Constantes.ADD);
            if (v != null ? v.getId() != versement.getId() : false) {
                return new Versement("Ce numéro est déjà utilisé par une autre pièce !");
            }

            v = repos.findByPaiementAndEtatEquals(versement.getPaiement(), Constantes.ADD);
            if (v != null ? v.getId() != versement.getId() : false) {
                return new Versement("Ce paiement est déjà encaisser !");
            }

            versement.setDate_update(new Date());
            return repos.save(versement);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Versement(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {

            Versement versement = repos.getOne(id);
            versement.setEtat(Constantes.DELETE);
            versement.setDate_update(new Date());
            repos.save(versement);
            return "Suppression effectuée avec succès !";

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }


    private String generatedNumero() {
        String numero = "SONACAM/PV/";
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
