package com.dh.apiClinic.controller;

import com.dh.apiClinic.security.jwt.JwtProvider;
import com.dh.apiClinic.security.jwt.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/token")
    public ResponseEntity<String> Token(@RequestHeader("Authorization") String token) {
        try {
            jwtProvider.validateToken(token);
            // Lógica normal aquí si el token es válido
            return new ResponseEntity<>("Operación exitosa", HttpStatus.OK);
        } catch (TokenExpiredException e) {
            // Manejo de la excepción por token expirado
            return new ResponseEntity<>("El token ha expirado. Vuelve a iniciar sesión.", HttpStatus.UNAUTHORIZED);
        }
    }
}
