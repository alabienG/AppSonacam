package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.AgenceService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "agence")
@CrossOrigin("*")
public class AgenceController {

    @Autowired
    AgenceService service;

    @GetMapping("/")
    public List<Agence> all() {
        return service.all();
    }

    @GetMapping("/findByPays/{idPays}")
    public List<Agence> findByPays(@PathVariable Long idPays) {
        return service.findByPays(idPays);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Agence agence) {
        Agence p = service.create(agence);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Agence agence) {
        Agence p = service.update(agence);
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
