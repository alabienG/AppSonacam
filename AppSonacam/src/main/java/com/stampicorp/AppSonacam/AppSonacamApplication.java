package com.stampicorp.AppSonacam;

import com.stampicorp.AppSonacam.services.gestion_utilisateur.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class AppSonacamApplication {



	public static void main(String[] args) {
		SpringApplication.run(AppSonacamApplication.class, args);
	}


}
