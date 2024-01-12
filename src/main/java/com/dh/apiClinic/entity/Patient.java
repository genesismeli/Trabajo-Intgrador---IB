package com.dh.apiClinic.entity;


import com.dh.apiClinic.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Date;


@ToString
@Setter
@Getter
@Entity
@Table(name = "patients")

@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Patient {
    @Id
    @Column(name = "patient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String lastName;
    @Column
    private String dni;
    @Column
    private Date birthdate;
    @Column
    private Gender gender;
    @Column
    private String adress;
    @Column
    private String phone;
    @Column
    private String email;


}
