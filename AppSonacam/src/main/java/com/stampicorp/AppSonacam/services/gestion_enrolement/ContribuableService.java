package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.*;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ContribuableRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AgenceRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.*;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ContribuableService {
    @Autowired
    ContribuableRepos repos;
    @Autowired
    UtilisateurService utilisateurService;
    @Autowired
    AgentService agentService;
    @Autowired
    EmployeService employeService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ZoneService zoneService;

    public List<Contribuable> all() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Utilisateur users = utilisateurService.getOne(user.getId());
            if (users.getAgent()) {
                return allByAuthor();
            } else {
                Set<Role> roles = users.getRoles();
                Employe employe = employeService.getEmployeByUser(users.getId());
                Role admin = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ADMIN, Constantes.ADD);
                if (roles.contains(admin)) {
                    return repos.findByEtatEqualsOrderByIdDesc(Constantes.ADD);
                }
                Role agence = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_AGENCE, Constantes.ADD);
                if (roles.contains(agence)) {
                    return repos.findByAgence(employe.getAgence(), Constantes.ADD);
                }

            }
        }
        List<Contribuable> list = repos.findByEtatEqualsOrderByIdDesc(Constantes.ADD);
        return list;
    }

    public List<Contribuable> allByActivite(Long idActivite) {
        return repos.findByActiviteAndEtatEqualsOrderByIdDesc(new Activite(idActivite), Constantes.ADD);
    }

    public List<Contribuable> allByAuthor() {
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = new Utilisateur(user.getId());
                return repos.findByAuthorAndEtatEqualsOrderById(users, Constantes.ADD);
            }
            return null;
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }
    }

    public Contribuable findByNumero(String numero) {
        try {
            return repos.findByNumeroAndEtatEquals(numero, Constantes.ADD);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    public Contribuable getOne(Long id) {
        return repos.getOne(id);
    }

    @Transactional
    public Contribuable create(Contribuable contribuable) {
        try {
            contribuable.setNumero(generatedNumero(contribuable));

            Contribuable c = repos.findByNumeroAndEtatEquals(contribuable.getNumero(), Constantes.ADD);
            if (c != null ? c.getId() > 0 : false) {
                return new Contribuable("Un contribuable existe déjà avec ce numéro !");
            }

            if (contribuable.getCni() != null ? contribuable.getCni().length() > 0 : false) {
                c = repos.findByCniAndEtatEquals(contribuable.getCni(), Constantes.ADD);
                if (c != null ? c.getId() > 0 : false) {
                    return new Contribuable("Un contribuable existe déjà avec ce numéro de CNI !");
                }
            }

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                contribuable.setAuthor(users);
                if (users.getAgent()) {
                    Agent agent = agentService.getAgentByUser(users.getId());
                    if (agent != null ? agent.getId() > 0 : false) {
                        contribuable.setZone(agent.getZone());
                    }
                } else {
                    Employe employe = employeService.getEmployeByUser(users.getId());
                    if (employe != null ? employe.getId() > 0 : false) {
                        List<Zone> zones = zoneService.findByAgence(employe.getAgence().getId());
                        contribuable.setZone(zones.get(0));
                    } else {
                        return new Contribuable("Vous n'êtes rattaché à aucune zone ni agence ! veuillez contacter un administrateur");
                    }
                }
            }
            contribuable.setEtat(Constantes.ADD);
            contribuable.setDate_save(new Date());
            contribuable.setDate_update(new Date());
            return repos.save(contribuable);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    @Transactional
    public Contribuable update(Contribuable contribuable) {
        try {
            Contribuable c = repos.findByNumeroAndEtatEquals(contribuable.getNumero(), Constantes.ADD);
            // on verifie que le numéro n'appartient pas déjà à un contribuable
            if (c != null ? c.getId() != contribuable.getId() : false) {
                return new Contribuable("Un contribuable existe déjà avec ce numéro !");
            }
            // on verifie que le numero de cni n'appartient pas déjà à un contribuable
            if (contribuable.getCni() != null ? contribuable.getCni().length() > 0 : false) {
                c = repos.findByCniAndEtatEquals(contribuable.getCni(), Constantes.ADD);
                if (c != null ? c.getId() != contribuable.getId() : false) {
                    return new Contribuable("Un contribuable existe déjà avec ce numéro de CNI !");
                }
            }
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Utilisateur users = utilisateurService.getOne(user.getId());
                contribuable.setAuthor(users);
                if (users.getAgent()) {
                    Agent agent = agentService.getAgentByUser(users.getId());
                    if (agent != null ? agent.getId() > 0 : false) {
                        contribuable.setZone(agent.getZone());
                    }
                }
            }
            contribuable.setDate_update(new Date());
            return repos.save(contribuable);
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Contribuable(e.getMessage());
        }
    }

    @Transactional
    public String delete(Long id) {
        try {
            // on cherche les factures du contribuable

            Contribuable contribuable = repos.getOne(id);
            contribuable.setEtat(Constantes.DELETE);
            contribuable.setDate_update(new Date());
            repos.save(contribuable);
            return "Suppression effectuée avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }


    private String generatedNumero(Contribuable contribuable) {
        String numero = "SONACAM/";
        Long id = repos.getCountId() + 1;
        if (id < 10) {
            return numero + "00" + id;
        } else if (id > 10 && id < 100) {
            return numero + "0" + id;
        } else {
            return numero + id.toString();
        }
    }
}
