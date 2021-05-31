package com.stampicorp.AppSonacam.services.gestion_enrolement;

import com.stampicorp.AppSonacam.models.gestion_enrolement.Activite;
import com.stampicorp.AppSonacam.repos.gestion_enrolement.ActiviteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiviteService {
    @Autowired
    ActiviteRepo repos;

//    public List<Activite> all(){
//        return repos
//    }
}
