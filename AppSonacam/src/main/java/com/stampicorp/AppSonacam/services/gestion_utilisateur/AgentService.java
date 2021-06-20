package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Agent;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AgentRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AgentService {

    @Autowired
    AgentRepos repos;

    public Agent getAgentByUser(Long idUser) {
        return repos.findByUser(new Utilisateur(idUser));
    }

    @Transactional
    public Agent create(Agent agent) {
        try {
            Agent agent1 = repos.findByUser(agent.getUser());
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
    public Agent update(Agent agent) {
        try {
            Agent agent1 = repos.findByUser(agent.getUser());
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

    public void delete(){

    }
}
