package com.dh.apiClinic.DTO;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter

public class VamedecumDTO {

    private Long id;
    private String laboratorio_titular;
    private String numero_certificado;
    private String nombre_generico;
    private String concentracion;
    private String forma_farmaceutica;
    private String presentacion;
    private String nombre_comercial;


}
