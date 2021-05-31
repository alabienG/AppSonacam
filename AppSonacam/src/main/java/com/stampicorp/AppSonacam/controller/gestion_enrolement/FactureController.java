package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.services.gestion_enrolement.FactureService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "facture")
@CrossOrigin("*")
public class FactureController {

    @Autowired
    FactureService service;

    @GetMapping("/")
    public List<Facture> all() {
        return service.list();
    }

    @GetMapping("/listByContribuable/{idContribuable}")
    public List<Facture> listByContribuable(@PathVariable Long idContribuable) {
        return service.listByContribuable(idContribuable);
    }

    @GetMapping("/findByNumero/{numero}")
    public ResponseEntity findByNumero(@PathVariable String numero) {
        return new ResponseEntity(service.findByNumero(numero), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Facture facture) {
        Facture fact = service.create(facture);
        if (fact != null ? fact.getId() > 0 : false) {
            return new ResponseEntity(fact, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(fact.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/valider")
    public ResponseEntity valider(@RequestBody Facture facture) {
        Facture fact = service.valider(facture);
        if (fact != null ? fact.getId() > 0 : false) {
            return new ResponseEntity(fact, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(fact.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/payer")
    public ResponseEntity payer(@RequestBody Facture facture) {
        Facture fact = service.payer(facture);
        if (fact != null ? fact.getId() > 0 : false) {
            return new ResponseEntity(fact, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(fact.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Facture facture) {
        Facture fact = service.update(facture);
        if (fact != null ? fact.getId() > 0 : false) {
            return new ResponseEntity(fact, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(fact.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }


}
