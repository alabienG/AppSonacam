package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Antenne;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.ZoneRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ZoneService {
    private final ZoneRepo zoneRepo;

    @Autowired
    public ZoneService(ZoneRepo zoneRepo) {
        this.zoneRepo = zoneRepo;
    }

    public List<Zone> findAllZone() {
        return zoneRepo.findByEtatEquals(Constantes.ADD);
    }

    public Zone findZoneById(Long id) {
        return zoneRepo.getOne(id);
    }

    public Zone AddZone(Zone zone) {
        Zone z = zoneRepo.findByLibelleAndEtatEquals(zone.getLibelle(), Constantes.ADD);
        try {

            if (z != null ? z.getId() > 0 : false) {
                return new Zone("cette zone n'existe pas");
            } else {
                zone.setEtat(Constantes.ADD);
                zone.setDateUpdate(new Date());
                zone.setDateSave(new Date());
                return zoneRepo.save(zone);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Zone(e.getMessage());

        }
    }

    public Zone updateZone(Zone zone) {
        Zone z = zoneRepo.findByLibelleAndEtatEquals(zone.getLibelle(), Constantes.ADD);
        try {
            if (z != null ? z.getId() > 0 : false) {
                return new Zone("cette zone n'existe pas");
            } else {
                zone.setEtat(Constantes.ADD);
                zone.setDateUpdate(new Date());
                return zoneRepo.save(zone);
            }

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Zone(e.getMessage());
        }
    }

    public List<Zone> findByAgence(Long idAgence) {
        return zoneRepo.findByAgenceAndEtatEquals(new Agence(idAgence), Constantes.ADD);
    }

    @Transactional
    public void delete(Long id) {
        Zone z = zoneRepo.getOne(id);
        z.setEtat(Constantes.DELETE);
        z.setDateUpdate(new Date());
        zoneRepo.save(z);
    }
}
