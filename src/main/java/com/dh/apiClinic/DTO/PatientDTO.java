package com.dh.apiClinic.DTO;

import com.dh.apiClinic.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter

public class PatientDTO {

    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    @NotBlank(message = "El DNI es obligatorio")
    private String dni;
    @NotBlank(message = "La fecha de nacimiento es obligatoria")
    private Date birthdate;
    @NotBlank(message = "El sexo es obligatorio")
    private Gender gender;
    @NotBlank(message = "La dirección es obligatoria")
    private String adress;
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe contener 10 dígitos")
    private String phone;
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

}
