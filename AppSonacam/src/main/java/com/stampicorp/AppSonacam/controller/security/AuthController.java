package com.stampicorp.AppSonacam.controller.security;

import com.stampicorp.AppSonacam.exception.TokenRefreshException;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Role;
import com.stampicorp.AppSonacam.models.gestion_utilisateur.Utilisateur;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.RoleRepo;
import com.stampicorp.AppSonacam.repos.gestion_utilisateur.UtilisateurRepo;
import com.stampicorp.AppSonacam.request.RefreshToken;
import com.stampicorp.AppSonacam.request.exchange.LoginRequest;
import com.stampicorp.AppSonacam.request.exchange.SignupRequest;
import com.stampicorp.AppSonacam.request.exchange.TokenRefreshResponse;
import com.stampicorp.AppSonacam.request.response.JwtResponse;
import com.stampicorp.AppSonacam.request.response.MessageResponse;
import com.stampicorp.AppSonacam.security.JwtUtils;
import com.stampicorp.AppSonacam.security.TokenRefreshRequest;
import com.stampicorp.AppSonacam.security.UserDetailsImpl;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.RoleService;
import com.stampicorp.AppSonacam.services.gestion_utilisateur.UtilisateurService;
import com.stampicorp.AppSonacam.services.secutity.RefreshTokenService;
import com.stampicorp.AppSonacam.utils.Constantes;
import com.stampicorp.AppSonacam.utils.ERole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Constantes.PATH + "auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UtilisateurService utilisateurService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    RoleService roleService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // create if not exist role

        roleService.initRole();

        // Create new user's account
        Utilisateur user = new Utilisateur(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
//            System.out.println("roles =" + signUpRequest.getRole());
        Set<String> strRoles = Collections.singleton(signUpRequest.getRole());
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByLibelle(ERole.ROLE_ZONE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role != null) {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByLibelle(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);

                            break;
                        case "collecte":
                            Role modRole = roleRepository.findByLibelle(ERole.ROLE_COLLECTEUR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(modRole);

                            break;
                        case "enroller":
                            Role enroleRole = roleRepository.findByLibelle(ERole.ROLE_ENROLEUR)
                                    .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                            roles.add(enroleRole);
                        case "zone":
                            Role zoneRole = roleRepository.findByLibelle(ERole.ROLE_ZONE)
                                    .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                            roles.add(zoneRole);
                        case "antenne":
                            Role antenneRole = roleRepository.findByLibelle(ERole.ROLE_ANTENNE)
                                    .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                            roles.add(antenneRole);
                        case "agence":
                            Role agenceRole = roleRepository.findByLibelle(ERole.ROLE_AGENCE)
                                    .orElseThrow(() -> new RuntimeException("Error : Role is not found"));
                            roles.add(agenceRole);
                        default:
                            Role userRole = roleRepository.findByLibelle(ERole.ROLE_ENROLEUR)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                }

            });
        }

        user.setRoles(roles);
        user.setEtat(Constantes.ADD);
        user.setNom(signUpRequest.getNom());
        user.setPrenom(signUpRequest.getPrenom());
        user.setSexe(signUpRequest.getSexe());
        user.setMatricule(utilisateurService.generatedMatricule());
        user.setAgent(signUpRequest.getAgent());
        user.setDateSave(new Date());
        user.setDateUpdate(new Date());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
