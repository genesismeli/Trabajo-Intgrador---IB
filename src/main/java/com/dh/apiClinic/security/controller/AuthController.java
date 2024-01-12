package com.dh.apiClinic.security.controller;

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
        //Set<Rol> rols = new HashSet<>();
        //if (registerDTO.getRoles().contains("ADMIN")) {
          //  rols.add(rolService.getByNameRol(NameRol.ROLE_ADMIN).get());
        //} else {
         //   rols.add(rolService.getByNameRol(NameRol.ROLE_USER).get());
        //}
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
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(),loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(),userDetails.getAuthorities());
        return new ResponseEntity<>(jwtDTO,HttpStatus.OK);
    }


}
