package com.stampicorp.AppSonacam.controller;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Paiement;
import com.stampicorp.AppSonacam.services.gestion_enrolement.PaiementService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "paiement")
@CrossOrigin("*")
public class PaiementController {

    @Autowired
    PaiementService service;

    @GetMapping("/")
    public List<Paiement> all() {
        return service.all();
    }

    @GetMapping("/allByFacture/{idFacture}")
    public List<Paiement> allByFacture(@PathVariable Long idFacture) {
        return service.allByFacture(idFacture);
    }

    @GetMapping("/allByAuthor/{idAuthor}")
    public List<Paiement> allByAuthor(@PathVariable Long idAuthor) {
        return service.allByAuthor(idAuthor);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Paiement paiement) {
        Paiement p = service.create(paiement);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Paiement paiement) {
        Paiement p = service.update(paiement);
        if (p != null ? p.getId() > 0 : false) {
            return new ResponseEntity(p, HttpStatus.OK);
        } else {
            return new ResponseEntity(p.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
