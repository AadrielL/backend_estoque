package com.seu.estoque.controller;

import com.seu.estoque.model.LoginRequest;
import com.seu.estoque.model.LoginResponse;
import com.seu.estoque.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autentica o usuário
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails;
         // Gera o token JWT
         String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new LoginResponse(token)); // Retorna o token
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
    }
}