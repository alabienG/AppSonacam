package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class RoleService {
    public final RoleRepo roleRepo;

    @Autowired
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

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

    public List<Role> findAllRole() {
        return roleRepo.findByEtatEquals(Constantes.ADD);
    }


}
