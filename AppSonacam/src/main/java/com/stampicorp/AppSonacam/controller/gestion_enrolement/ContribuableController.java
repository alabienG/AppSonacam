package com.stampicorp.AppSonacam.controller.gestion_enrolement;

import com.stampicorp.AppSonacam.models.beans.Images;
import com.stampicorp.AppSonacam.models.beans.Usager;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.request.response.MessageResponse;
import com.stampicorp.AppSonacam.services.gestion_enrolement.ContribuableService;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

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

    @GetMapping("/getNombreTotal")
    public Long getNombreTotal() {
        return service.nombreTotalByAuthor();
    }

    @GetMapping("/getNombreJour/{dateDebut}")
    public Double getNombreJour(@PathVariable String dateDebut) {
        return service.nombreJournalier(dateDebut);
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
    @PostMapping("/saveMobile")
    public ResponseEntity createMobile(@RequestBody Usager usager) {
        Contribuable con = service.saveMobile(usager);
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

    @GetMapping("/getImage/{idUsager}")
    public Images getImages(@PathVariable Long idUsager) {
        return service.getImagesUsager(idUsager);
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                service.save(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
            } catch (Exception e) {
                message = e.getMessage();
//                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
            }
        }
        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(message));
    }

    @GetMapping("/getUsagerMontantNull")
    public List<Contribuable> getUsagerMontantNull() {
        return service.getUsagerMontantNull();
    }

}
