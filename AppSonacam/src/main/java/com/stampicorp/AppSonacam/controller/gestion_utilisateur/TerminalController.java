package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Terminal;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.TerminalService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "terminal")
@CrossOrigin("*")
public class TerminalController {

    @Autowired
    TerminalService service;

    @GetMapping("/")
    public List<Terminal> all() {
        return service.all();
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Terminal terminal) {
        Terminal term = service.create(terminal);
        if (term != null ? term.getId() > 0 : false) {
            return new ResponseEntity(term, HttpStatus.OK);
        } else {
            return new ResponseEntity(term.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Terminal terminal) {
        Terminal term = service.create(terminal);
        if (term != null ? term.getId() > 0 : false) {
            return new ResponseEntity(term, HttpStatus.OK);
        } else {
            return new ResponseEntity(term.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
