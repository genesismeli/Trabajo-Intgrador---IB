package com.dh.apiClinic.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter

@Table(name = "code_cie10")


public class CodeCie10 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_cie10_id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

}