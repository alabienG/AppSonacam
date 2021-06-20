package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Delegation;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.DelegationService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "delegation")
@CrossOrigin("*")
public class DelegationController {

    @Autowired
    DelegationService service;

    @GetMapping("/")
    public List<Delegation> all() {
        return service.all();
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Delegation delegation) {
        Delegation del = service.create(delegation);
        if (del != null ? del.getId() > 0 : false) {
            return new ResponseEntity(del, HttpStatus.OK);
        } else {
            return new ResponseEntity(del.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Delegation delegation) {
        Delegation del = service.update(delegation);
        if (del != null ? del.getId() > 0 : false) {
            return new ResponseEntity(del, HttpStatus.OK);
        } else {
            return new ResponseEntity(del.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.ACCEPTED);
    }

}
