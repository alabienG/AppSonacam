package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;
import com.stampicorp.AppSonacam.services.gestion_enrolement.PeriodePaiementService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "periode")
@CrossOrigin("*")
public class PeriodeController {

    @Autowired
    PeriodePaiementService service;


    @GetMapping("/")
    public List<PeriodePaiement> all() {
        return service.all();
    }

    @GetMapping("/findByFacture/{idFacture}")
    public PeriodePaiement findByFacture(@PathVariable Long idFacture) {
        return service.findByFacture(idFacture);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody PeriodePaiement periodePaiement) {
        PeriodePaiement periode = service.create(periodePaiement);
        if (periode != null ? periode.getId() > 0 : false) {
            return new ResponseEntity(periode, HttpStatus.OK);
        } else {
            return new ResponseEntity(periode.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity update(@RequestBody PeriodePaiement periodePaiement) {
        PeriodePaiement periode = service.update(periodePaiement);
        if (periode != null ? periode.getId() > 0 : false) {
            return new ResponseEntity(periode, HttpStatus.OK);
        } else {
            return new ResponseEntity(periode.getId(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
