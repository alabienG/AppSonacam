package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_enrolement.PeriodePaiement;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agence;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.PeriodePaiementRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PeriodePaiementService {
    @Autowired
    PeriodePaiementRepo repos;

    public List<PeriodePaiement> all() {
        return repos.findByEtatEquals(Constantes.ADD);
    }

    public PeriodePaiement findByFacture(Long idFacture) {
        return repos.findByFactureAndEtatEquals(new Facture(idFacture), Constantes.ADD);
    }

    List<Facture> listByDate(String date) {
        try {
            Date debut = Utils.modifyDateLayout(date + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(date + " 23:59:00 UTC");
            return repos.getFactureByDate(debut, fin, Constantes.ADD,Constantes.STATUT_VALIDER);
        } catch (Exception e) {
            return null;
        }

    }

    List<Facture> listByDateAgence(Agence agence , String date) {
        try {
            Date debut = Utils.modifyDateLayout(date + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(date + " 23:59:00 UTC");
            return repos.getFactureByDate(debut, fin, Constantes.ADD,agence, Constantes.STATUT_VALIDER);
        } catch (Exception e) {
            return null;
        }

    }



    @Transactional
    public PeriodePaiement create(PeriodePaiement periodePaiement) {
        try {
            PeriodePaiement periode = repos.findByFactureAndEtatEquals(periodePaiement.getFacture(), Constantes.ADD);
            if (periode != null ? periode.getId() > 0 : false) {
                return new PeriodePaiement("Cette periodicité existe déjà !");
            }
            periodePaiement.setEtat(Constantes.ADD);
            periodePaiement.setDate_save(new Date());
            periodePaiement.setDate_update(new Date());

            return repos.save(periodePaiement);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new PeriodePaiement(e.getMessage());
        }
    }

    @Transactional
    public PeriodePaiement update(PeriodePaiement periodePaiement) {
        try {
            PeriodePaiement periode = repos.findByFactureAndEtatEquals(periodePaiement.getFacture(), Constantes.ADD);
            if (periode != null ? periode.getId() != periodePaiement.getId() : false) {
                return new PeriodePaiement("Cette periodicité existe déjà !");
            }
            periodePaiement.setDate_update(new Date());

            return repos.save(periodePaiement);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new PeriodePaiement(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            PeriodePaiement periode = repos.getOne(id);
            periode.setEtat(Constantes.DELETE);
            periode.setDate_update(new Date());

            repos.save(periode);
            return "Suppression effectuée avec succès !";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
