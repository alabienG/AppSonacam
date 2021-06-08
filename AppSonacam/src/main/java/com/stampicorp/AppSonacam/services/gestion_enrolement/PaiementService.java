package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
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
    @Autowired
    VersementService versementService;

    public List<Paiement> all() {
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    public List<Paiement> allByFacture(Long idFacture) {
        return repos.findByFactureAndEtatEqualsOrderById(new Facture(idFacture), Constantes.ADD);
    }

    public List<Paiement> allByAuthor(Long idAuthor) {
        return repos.findByAuthorAndEtatEqualsOrderById(new Utilisateur(idAuthor), Constantes.ADD);
    }

    @Transactional
    public Paiement create(Paiement paiement) {
        try {

            // on verifie les montants !


            List<Paiement> list = repos.findByFactureAndEtatEqualsOrderById(paiement.getFacture(), Constantes.ADD);
            if (list != null ? list.size() > 0 : false) {
                double montant = 0;
                for (Paiement p : list) {
                    montant += p.getMontant();
                }

                if (montant < paiement.getFacture().getMontant()) {
                    if ((montant + paiement.getMontant()) > paiement.getFacture().getMontant()) {
                        return new Paiement("La somme des paiements sera suppérieur au montant de la facture");
                    }
                } else {
                    return new Paiement("La facture est déjà payer !");
                }

                if ((montant + paiement.getMontant()) == paiement.getFacture().getMontant()) {
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);
                } else if (paiement.getMontant() == paiement.getFacture().getMontant()) {
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);
                }
            }


            paiement.setNumero(generatedNumero());
            paiement.setEtat(Constantes.ADD);
            paiement.setDate_save(new Date());
            paiement.setDate_update(new Date());
            paiement = repos.save(paiement);


            //on genere le versement

            Versement versement = new Versement();
            versement.setPaiement(paiement);
            versement.setMontant(paiement.getMontant());
            versement.setEtat(Constantes.ADD);
            versement.setStatut(Constantes.STATUT_ATTENTE);

            versementService.create(versement);
            return paiement;

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
            List<Paiement> list = repos.findByFactureAndEtatEqualsOrderById(paiement.getFacture(), Constantes.ADD);
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
                } else {
                    return new Paiement("La facture est déjà payer !");
                }
                if ((montant + paiement.getMontant()) == paiement.getFacture().getMontant()) {
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);
                } else if (paiement.getMontant() == paiement.getFacture().getMontant()) {
                    Facture facture = paiement.getFacture();
                    factureService.payer(facture);
                }
            }
            paiement.setDate_update(new Date());
            paiement = repos.save(paiement);

            Versement versement = versementService.findByPaiement(paiement.getId());
            versement.setMontant(paiement.getMontant());
            versementService.update(versement);

            return paiement;

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
