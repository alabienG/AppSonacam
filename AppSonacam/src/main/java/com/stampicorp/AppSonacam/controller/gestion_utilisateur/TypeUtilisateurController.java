package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.TypeUtilisateur;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.TypeUtilisateurService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "typeUser")
@CrossOrigin("*")
public class TypeUtilisateurController {

    @Autowired
    TypeUtilisateurService service;

    @GetMapping
    public List<TypeUtilisateur> all() {
        return service.findAllTypeUtilisateur();
    }

    @GetMapping("findById/{id}")
    public TypeUtilisateur findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TypeUtilisateur typeUtilisateur) {
        TypeUtilisateur type = service.add(typeUtilisateur);
        if (type != null ? type.getId() > 0 : false) {
            return new ResponseEntity(type, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(type.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody TypeUtilisateur typeUtilisateur) {
        TypeUtilisateur type = service.update(typeUtilisateur);
        if (type != null ? type.getId() > 0 : false) {
            return new ResponseEntity(type, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(type.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity("Suppression effectuée avec succès !", HttpStatus.OK);
    }

}
