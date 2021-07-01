package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ActiviteRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.zaxxer.hikari.util.DriverDataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
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
public class ActiviteService {
    @Autowired
    ActiviteRepo repos;

    @Autowired
    ContribuableService contribuableService;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    public Activite findByLibelle(String libelle) {
        return repos.findByLibelleAndEtatEquals(libelle, Constantes.ADD);
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


    public JasperPrint getFile() throws FileNotFoundException, JRException, SQLException {
        File file = ResourceUtils.getFile("classpath:activites.jrxml");
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
