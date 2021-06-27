package com.stampicorp.AppSonacam.controller.repport;

import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_enrolement.ActiviteService;
import com.stampicorp.AppSonacam.services.gestion_enrolement.FactureService;
import com.stampicorp.AppSonacam.services.gestion_enrolement.VersementService;
import com.stampicorp.AppSonacam.utils.Constantes;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@RestController
@RequestMapping(Constantes.PATH + "report")
@CrossOrigin("*")
public class RepportController {

    @Autowired
    VersementService service;
    @Autowired
    ActiviteService activiteService;
    @Autowired
    FactureService factureService;
    @Autowired
    VersementService versementService;


    @GetMapping("/versement/{format}")
    public void generatedReport(HttpServletResponse response, @PathVariable String format) throws IOException, JRException, SQLException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachement; filename=\"versement.pdf\""));
        OutputStream out = response.getOutputStream();
        service.exportReport(out);
    }

    @GetMapping("/activites/{format}")
    public void activiteReport(HttpServletResponse response, @PathVariable String format) throws IOException, JRException, SQLException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachement; filename=\"activite.pdf\""));
        OutputStream out = response.getOutputStream();
        activiteService.exportReport(out);
    }

    @GetMapping("/facture")
    public void fcatureReport(HttpServletResponse response) throws IOException, JRException, SQLException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachement; filename=\"facture.pdf\""));
        OutputStream out = response.getOutputStream();
        factureService.exportReport(out);
    }

    @GetMapping("/versementByAgence/{idUser}")
    public void versementByAgence(HttpServletResponse response, @PathVariable Long idUser) throws IOException, JRException, SQLException {
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", String.format("attachement; filename=\"facture.pdf\""));
        OutputStream out = response.getOutputStream();
        versementService.exportVersementByAgence(out, idUser);
    }
}
