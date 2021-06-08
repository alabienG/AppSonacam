package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "utilisateur")
@CrossOrigin("*")
public class UtilisateurController {

    @Autowired
    UtilisateurService service;

    @GetMapping("/")
    public List<Utilisateur> all() {
        return service.all();
    }

    @GetMapping("/findByRole/{idRole}")
    public List<Utilisateur> findByRole(@PathVariable Long idRole) {
        return service.findByRole(idRole);
    }

    @GetMapping("/findByZone/{idZone}")
    public List<Utilisateur> findByZone(@PathVariable Long idZone) {
        return service.findByZone(idZone);
    }

    @PostMapping("/")
    public ResponseEntity create(Utilisateur utilisateur) {
        Utilisateur user = service.create(utilisateur);
        if (user != null ? user.getId() > 0 : false) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(user.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(Utilisateur utilisateur) {
        Utilisateur user = service.update(utilisateur);
        if (user != null ? user.getId() > 0 : false) {
            return new ResponseEntity(user, HttpStatus.OK);
        } else {
            return new ResponseEntity(user.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
