package com.dh.apiClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "diagnosis") // Nombre de la tabla en la base de datos
public class Diagnosis {

    @Id
    @JoinColumn(name = "diagnosis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "code")
    //private String code;

    //@Column(name = "description")
    //private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_cie10_id")
    @JsonIgnoreProperties("hibernateLazyInitializer")
    private CodeCie10 codeCie10;

    //@Convert(converter = DiagnosisStatusConverter.class)
    //@Column(name = "status")
    //private DiagnosisStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;

}

