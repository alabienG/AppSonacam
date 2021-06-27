package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.models.beans.Statistique;
import com.stampicorp.AppSonacam.models.beans.StatistiqueMobile;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ContribuableRepos;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.FactureRepos;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.PaiementRepo;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.VersementRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AgentRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.EmployeRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.UtilisateurRepo;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.ZoneRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import com.stampicorp.AppSonacam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StatistiqueService {

    @Autowired
    UtilisateurRepo utilisateurRepo;
    @Autowired
    EmployeRepos employeRepos;
    @Autowired
    VersementRepos versementRepos;
    @Autowired
    FactureRepos factureRepos;
    @Autowired
    PaiementRepo paiementRepo;
    @Autowired
    ZoneRepo zoneRepo;
    @Autowired
    AgentRepos agentRepos;
    @Autowired
    ContribuableRepos contribuableRepos;

    public Statistique getStatistique() {
        Statistique stat = new Statistique();
        // tout les versement
        UserDetailsImpl userToken = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userToken != null) {
            System.out.println(userToken.getAuthorities());
            Utilisateur user = utilisateurRepo.getOne(userToken.getId());
            user.getRoles().forEach(element -> {
                if (element.getLibelle().equals(ERole.ROLE_ADMIN)) {
                    // versement
                    stat.setNombreVersement(versementRepos.countAllVersement(Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setMontantVersement(versementRepos.montantVersement(Constantes.STATUT_PAYER, Constantes.ADD));
                    // facture
                    stat.setNombreFacture(factureRepos.nombreOrdre(Constantes.ADD));
                    stat.setNombreOrdreTerminer(factureRepos.nombreOrdreByStatut(Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setNombreOrdreEncours(factureRepos.nombreOrdreByStatut(Constantes.STATUT_VALIDER, Constantes.ADD));
                    stat.setMontantFactureTerminer(factureRepos.montantOrdreByStatut(Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setMontantFactureEncours(factureRepos.montantOrdreByStatut(Constantes.STATUT_VALIDER, Constantes.ADD));
                    stat.setMontantFActure(factureRepos.montantOrdre(Constantes.ADD));
                    stat.setNombrePaiement(paiementRepo.nombreFacture(Constantes.ADD));
                    stat.setMontantPaiement(paiementRepo.montantPaiement(Constantes.ADD));
                    stat.setNombreZone(zoneRepo.nombreZone(Constantes.ADD));
                    stat.setNombreAgent(utilisateurRepo.nombreAgent(Constantes.ADD));
                    stat.setNombreUsager(contribuableRepos.nombreUsager(Constantes.ADD));


                } else if (element.getLibelle().equals(ERole.ROLE_AGENCE)) {
                    // versement
                    Employe employe = employeRepos.findByUser(user);
                    stat.setNombreVersement(versementRepos.countVersementByAgence(employe.getAgence(), Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setMontantVersement(versementRepos.montantVersementByAgence(employe.getAgence(), Constantes.STATUT_PAYER, Constantes.ADD));
                    //facture
                    stat.setNombreFacture(factureRepos.nombreOrdreByAgence(employe.getAgence(), Constantes.ADD));
                    stat.setNombreOrdreTerminer(factureRepos.nombreOrdreByStatutAndAgence(employe.getAgence(), Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setNombreOrdreEncours(factureRepos.nombreOrdreByStatutAndAgence(employe.getAgence(), Constantes.STATUT_VALIDER, Constantes.ADD));
                    stat.setMontantFactureTerminer(factureRepos.montantOrdreByStatut(employe.getAgence(), Constantes.STATUT_PAYER, Constantes.ADD));
                    stat.setMontantFactureEncours(factureRepos.montantOrdreByStatut(employe.getAgence(), Constantes.STATUT_VALIDER, Constantes.ADD));
                    stat.setMontantFActure(factureRepos.montantOrdreByAgence(employe.getAgence(), Constantes.ADD));
                    stat.setNombrePaiement(paiementRepo.nombreFactureByAgence(employe.getAgence(), Constantes.ADD));
                    stat.setMontantPaiement(paiementRepo.montantPaiementByAgence(employe.getAgence(), Constantes.ADD));
                    stat.setNombreZone(zoneRepo.nombreZone(employe.getAgence(), Constantes.ADD));
                    stat.setNombreAgent(agentRepos.nombreAgent(employe.getAgence(), Constantes.ADD));
                    stat.setNombreUsager(contribuableRepos.nombreUsagerByAgence(employe.getAgence(), Constantes.ADD));
                }
            });
        }
        return stat;
    }

    public StatistiqueMobile getStatistiqueMobile(String dateDebut) {
        StatistiqueMobile statistique = new StatistiqueMobile();
        try {
            Date debut = Utils.modifyDateLayout(dateDebut + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(dateDebut + " 23:59:00 UTC");
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                statistique.setMontant_jour(paiementRepo.getSoldeJourByAuthor(new Utilisateur(user.getId()), debut, fin, Constantes.ADD));
                statistique.setNombre_paiement(paiementRepo.getNombreContribuableByUser(new Utilisateur(user.getId()), debut, fin, Constantes.ADD));
                statistique.setNombre_usager(contribuableRepos.getNombreTotalContribuableByUser(new Utilisateur(user.getId()), Constantes.ADD));
                statistique.setMontant_total(paiementRepo.getSoldeByAuthor(new Utilisateur(user.getId()), Constantes.ADD));
            }

        } catch (Exception e) {

        }
        return statistique;
    }
}
