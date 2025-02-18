package com.questionpro.assessment.controllers;

import com.questionpro.assessment.models.Users;
import com.questionpro.assessment.services.UserService;
import com.questionpro.assessment.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService jwtUserService;

    @Autowired
    private JWTUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users user) {
        String username = user.getUsername();
        String password = passwordEncoder.encode(user.getPassword());
        String role = user.getRole();

        if (role == null || (!role.equals("ADMIN") && !role.equals("USER"))) {
            return ResponseEntity.badRequest().body("Invalid role. Allowed roles: ADMIN, USER");
        }

        Users jwtUser = new Users(username, password, role);
        jwtUserService.addUser(jwtUser);
        return ResponseEntity.ok("User registered successfully as " + role);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Users user) {
        try {
            authenticate(user.getUsername(), user.getPassword());
            UserDetails details = jwtUserService.loadUserByUsername(user.getUsername());
            String jwtToken = jwtTokenUtil.generateToken(details);
            return ResponseEntity.ok(Map.of("token", jwtToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

    private void authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        } catch (DisabledException e) {
            throw new DisabledException("User account is disabled");
        }
    }

}
