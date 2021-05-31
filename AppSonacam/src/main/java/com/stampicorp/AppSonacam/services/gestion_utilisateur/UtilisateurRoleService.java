package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.UtilisateurRole;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.UtilisateurRoleRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UtilisateurRoleService  {

    private final UtilisateurRoleRepo utilisateurRoleRepo;
@Autowired
    public UtilisateurRoleService(UtilisateurRoleRepo utilisateurRoleRepo) {
        this.utilisateurRoleRepo = utilisateurRoleRepo;
    }
    @Transactional
    public UtilisateurRole add(UtilisateurRole utilisateurRole){
    try {
        UtilisateurRole ur= utilisateurRoleRepo.findByRoleAndUtilisateurAndEtatEquals(utilisateurRole.getRole(), utilisateurRole.getUtilisateur(), Constantes.ADD);
        if(ur!=null? ur.getId()>0: false){
            return new UtilisateurRole("ce role existe deja pour cet utilisateur");
        }else{
            ur.setEtat(Constantes.ADD);
            ur.setDateUpdate(new Date());
            ur.getDateSave(new Date());
            return utilisateurRoleRepo.save(ur);
        }
    }catch (Exception e){
        new SonacamException(e.getMessage());
        return new UtilisateurRole(e.getMessage());
    }
    }
@Transactional
    public UtilisateurRole update(UtilisateurRole utilisateurRole){
        try {
            UtilisateurRole ur= utilisateurRoleRepo.findByRoleAndUtilisateurAndEtatEquals(utilisateurRole.getRole(), utilisateurRole.getUtilisateur(), Constantes.ADD);
            if(ur!=null? ur.getId()>0: false){
                return new UtilisateurRole("ce role existe deja pour cet utilisateur");
            }else{
                ur.setEtat(Constantes.ADD);
                ur.setDateUpdate(new Date());
                return utilisateurRoleRepo.save(ur);
            }
        }catch (Exception e){
            new SonacamException(e.getMessage());
            return new UtilisateurRole(e.getMessage());
        }
    }
@Transactional
    public void delete (Long id){
        UtilisateurRole utilisateurRole = utilisateurRoleRepo.getOne(id);
        utilisateurRole.setEtat(Constantes.DELETE);
        utilisateurRole.setDateUpdate(new Date());
        utilisateurRoleRepo.save(utilisateurRole);
    }

}
