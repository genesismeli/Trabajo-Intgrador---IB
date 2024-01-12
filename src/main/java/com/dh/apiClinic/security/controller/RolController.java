package com.dh.apiClinic.security.controller;

import com.dh.apiClinic.security.entity.Rol;
import com.dh.apiClinic.security.enums.NameRol;
import com.dh.apiClinic.security.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "All rol")
    @GetMapping("/all")
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }


    @Operation(summary = "Roles por nombre")
    @GetMapping("/byName")
    public ResponseEntity<Rol> getRoleByName(NameRol nameRol) {
        if (nameRol == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Rol rol = rolService.getByNameRol(nameRol).orElse(null);

        if (rol == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rol, HttpStatus.OK);
    }
}
