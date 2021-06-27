package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.beans.Statistique;
import com.stampicorp.AppSonacam.models.beans.StatistiqueMobile;
import com.stampicorp.AppSonacam.services.gestion_enrolement.StatistiqueService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constantes.PATH + "statistique")
@CrossOrigin("*")
public class StatistiqueController {

    @Autowired
    StatistiqueService service;

    @GetMapping("/")
    public ResponseEntity getStat() {
        Statistique statistique = service.getStatistique();
        if (statistique != null) {
            return new ResponseEntity(statistique, HttpStatus.OK);
        } else {
            return new ResponseEntity(statistique, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mobile/{dateDebut}")
    public ResponseEntity getStatMobile(@PathVariable String dateDebut) {
        StatistiqueMobile stat = service.getStatistiqueMobile(dateDebut);
        if (stat != null) {
            return new ResponseEntity(stat, HttpStatus.OK);
        } else {
            return new ResponseEntity(stat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
