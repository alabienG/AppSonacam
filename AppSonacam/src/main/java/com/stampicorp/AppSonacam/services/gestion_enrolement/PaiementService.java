package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.PaiementRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PaiementService {
    @Autowired
    PaiementRepo repos;
    @Autowired
    FactureService factureService;

    public List<Paiement> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public List<Paiement> allByFacture(Long idFacture) {
        return repos.findByFactureAndEtatEquals(new Facture(idFacture), Constantes.ADD);
    }

    public List<Paiement> allByAuthor(Long idAuthor) {
        return repos.findByAuthorAndEtatEquals(new Utilisateur(idAuthor), Constantes.ADD);
    }

    @Transactional
    public Paiement create(Paiement paiement) {
        try {

            // on verifie les montants !
            List<Paiement> list = repos.findByFactureAndEtatEquals(paiement.getFacture(), Constantes.ADD);
            if (list != null ? list.size() > 0 : false) {
                double montant = 0;
                for (Paiement p : list) {
                    montant += p.getMontant();
                }

                if (montant < paiement.getFacture().getMontant()) {
                    if ((montant + paiement.getMontant()) > paiement.getFacture().getMontant()) {
                        return new Paiement("La somme des paiements sera suppérieur au montant de la facture");
                    }
                } else if ((montant + paiement.getMontant()) == paiement.getFacture().getMontant()) {
                    // on payer la facture !
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);

                } else {
                    return new Paiement("La facture est déjà payer !");
                }
            }
            paiement.setEtat(Constantes.ADD);
            paiement.setDate_save(new Date());
            paiement.setDate_update(new Date());
            return repos.save(paiement);

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Paiement(e.getMessage());
        }
    }

    @Transactional
    public Paiement update(Paiement paiement) {
        try {
            // on verifie les montants !
            Paiement oldPaiement = repos.getOne(paiement.getId());
            double oldMontant = oldPaiement.getMontant();
            List<Paiement> list = repos.findByFactureAndEtatEquals(paiement.getFacture(), Constantes.ADD);
            if (list != null ? list.size() > 0 : false) {
                double montant = 0;
                for (Paiement p : list) {
                    montant += p.getMontant();
                }
                montant -= oldMontant;
                if (montant < paiement.getFacture().getMontant()) {
                    if ((montant + paiement.getMontant()) > paiement.getFacture().getMontant()) {
                        return new Paiement("La somme des paiements sera suppérieur au montant de la facture");
                    }
                } else if ((montant + paiement.getMontant()) == paiement.getFacture().getMontant()) {
                    // on payer la facture !
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);

                } else {
                    return new Paiement("La facture est déjà payer !");
                }
            }
            paiement.setDate_update(new Date());
            return repos.save(paiement);

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Paiement(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {

            Paiement paiement = repos.getOne(id);
            paiement.setEtat(Constantes.DELETE);
            paiement.setDate_update(new Date());
            repos.save(paiement);
            // suppression des versements !
            return "Suppression effectuée avec succès !";


        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }

}
