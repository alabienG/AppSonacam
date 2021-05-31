package com.stampicorp.AppSonacam.controller.gestion_utilisateur;

import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.RoleService;
import com.stampicorp.AppSonacam.utils.Constantes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constantes.PATH + "role")
@CrossOrigin("*")
public class RoleRessource  {
    private final RoleService roleService;

    public RoleRessource(RoleService roleService) {
        this.roleService = roleService;
    }
@GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRole(){
        List<Role> roles =roleService.findAllRole();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id){
        Role role = roleService.findRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity addRole(@RequestBody Role role){
        Role newRole= roleService.create(role);
        if (newRole!=null? newRole.getId()>0: false){
            return new ResponseEntity<>(newRole, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(newRole.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@PutMapping("/update")
public ResponseEntity updateRole(@RequestBody Role role){
    Role updateRole= roleService.create(role);
    if (updateRole!=null? updateRole.getId()>0: false){
        return new ResponseEntity<>(updateRole, HttpStatus.CREATED);
    }else {
        return new ResponseEntity<>(updateRole.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("/delete/{id}")

public ResponseEntity<Role> deleteRole(@PathVariable("id") Long id) {
    roleService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
}
}
