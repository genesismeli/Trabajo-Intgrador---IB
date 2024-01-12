package com.dh.apiClinic.controller;

import com.dh.apiClinic.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;

@Tag(name = "UserJwT", description = "Obtencion de datos por JWT")
@RestController
@RequestMapping("/user")
public class UserController {

    private final JwtProvider jwtProvider;

    @Autowired
    public UserController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/username")
    public String getLoggedInUsername(HttpServletRequest request) {
        // Obtener el token del encabezado de la solicitud
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            // Eliminar el prefijo "Bearer " del token
            token = token.substring(7);

            // Obtener el nombre de usuario del token utilizando JwtProvider
            return jwtProvider.getUserNameFromToken(token);
        }
        // Si no hay token o el formato no es correcto, puedes devolver un valor por defecto o lanzar una excepción según tus necesidades
        return "Usuario no encontrado";
    }

    @GetMapping("/authorities")
    public ApiResponse<Collection<String>> getUserAuthorities(Authentication authentication) {
        ApiResponse<Collection<String>> response = new ApiResponse<>();

        if (authentication != null && authentication.isAuthenticated()) {
            Collection<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            response.setMessage("Authorities fetched successfully");
            response.setModel(authorities);
        } else {
            response.setMessage("User not authenticated");
            // Puedes devolver un mensaje específico o personalizar según tus necesidades
        }

        return response;
    }

}
