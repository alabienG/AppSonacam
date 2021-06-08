package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.PaysService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "pays")
@CrossOrigin("*")
public class PaysController {

    @Autowired
    PaysService service;

    @GetMapping("/")
    public List<Pays> all() {
        return service.all();
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Pays pays) {
        Pays p = service.create(pays);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Pays pays) {
        Pays p = service.update(pays);
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
