package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.AntenneService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "antenne")
@CrossOrigin("*")
public class AntenneController {

    @Autowired
    AntenneService service;

    @GetMapping("/")
    public List<Antenne> all() {
        return service.all();
    }

    @GetMapping("/allByAgence/{idAgence}")
    public List<Antenne> allByAgence(@PathVariable Long idAgence) {
        return service.allByAgence(idAgence);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Antenne antenne) {
        Antenne p = service.create(antenne);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Antenne antenne) {
        Antenne p = service.update(antenne);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.ACCEPTED);
    }
}
