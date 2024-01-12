package com.dh.apiClinic.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class MedicDTO {
    private Long id;


    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @NotBlank(message = "El numero de matricula no puede estar vacío")
    private String registrationNumber;

    @NotBlank (message = "La especialidad no puede estar vacía")
    private String speciality;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @NotBlank(message = "El usuario es necesario")
    private String userName;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Set<String> roles = new HashSet<>();
}
