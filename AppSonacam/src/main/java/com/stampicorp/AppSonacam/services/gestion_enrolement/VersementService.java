package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.VersementRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.EmployeService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.RoleService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurService;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VersementService {

    @Autowired
    VersementRepos repos;
    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeService employeService;

    @Autowired
    RoleRepo roleRepo;

    public List<Versement> all() {
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    public List<Versement> allByFacture(Long idFacture) {
        return repos.allByFacture(new Facture(idFacture), Constantes.ADD);
    }

    public List<Versement> allByUtilisateur(Long idUser) {
        return repos.findByAuthorAndEtatEqualsOrderById(new Utilisateur(idUser), Constantes.ADD);
    }

    public Versement findByPaiement(Long idPaiement) {
        return repos.findByPaiementAndEtatEquals(new Paiement(idPaiement), Constantes.ADD);
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

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                versement.setAuthor(users);
            }
            versement.setStatut(Constantes.STATUT_PAYER);
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

    public JasperPrint getFile() throws FileNotFoundException, JRException, SQLException {
        File file = ResourceUtils.getFile("classpath:versements.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameter = new HashMap<>();
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, connection);
        return jasperPrint;
    }

    public JasperPrint getFileVersementByAgence(Long idAgence, boolean admin) throws FileNotFoundException, JRException, SQLException {
        File file;
        if(admin){
            file  = ResourceUtils.getFile("classpath:AllVersementByZone.jrxml");
        }else{
            file = ResourceUtils.getFile("classpath:versementZone.jrxml");
        }
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameter = new HashMap<>();
        if(!admin){
            parameter.put("zone_id", Integer.valueOf(idAgence.toString()));
        }
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, connection);
        return jasperPrint;
    }

    public void exportVersementByAgence(OutputStream outputStream, Long idUser) throws FileNotFoundException, JRException, SQLException {
        if (idUser != null) {
            Utilisateur users =utilisateurService.getOne(idUser);
            System.out.println(users.getRoles().size());
            JasperPrint jasperPrint;
            Role admin = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ADMIN, Constantes.ADD);
            if(users.getRoles().contains(admin)){
                 jasperPrint = getFileVersementByAgence(0L,true);
            }else{
                Employe employe = employeService.getEmployeByUser(users.getId());
                 jasperPrint = getFileVersementByAgence(employe.getAgence().getId(),false);
            }

            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        }
    }

    public void exportReport(OutputStream outputStream) throws FileNotFoundException, JRException, SQLException {
        JasperPrint jasperPrint = getFile();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }


}
