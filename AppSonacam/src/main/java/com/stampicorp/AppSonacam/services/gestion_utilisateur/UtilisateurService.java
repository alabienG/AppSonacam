package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.UtilisateurRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepo repos;

    public List<Utilisateur> all() {
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    public List<Utilisateur> findByRole(Long idRole) {
        return null;
    }

    public List<Utilisateur> findByZone(Long idZone) {
        return repos.findByZoneAndEtatEqualsOrderById(new Zone(idZone), Constantes.ADD);
    }

    @Transactional
    public Utilisateur create(Utilisateur user) {
        try {
            user.setMatricule(generatedMatricule());
            // crypter le passeword
            user.setEtat(Constantes.ADD);
            user.setDateSave(new Date());
            user.setDateUpdate(new Date());

            return repos.save(user);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Utilisateur(e.getMessage());
        }
    }

    @Transactional
    public Utilisateur update(Utilisateur user) {
        try {
            // crypter le passeword
            user.setDateUpdate(new Date());
            return repos.save(user);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Utilisateur(e.getMessage());
        }
    }

    private String generatedMatricule() {
        String numero = "SONACAM/MAT/";
        Long id = repos.getCountId() + 1;
        if (id < 10) {
            return numero + "00" + id;
        } else if (id > 10 && id < 100) {
            return numero + "0" + id;
        } else {
            return numero + id.toString();
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            Utilisateur z = repos.getOne(id);
            z.setEtat(Constantes.DELETE);
            z.setDateUpdate(new Date());
            repos.save(z);
            return "Suppression effectuée avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }

}
