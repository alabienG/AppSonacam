package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agent;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.PaiementRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.AgentService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.EmployeService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurService;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import com.stampicorp.AppSonacam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PaiementService {
    @Autowired
    PaiementRepo repos;
    @Autowired
    FactureService factureService;
    @Autowired
    VersementService versementService;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    AgentService agentService;
    @Autowired
    EmployeService employeService;

    public List<Paiement> all() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Utilisateur users = utilisateurService.getOne(user.getId());
            if (users.getAgent()) {
                Agent agent = agentService.getAgentByUser(users.getId());
                if (agent != null ? agent.getId() > 0 : false) {
                    return repos.findByZone(agent.getZone(), Constantes.ADD);
                }
                return null;
            }else{
                Set<Role> roles = users.getRoles();
                Employe employe = employeService.getEmployeByUser(users.getId());
                Role admin = new Role(ERole.ROLE_ADMIN);
                if (roles.contains(admin)) {
                    return repos.findByEtatEqualsOrderById(Constantes.ADD);
                }
                Role agence =new Role(ERole.ROLE_AGENCE);
                if (roles.contains(agence)) {
                    return repos.findByAgence(employe.getAgence(), Constantes.ADD);
                }
            }
        }
        return null;

    }

    public List<Paiement> allByFacture(Long idFacture) {
        return repos.findByFactureAndEtatEqualsOrderById(new Facture(idFacture), Constantes.ADD);
    }

    public List<Paiement> allByAuthor(Long idAuthor) {
        return repos.findByAuthorAndEtatEqualsOrderById(new Utilisateur(idAuthor), Constantes.ADD);
    }

    public List<Paiement> allByAuthor() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            return allByAuthor(user.getId());
        }
        return null;
    }

    public Paiement getOne(Long id) {
        return repos.getOne(id);
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

                int tranche = list.size();
                if (tranche > 1) {
                    // alors on est à la troisieme tranche
                    if ((paiement.getFacture().getMontant() - montant) > paiement.getMontant()) {
                        return new Paiement("Ce paiement correspond à la troisième tranche et doit solder la facture , vous devez payer " + (paiement.getFacture().getMontant() - montant) + " FCFA");
                    } else {
                        tranche++;
                        paiement.setTranche(tranche);
                        factureService.payer(paiement.getFacture());
                    }
                } else {
                    tranche++;
                    paiement.setTranche(tranche);
                }
            } else {
                paiement.setTranche(1);
                if(paiement.getMontant() == paiement.getFacture().getMontant()){
                    factureService.payer(paiement.getFacture());
                }
            }
            if (paiement.getMontant() > paiement.getFacture().getMontant()) {
                return new Paiement("Le montant du paiement est supérieur au montant de l'ordre de redevance !");
            }

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                paiement.setAuthor(users);
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

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                paiement.setAuthor(users);
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

    public Double getSolde(Long idFacture) {
        try {
            List<Paiement> list = repos.findByFactureAndEtatEqualsOrderById(new Facture(idFacture), Constantes.ADD);
            double solde = 0.0;
            if (list != null ? list.size() > 0 : false) {
                for (Paiement p : list) {
                    solde += p.getMontant();
                }

            }
            return solde;
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }
    }

    public Double nombreTotalByAuthor() {
        Double nombre = 0.0;
        try {

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                nombre = repos.getNombreTotalContribuableByUser(new Utilisateur(user.getId()), Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());

        }
        return nombre;
    }

    public List<Paiement> getPaiementJourByAuthor(String dateDebut) {
        List<Paiement> list = new ArrayList<>();
        try {
            Date debut = Utils.modifyDateLayout(dateDebut + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(dateDebut + " 23:59:00 UTC");
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                list = repos.getPaiementJourByAuthor(new Utilisateur(user.getId()), debut, fin, Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
        }

        return list;
    }

    public Double getSoldeJourByAuthor(String dateDebut) {
        Double montant = 0.0;
        try {
            Date debut = Utils.modifyDateLayout(dateDebut + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(dateDebut + " 23:59:00 UTC");
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                montant = repos.getSoldeJourByAuthor(new Utilisateur(user.getId()), debut, fin, Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
        }
        return montant;
    }

    public Double getSoldeByAuthor() {
        Double montant = 0.0;
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                montant = repos.getSoldeByAuthor(new Utilisateur(user.getId()), Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
        }
        return montant;
    }


    public Long nombreJournalier(String dateDebut) {
        Long nombre = 0L;
        try {
            Date debut = Utils.modifyDateLayout(dateDebut + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(dateDebut + " 23:59:00 UTC");

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                nombre = repos.getNombreContribuableByUser(new Utilisateur(user.getId()), debut, fin, Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());

        }
        return nombre;
    }

    @Transactional
    public String delete(Long id) {
        try {

            Paiement paiement = repos.getOne(id);
            // on verifie si on a déjà encaisser le paiement
            Versement versement = versementService.findByPaiement(paiement.getId());
            if (versement.getStatut().equals(Constantes.STATUT_PAYER)) {
                return "Ce paiement ne peut pas être supprimer, il a déjà été encaisser !";
            }

            // on modifie le statut de la facture si elle était déjà payer !
            if (paiement.getFacture().getStatut().equals(Constantes.STATUT_PAYER)) {
                paiement.getFacture().setStatut(Constantes.STATUT_VALIDER);
                factureService.update(paiement.getFacture());
            }

            // on efface le versement
            versementService.delete(versement.getId());
            // on efface le paiement
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
