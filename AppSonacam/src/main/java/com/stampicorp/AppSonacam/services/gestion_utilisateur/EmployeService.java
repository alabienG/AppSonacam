package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Employe;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.EmployeRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class EmployeService {

    @Autowired
    EmployeRepos repos;

    public Employe getEmployeByUser(Long idUser) {
        return repos.findByUser(new Utilisateur(idUser));
    }

    @Transactional
    public Employe create(Employe agent) {
        try {
            Employe agent1 = repos.findByUser(agent.getUser());
            if (agent1 != null ? agent1.getId() > 0 : false) {
                return agent1;
            } else {
                agent.setEtat(Constantes.ADD);
                agent.setDateSave(new Date());
                agent.setDateUpdate(new Date());

                return repos.save(agent);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }
    }

    @Transactional
    public Employe update(Employe agent) {
        try {
            Employe agent1 = repos.findByUser(agent.getUser());
            if (agent1 != null ? agent1.getId() != agent.getId() : false) {
                return agent1;
            } else {
                agent.setDateUpdate(new Date());

                return repos.save(agent);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }
    }

    public void delete() {

    }
}
