package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_enrolement.Facture;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Zone;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.UtilisateurRepo;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import com.stampicorp.AppSonacam.utils.RoleNameUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UtilisateurService {
    @Autowired
    UtilisateurRepo repos;
    @Autowired
    RoleRepo roleRepository;

    public List<Utilisateur> all() {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getId());
        return repos.findByEtatEqualsOrderById(Constantes.ADD);
    }

    public List<Utilisateur> findByRole(Long idRole) {
        return null;
    }

//    public List<Utilisateur> findByZone(Long idZone) {
//        return repos.findByZoneAndEtatEqualsOrderById(new Zone(idZone), Constantes.ADD);
//    }

    public Utilisateur getOne(Long id) {
        return repos.getOne(id);
    }

    public Utilisateur addRole(Long idUSer, Long idRole) {
        try {
            repos.addRole(idUSer, idRole);
            return new Utilisateur("Role ajouter !");
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Utilisateur(e.getMessage());
        }
    }

    public Utilisateur addRole(RoleNameUser roleNameUser) {
        try {
            Utilisateur user = repos.getOne(roleNameUser.getIdUser());
            Set<Role> roles = user.getRoles();
            System.out.println(roleNameUser.getName());
            if (roleNameUser.getName().equals("admin")) {
                Role adminRole = roleRepository.findByLibelle(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                if (roleNameUser.isInsert()) {
                    roles.add(adminRole);
                } else {
                    user.getRoles().remove(adminRole);
                }
            } else if (roleNameUser.getName().equals("collecte")) {
                Role modRole = roleRepository.findByLibelle(ERole.ROLE_COLLECTEUR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                if (roleNameUser.isInsert()) {
                    roles.add(modRole);
                } else {
                    user.getRoles().remove(modRole);
                }
            } else if (roleNameUser.getName().equals("enroller")) {
                Role enroleRole = roleRepository.findByLibelle(ERole.ROLE_ENROLEUR)
                        .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                if (roleNameUser.isInsert()) {
                    roles.add(enroleRole);
                } else {
                    roles.remove(enroleRole);
                }
            } else if (roleNameUser.getName().equals("zone")) {
                Role zoneRole = roleRepository.findByLibelle(ERole.ROLE_ZONE)
                        .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                if (roleNameUser.isInsert()) {
                    roles.add(zoneRole);
                } else {
                    roles.remove(zoneRole);
                }
            } else if (roleNameUser.getName().equals("antenne")) {
                Role antenneRole = roleRepository.findByLibelle(ERole.ROLE_ANTENNE)
                        .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                if (roleNameUser.isInsert()) {
                    roles.add(antenneRole);
                } else {
                    roles.remove(antenneRole);
                }
            } else if (roleNameUser.getName().equals("agence")) {
                Role agenceRole = roleRepository.findByLibelle(ERole.ROLE_AGENCE)
                        .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                if (roleNameUser.isInsert()) {
                    roles.add(agenceRole);
                } else {
                    roles.remove(agenceRole);
                }
            } else if (roleNameUser.getName().equals("delegation")) {
                Role delegueRole = roleRepository.findByLibelle(ERole.ROLE_DELEGATION)
                        .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                if (roleNameUser.isInsert()) {
                    roles.add(delegueRole);
                } else {
                    roles.remove(delegueRole);
                }
            }
            user.setRoles(roles);
            repos.save(user);
            return user;
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Utilisateur(e.getMessage());
        }
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

    public String generatedMatricule() {
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

