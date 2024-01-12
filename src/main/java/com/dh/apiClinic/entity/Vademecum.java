package com.dh.apiClinic.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Vademecum") // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@Getter
@Setter

public class Vademecum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vademecum_id")
    private Long id;

    @Column(name = "laboratorio_titular")
    private String laboratorio_titular;

    @Column(name = "numero_certificado")
    private String numero_certificado;

    @Column(name = "nombre_generico")
    private String nombre_generico;

    @Column(name = "concentracion")
    private String concentracion;

    @Column(name = "forma_farmaceutica")
    private String forma_farmaceutica;

    @Column(name = "presentacion")
    private String presentacion;

    @Column(name = "nombre_comercial")
    private String nombre_comercial;



}
