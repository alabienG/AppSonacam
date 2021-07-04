package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.beans.Images;
import com.stampicorp.AppSonacam.models.beans.Usager;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Contribuable;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.*;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ContribuableRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.AgenceRepos;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.*;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import com.stampicorp.AppSonacam.utils.ExcelHelper;
import com.stampicorp.AppSonacam.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
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
    @Autowired
    ActiviteService ac;
    @Autowired
    FactureService factureService;
    @Autowired
    PeriodePaiementService periodePaiementService;

    public List<Contribuable> all() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) {
            Utilisateur users = utilisateurService.getOne(user.getId());
            if (users.getAgent()) {
                return allByAuthor();
            } else {
                Set<Role> roles = users.getRoles();
                Employe employe = employeService.getEmployeByUser(users.getId());
//                Role admin = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_ADMIN, Constantes.ADD);
                Role admin = new Role(ERole.ROLE_ADMIN);
                if (roles.contains(admin)) {
                    return repos.findByEtatEqualsOrderByIdDesc(Constantes.ADD);
                }
//                Role agence = roleRepo.findByLibelleAndEtatEquals(ERole.ROLE_AGENCE, Constantes.ADD);
                Role agence = new Role(ERole.ROLE_AGENCE);
                if (roles.contains(agence)) {
                    return repos.findByAgence(employe.getAgence(), Constantes.ADD);
                }
            }
        }
        List<Contribuable> list = repos.findByEtatEqualsOrderByIdDesc(Constantes.ADD);
        return list;
    }

    public Images getImagesUsager(Long id) {
        Images image = new Images();
        image.setImg(repos.getImage1(id));
        image.setImg2(repos.getImage2(id));
        image.setImg3(repos.getImage3(id));
        return image;
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
//            if (contribuable.getCni() != null ? contribuable.getCni().length() > 0 : false) {
//                c = repos.findByCniAndEtatEquals(contribuable.getCni(), Constantes.ADD);
//                if (c != null ? c.getId() > 0 : false) {
//                    return new Contribuable("Un contribuable existe déjà avec ce numéro de CNI !");
//                }
//            }

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

    public Long nombreTotalByAuthor() {
        Long nombre = 0L;
        try {

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                nombre = repos.getNombreTotalContribuableByUser(new Utilisateur(user.getId()), Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());

        }
        return nombre;
    }

    public Double nombreJournalier(String dateDebut) {
        Double nombre = 0.0;
        try {
            Date debut = Utils.modifyDateLayout(dateDebut + " 00:00:00 UTC");
            Date fin = Utils.modifyDateLayout(dateDebut + " 23:59:00 UTC");

            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                nombre = repos.getNombreContribuableByUser(new Utilisateur(user.getId()), debut, fin, Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());

        }
        return nombre;
    }

    @Transactional
    public String delete(Long id) {
        try {
            // on cherche les factures du contribuable
            Contribuable contribuable = repos.getOne(id);
            contribuable.setEtat(Constantes.DELETE);
            contribuable.setDate_update(new Date());
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                contribuable.setAuthor(new Utilisateur(user.getId()));
            }
            repos.save(contribuable);
            return "Suppression effectuée avec succès";
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return e.getMessage();
        }
    }


    private String generatedNumero(Contribuable contribuable) {
        String numero = "SONACAM-";
        Long id = repos.getCountId() + 1;
        if (id < 10) {
            return numero + "00" + id;
        } else if (id > 10 && id < 100) {
            return numero + "0" + id;
        } else {
            return numero + id.toString();
        }
    }

    public void save(MultipartFile file) {
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Contribuable> contribuables = ExcelHelper.excelToTutorials(file.getInputStream());
            if (contribuables != null ? !contribuables.isEmpty() : false) {
                contribuables.forEach(element -> {
                    Zone zone = zoneService.findZoneByLibelle(element.getFakeZone());
                    Activite activite = ac.findByLibelle(element.getFakeActivite());
                    if (activite != null ? activite.getId() > 0 : false) {
                        element.setActivite(activite);
                    }
                    if (zone != null ? zone.getId() > 0 : false) {
                        element.setZone(zone);
                    }
                    if (user != null) {
                        element.setAuthor(new Utilisateur(user.getId()));
                    }
                    element.setNumero(generatedNumero(element));
                    element.setDate_update(new Date());
                    element.setDate_update(new Date());
                    element.setEtat(Constantes.ADD);
                    repos.save(element);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Contribuable> getUsagerMontantNull() {
        try {
            UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user != null) {
                Zone zone = new Zone(agentService.getAgentByUser(user.getId()).getZone().getId());
                return repos.findByMontantIsNullAndZoneAndEtatEquals(zone, Constantes.ADD);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
        }

        return null;
    }

    @Transactional
    public Contribuable saveMobile(Usager usager) {
        try {
            if (usager.getContribuable().getFakeImg1() != null) {
                usager.getContribuable().setImage1(usager.getContribuable().getFakeImg1());
            }
            if (usager.getContribuable().getFakeImg2() != null) {
                usager.getContribuable().setImage2(usager.getContribuable().getFakeImg2());
            }
            if (usager.getContribuable().getFakeImg3() != null) {
                usager.getContribuable().setImage3(usager.getContribuable().getFakeImg3());
            }
            Contribuable con = create(usager.getContribuable());
            if (con != null ? con.getId() > 0 : false) {
                usager.getFacture().setContribuable(con);
                Facture facture = factureService.create(usager.getFacture());
                usager.getPeriode().setFacture(facture);
                periodePaiementService.create(usager.getPeriode());

            }
            return con;
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return null;
        }


    }
}


