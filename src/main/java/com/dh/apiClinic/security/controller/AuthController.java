package com.dh.apiClinic.security.controller;

import com.dh.apiClinic.security.entity.DTO.ChangePasswordDTO;
import com.dh.apiClinic.security.entity.DTO.JwtDTO;
import com.dh.apiClinic.security.entity.DTO.LoginDTO;
import com.dh.apiClinic.security.entity.DTO.RegisterDTO;
import com.dh.apiClinic.security.entity.Rol;
import com.dh.apiClinic.security.entity.User;
import com.dh.apiClinic.security.enums.NameRol;
import com.dh.apiClinic.security.jwt.JwtProvider;
import com.dh.apiClinic.security.service.RolService;
import com.dh.apiClinic.security.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
@Tag(name = "Authentication", description = "Operations about authentications users")
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    public AuthController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Operation(summary = "Register new user")
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<User>> newUser(@Valid @RequestBody RegisterDTO registerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new ApiResponse<>("Wrong values entered or invalid email", null), HttpStatus.BAD_REQUEST);
        if (userService.existsByUserName(registerDTO.getUserName()))
            return new ResponseEntity<>(new ApiResponse<>("Username already exists", null), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(registerDTO.getEmail()))
            return new ResponseEntity<>(new ApiResponse<>("Email already exists",null), HttpStatus.BAD_REQUEST);
        User user =
                new User(registerDTO.getName(), registerDTO.getUserName(), registerDTO.getEmail(),
                        passwordEncoder.encode(registerDTO.getPassword()));
        Set<Rol> rols = new HashSet<>();
        rols.add(rolService.getByNameRol(NameRol.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found"))); // Agregar el rol de administrador

        user.setRoles(rols);
        userService.save(user);

        ApiResponse<User> response = new ApiResponse<>("User created successfully!!", user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Login a existing user")
    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String jwt = jwtProvider.generateToken(authentication);

        // Obtener el usuario desde la base de datos
        User user = userService.getByUserName(loginDTO.getUserName()).orElse(null);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Verificar si la contraseña ha sido cambiada
        boolean changedPassword = user.getChangedPassword();

        // Crear el DTO de respuesta incluyendo la información sobre si la contraseña ha sido cambiada
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities(), changedPassword);

        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }


    @Operation(summary = "Change user password")
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ApiResponse<>("Invalid input for changing password", null), HttpStatus.BAD_REQUEST);
        }

        // Obtener el token del encabezado de la solicitud
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return new ResponseEntity<>(new ApiResponse<>("Token not found or invalid", null), HttpStatus.UNAUTHORIZED);
        }

        // Eliminar el prefijo "Bearer " del token
        token = token.substring(7);

        // Obtener el nombre de usuario del token utilizando JwtProvider
        String currentUserName = jwtProvider.getUserNameFromToken(token);

        // Buscar al usuario por su nombre de usuario
        User user = userService.getByUserName(currentUserName).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(new ApiResponse<>("User not found", null), HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>(new ApiResponse<>("Incorrect old password", null), HttpStatus.BAD_REQUEST);
        }

        String newPasswordHash = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        user.setPassword(newPasswordHash);
        // Actualizar el campo changedPassword a true
        user.setChangedPassword(true);
        userService.save(user);

        return new ResponseEntity<>(new ApiResponse<>("Password changed successfully", null), HttpStatus.OK);
    }

}
