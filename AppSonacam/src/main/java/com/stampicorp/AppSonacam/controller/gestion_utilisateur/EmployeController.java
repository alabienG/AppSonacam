package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.EmployeService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.PATH + "employe")
@CrossOrigin("*")
public class EmployeController {

    @Autowired
    EmployeService service;

    @GetMapping("/findByUser/{idUser}")
    public Employe findByUser(@PathVariable Long idUser) {
        return service.getEmployeByUser(idUser);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Employe agent) {
        Employe agen = service.create(agent);
        if (agen != null ? agen.getId() > 0 : false) {
            return new ResponseEntity(agen, HttpStatus.OK);
        } else {
            return new ResponseEntity("Action impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Employe agent) {
        Employe agen = service.update(agent);
        if (agen != null ? agen.getId() > 0 : false) {
            return new ResponseEntity(agen, HttpStatus.OK);
        } else {
            return new ResponseEntity("Action impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
