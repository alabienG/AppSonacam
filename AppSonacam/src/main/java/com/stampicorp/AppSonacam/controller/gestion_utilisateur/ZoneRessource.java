package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.ZoneService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "zone")
@CrossOrigin("*")
public class ZoneRessource {
    private final ZoneService zoneService;

    public ZoneRessource(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Zone>> getAllZone() {
        List<Zone> zones = zoneService.findAllZone();
        return new ResponseEntity<>(zones, HttpStatus.OK);
    }

    @GetMapping("/findByAgence/{idAgence}")
    public List<Zone> findByAntenne(@PathVariable Long idAgence) {
        return zoneService.findByAgence(idAgence);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Zone> getZoneById(@PathVariable Long id) {
        Zone zone = zoneService.findZoneById(id);
        return new ResponseEntity<>(zone, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addZone(@RequestBody Zone zone) {
        Zone newZone = zoneService.AddZone(zone);
        if (newZone != null ? newZone.getId() > 0 : false) {
            return new ResponseEntity<>(newZone, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(newZone.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateZone(@RequestBody Zone zone) {
        Zone newZone = zoneService.AddZone(zone);
        if (newZone != null ? newZone.getId() > 0 : false) {
            return new ResponseEntity<>(newZone, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(newZone.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Role> deleteZone(@PathVariable("id") Long id) {
        zoneService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
