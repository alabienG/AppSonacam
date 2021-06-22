package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Pays;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleService {
    public final RoleRepo roleRepo;


    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Autowired
    PaysService service;

    @Transactional
    public Role create(Role role) {
        try {
            Role r = roleRepo.findByLibelleAndEtatEquals(role.getLibelle(), Constantes.ADD);
            if (r != null ? r.getId() > 0 : false) {
                return new Role("ce role exite deja!");
            } else {
                role.setEtat(Constantes.ADD);
                role.setDateSave(new Date());
                role.setDateUpdate(new Date());
                return roleRepo.save(role);
            }

        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Role(e.getMessage());
        }
    }

    @Transactional
    public Role update(Role role) {
        try {
            Role r = roleRepo.findByLibelleAndEtatEquals(role.getLibelle(), Constantes.ADD);
            if (r != null ? r.getId() > 0 : false) {
                return new Role("ce role existe deja");
            } else {
                role.setDateUpdate(new Date());
                return roleRepo.save(role);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new Role(e.getMessage());
        }
    }

    public Role findRoleById(Long id) {
        return roleRepo.findRoleById(id).orElseThrow(() -> new SonacamException("ce role n'existe pas!"));
    }

    @Transactional
    public void delete(Long id) {
        Role role = roleRepo.getOne(id);
        role.setEtat(Constantes.DELETE);
        role.setDateUpdate(new Date());
        roleRepo.save(role);
    }

    public void initRole() {
        try {
            List<Role> list = roleRepo.findByEtatEquals(Constantes.ADD);
            if (list.isEmpty()) {
                // on Ajoute les roles

                Role admin = new Role(ERole.ROLE_ADMIN, "Role administrateur", Constantes.ADD, new Date(), new Date());
                list.add(admin);
                Role agence = new Role(ERole.ROLE_AGENCE, "Role agence", Constantes.ADD, new Date(), new Date());
                list.add(agence);
                Role zone = new Role(ERole.ROLE_ZONE, "Role zone", Constantes.ADD, new Date(), new Date());
                list.add(zone);
                Role delegation = new Role(ERole.ROLE_DELEGATION, "Role delegation", Constantes.ADD, new Date(), new Date());
                list.add(delegation);
                Role antenne = new Role(ERole.ROLE_ANTENNE, "Role antenne", Constantes.ADD, new Date(), new Date());
                list.add(antenne);
                Role collecte = new Role(ERole.ROLE_COLLECTEUR, "Role collecteur", Constantes.ADD, new Date(), new Date());
                list.add(collecte);
                Role enroller = new Role(ERole.ROLE_ENROLEUR, "Role enroleur", Constantes.ADD, new Date(), new Date());
                list.add(enroller);

                for (Role r : list) {
                    create(r);
                }

                // on ajoute le pays
                Pays pays = new Pays();
                pays.setLibelle("Cameroun");
                pays.setDateSave(new Date());
                pays.setDateUpdate(new Date());
                pays.setEtat(Constantes.ADD);
                service.create(pays);


            }

        } catch (Exception e) {
            new SonacamException(e.getMessage());
        }
    }

    public List<Role> findAllRole() {
        return roleRepo.findByEtatEquals(Constantes.ADD);
    }


}
