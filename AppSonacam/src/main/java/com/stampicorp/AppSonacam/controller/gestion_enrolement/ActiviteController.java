package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.services.gestion_enrolement.ActiviteService;
import com.stampicorp.AppSonacam.utils.Constantes;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "activite")
@CrossOrigin("*")
public class ActiviteController {

    @Autowired
    ActiviteService service;

    @GetMapping("/")
    public List<Activite> getAll() {
        return service.all();
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Activite activite) {
        Activite a = service.create(activite);
        if (a != null ? a.getId() > 0 : false) {
            return new ResponseEntity(a, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(a.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Activite activite) {
        Activite a = service.update(activite);
        if (a != null ? a.getId() > 0 : false) {
            return new ResponseEntity(a, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(a.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }

    @GetMapping("/report/{format}")
    public String generatedReport(@PathVariable String format) throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }
}
