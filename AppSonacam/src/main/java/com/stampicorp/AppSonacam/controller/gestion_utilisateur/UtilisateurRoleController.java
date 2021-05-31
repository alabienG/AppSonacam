package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.UtilisateurRole;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurRoleService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "utilisateurRole")
@CrossOrigin("*")
public class UtilisateurRoleController {

    @Autowired
    UtilisateurRoleService service;

    @GetMapping("/listByUtilisateur/{idUser}")
    public List<UtilisateurRole> all(@PathVariable Long idUser) {
        return service.listByUtilisateur(idUser);
    }

    @PostMapping("/")
    public ResponseEntity create(UtilisateurRole utilisateurRole) {
        UtilisateurRole userRole = service.add(utilisateurRole);
        if (userRole != null ? userRole.getId() > 0 : false) {
            return new ResponseEntity(userRole, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(userRole.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(UtilisateurRole utilisateurRole) {
        UtilisateurRole userRole = service.update(utilisateurRole);
        if (userRole != null ? userRole.getId() > 0 : false) {
            return new ResponseEntity(userRole, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(userRole.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity("Suppression effectuée avec succès !", HttpStatus.OK);
    }
}
