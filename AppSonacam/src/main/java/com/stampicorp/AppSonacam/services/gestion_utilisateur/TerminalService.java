package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Terminal;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.TerminalRepos;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TerminalService {
    @Autowired
    TerminalRepos repos;

    public List<Terminal> all() {
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    @Transactional
    public Terminal create(Terminal terminal) {
        try {
            Terminal term = repos.findByNumeroSerieAndEtatEquals(terminal.getNumeroSerie(), Constantes.ADD);
            if (term != null ? term.getId() > 0 : false) {
                return new Terminal("Ce numéro de serie existe déjà !");
            }

            terminal.setEtat(Constantes.ADD);
            terminal.setDateSave(new Date());
            terminal.setDateUpdate(new Date());

            return repos.save(terminal);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Terminal(e.getMessage());
        }
    }

    @Transactional
    public Terminal update(Terminal terminal) {
        try {
            Terminal term = repos.findByNumeroSerieAndEtatEquals(terminal.getNumeroSerie(), Constantes.ADD);
            if (term != null ? term.getId() != terminal.getId() : false) {
                return new Terminal("Ce numéro de serie existe déjà !");
            }
            terminal.setDateUpdate(new Date());

            return repos.save(terminal);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Terminal(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            Terminal terminal = repos.getOne(id);
            terminal.setEtat(Constantes.DELETE);
            terminal.setDateUpdate(new Date());
            repos.save(terminal);

            return "Suppression effectuée avec succès !";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }
}
