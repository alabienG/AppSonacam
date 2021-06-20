package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agent;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.AgentService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.PATH + "agent")
@CrossOrigin("*")
public class AgentController {

    @Autowired
    AgentService service;

    @GetMapping("/findByUser/{idUser}")
    public Agent findByUser(@PathVariable Long idUser) {
        return service.getAgentByUser(idUser);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Agent agent) {
        Agent agen = service.create(agent);
        if (agen != null ? agen.getId() > 0 : false) {
            return new ResponseEntity(agen, HttpStatus.OK);
        } else {
            return new ResponseEntity("Action impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody Agent agent) {
        Agent agen = service.update(agent);
        if (agen != null ? agen.getId() > 0 : false) {
            return new ResponseEntity(agen, HttpStatus.OK);
        } else {
            return new ResponseEntity("Action impossible", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
