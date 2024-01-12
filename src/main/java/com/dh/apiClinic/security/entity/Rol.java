package com.dh.apiClinic.security.entity;

import com.dh.apiClinic.security.enums.NameRol;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIdentityReference(alwaysAsId = true)
    @Enumerated(EnumType.STRING)
    private NameRol nameRol;



    public Rol() {
    }

    public Rol(NameRol nameRol) {
        this.nameRol = nameRol;
    }
}
