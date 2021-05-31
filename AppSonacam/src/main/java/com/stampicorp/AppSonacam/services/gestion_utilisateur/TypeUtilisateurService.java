package com.stampicorp.AppSonacam.services.gestion_utilisateur;

import com.stampicorp.AppSonacam.exception.SonacamException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.TypeUtilisateur;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.TypeUtilisateurRepo;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TypeUtilisateurService {
    private final TypeUtilisateurRepo typeUtilisateurRepo;

    @Autowired
    public TypeUtilisateurService(TypeUtilisateurRepo typeUtilisateurRepo) {
        this.typeUtilisateurRepo = typeUtilisateurRepo;
    }

    public List<TypeUtilisateur> findAllTypeUtilisateur() {
        return typeUtilisateurRepo.findByEtatEquals(Constantes.ADD);
    }

    public TypeUtilisateur findById(Long id) {
        return typeUtilisateurRepo.getOne(id);
    }

    public TypeUtilisateur add(TypeUtilisateur typeUtilisateur) {
        try {
            TypeUtilisateur tu = typeUtilisateurRepo.findByLibelleAndEtatEquals(typeUtilisateur.getLibelle(), Constantes.ADD);

            if (tu != null ? tu.getId() > 0 : false) {
                return new TypeUtilisateur("ce type d'utilisateur existe deja!");
            } else {
                tu.setEtat(Constantes.ADD);
                tu.setDateUpdate(new Date());
                tu.setDateSave(new Date());
                return typeUtilisateurRepo.save(tu);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new TypeUtilisateur(e.getMessage());
        }
    }

    public TypeUtilisateur update(TypeUtilisateur typeUtilisateur) {
        try {
            TypeUtilisateur tu = typeUtilisateurRepo.findByLibelleAndEtatEquals(typeUtilisateur.getLibelle(), Constantes.ADD);

            if (tu != null ? tu.getId() > 0 : false) {
                return new TypeUtilisateur("ce type d'utilisateur existe deja!");
            } else {
                tu.setEtat(Constantes.ADD);
                tu.setDateUpdate(new Date());

                return typeUtilisateurRepo.save(tu);
            }
        } catch (Exception e) {
            new SonacamException(e.getMessage());
            return new TypeUtilisateur(e.getMessage());
        }
    }

    public void delete(Long id) {
        TypeUtilisateur typeUtilisateur = typeUtilisateurRepo.getOne(id);
        typeUtilisateur.setEtat(Constantes.DELETE);
        typeUtilisateur.setDateSave(new Date());
        typeUtilisateurRepo.save(typeUtilisateur);
    }
}
