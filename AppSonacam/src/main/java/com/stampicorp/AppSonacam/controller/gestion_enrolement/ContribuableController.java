package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.services.gestion_enrolement.ContribuableService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "contribuable")
@CrossOrigin("*")
public class ContribuableController {

    @Autowired
    ContribuableService service;

    @GetMapping("/")
    public List<Contribuable> all() {
        return service.all();
    }

    @GetMapping("/allByActivite/{idActivite}")
    public List<Contribuable> allByActivite(@PathVariable Long idActivite) {
        return service.allByActivite(idActivite);
    }

    @GetMapping("/allByAuthor")
    public List<Contribuable> allByAuthor() {
        return service.allByAuthor();
    }


    @GetMapping("/findByNumero/{numero}")
    public ResponseEntity findByNumero(@PathVariable String numero) {
        Contribuable contribuable = service.findByNumero(numero);
        if (contribuable != null ? contribuable.getId() > 0 : false) {
            return new ResponseEntity(contribuable, HttpStatus.OK);
        } else {
            return new ResponseEntity(contribuable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOne/{id}")
    public Contribuable getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Contribuable contribuable) {
        Contribuable con = service.create(contribuable);
        if (con != null ? con.getId() > 0 : false) {
            return new ResponseEntity(con, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(con.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Contribuable contribuable) {
        Contribuable con = service.update(contribuable);
        if (con != null ? con.getId() > 0 : false) {
            return new ResponseEntity(con, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(con.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }

}
