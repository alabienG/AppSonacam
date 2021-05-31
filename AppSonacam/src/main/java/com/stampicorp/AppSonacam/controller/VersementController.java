package com.stampicorp.AppSonacam.controller;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Versement;
import com.stampicorp.AppSonacam.services.gestion_enrolement.VersementService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "versement")
@CrossOrigin("*")
public class VersementController {

    @Autowired
    VersementService service;

    @GetMapping("/")
    public List<Versement> all() {
        return service.all();
    }

    @GetMapping("/allByFacture/{idFacture}")
    public List<Versement> allByFacture(@PathVariable Long idFacture) {
        return service.allByFacture(idFacture);
    }

    @GetMapping("/allByUtilisateur/{idUser}")
    public List<Versement> allByUtilisateur(@PathVariable Long idUser) {
        return service.allByUtilisateur(idUser);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Versement versement) {
        Versement v = service.create(versement);
        if (v != null ? v.getId() > 0 : false) {
            return new ResponseEntity(v, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(v.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/")
    public ResponseEntity update(@RequestBody Versement versement) {
        Versement v = service.update(versement);
        if (v != null ? v.getId() > 0 : false) {
            return new ResponseEntity(v, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(v.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/valider")
    public ResponseEntity valider(@RequestBody Versement versement) {
        Versement v = service.valider(versement);
        if (v != null ? v.getId() > 0 : false) {
            return new ResponseEntity(v, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(v.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
