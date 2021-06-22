package com.stampicorp.AppSonacam.controller.gestion_enrolement;

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

    @GetMapping("/allByAuthor")
    public List<Paiement> allByAuthor() {
        return service.allByAuthor();
    }

    @GetMapping("/allJourByAuthor/{dateDebut}")
    public List<Paiement> allJourByAuthor(@PathVariable String dateDebut) {
        return service.getPaiementJourByAuthor(dateDebut);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return new ResponseEntity(service.getOne(id), HttpStatus.OK);
    }

    @GetMapping("/getSolde/{idFacture}")
    public Double getSolde(@PathVariable Long idFacture) {
        return service.getSolde(idFacture);
    }

    @GetMapping("/getNombreTotal")
    public Double getNombreTotal() {
        return service.nombreTotalByAuthor();
    }

    @GetMapping("/getNombreJour/{dateDebut}")
    public Double getNombreJour(@PathVariable String dateDebut) {
        return service.nombreJournalier(dateDebut);
    }

    @GetMapping("/getSoldeJourByAuthor/{dateDebut}")
    public Double getSoldeJourByAuthor(@PathVariable String dateDebut) {
        return service.getSoldeJourByAuthor(dateDebut);
    }

    @GetMapping("/getSoldeByAuthor")
    public Double getSoldeByAuthor() {
        return service.getSoldeByAuthor();
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

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        String deleted = service.delete(id);
        return new ResponseEntity(deleted, HttpStatus.OK);
    }
}
