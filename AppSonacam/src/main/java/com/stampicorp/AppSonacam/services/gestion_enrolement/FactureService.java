package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.*;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.FactureRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.AgentService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.EmployeService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.ZoneService;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import com.stampicorp.AppSonacam.utils.Utils;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class FactureService {
    @Autowired
    FactureRepos repos;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    AgentService agentService;
    @Autowired
    EmployeService employeService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ZoneService zoneService;

    @Autowired
    ContribuableService contribuableService;

    @Autowired
    PeriodePaiementService periodePaiementService;
    @Autowired
    JdbcTemplate jdbcTemplate;


    public List<Facture> list() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Utilisateur users = utilisateurService.getOne(user.getId());
            if (users.getAgent()) {
                Agent agent = agentService.getAgentByUser(users.getId());
                if (agent != null ? agent.getId() > 0 : false) {
                    return repos.findByZone(agent.getZone(), Constantes.ADD);
                }
                return null;

            } else {
                Set<Role> roles = users.getRoles();
                Employe employe = employeService.getEmployeByUser(users.getId());
                Role admin = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ADMIN, Constantes.ADD);
                if (roles.contains(admin)) {
                    return repos.findByEtatEqualsOrderById(Constantes.ADD);
                }

                Role agence = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_AGENCE, Constantes.ADD);
                if (roles.contains(agence)) {
                    return repos.findByAgence(employe.getAgence(), Constantes.ADD);
                }
            }
        }
        return null;
    }

    public List<Facture> listByDate(String dateDebut) {
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                if (!users.getAgent()) {
                    Set<Role> roles = users.getRoles();
                    Employe employe = employeService.getEmployeByUser(users.getId());
                    Role admin = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ADMIN, Constantes.ADD);
                    Role agence = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_AGENCE, Constantes.ADD);
                    Role zone = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ZONE, Constantes.ADD);
                    if (roles.contains(admin)) {
                        return periodePaiementService.listByDate(dateDebut);
                    } else if (roles.contains(agence)) {
                        return periodePaiementService.listByDateAgence(employe.getAgence(), dateDebut);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }
    }

    public List<Facture> listByContribuable(Long idContribuable) {
        return repos.findByContribuableAndEtatEqualsOrderById(new Contribuable(idContribuable), Constantes.ADD);
    }

    public List<Facture> listByContribuable(String numero) {
        Contribuable contribuable = contribuableService.findByNumero(numero);
        if (contribuable != null ? contribuable.getId() > 0 : false) {
            return listByContribuable(contribuable.getId());
        } else {
            return null;
        }
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


            LocalDate now = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
            Date debut = Utils.modifyDateLayout(now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + " 00:00:00 UTC");
            now = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
            Date fin = Utils.modifyDateLayout(now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + " 00:00:00 UTC");

            f = repos.findbyContribuableAndDate(facture.getContribuable(), debut, fin, Constantes.ADD);
            if (f != null ? f.getId() > 0 : false) {
                return new Facture("Cet usager à déjà un ordre de paiement en cours");
            }

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                facture.setAuthor(users);
            }


            facture.setStatut(Constantes.STATUT_VALIDER);
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

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                facture.setAuthor(users);
            }

            if (facture.getDate_prochain() != null) {
                PeriodePaiement periode = periodePaiementService.findByFacture(facture.getId());
                periode.setDateProchainPaiement(facture.getDate_prochain());

                periodePaiementService.update(periode);
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

    public JasperPrint getFile() throws FileNotFoundException, JRException, SQLException {
        File file = ResourceUtils.getFile("classpath:factures.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameter = new HashMap<>();
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, connection);
        return jasperPrint;
    }


    public void exportReport(OutputStream outputStream) throws FileNotFoundException, JRException, SQLException {
        JasperPrint jasperPrint = getFile();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
