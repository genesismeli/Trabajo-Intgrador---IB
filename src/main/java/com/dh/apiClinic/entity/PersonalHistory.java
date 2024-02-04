package com.dh.apiClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "personal_history")
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class PersonalHistory {

    @Id
    @Column(name = "personal_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "diabetes")
    private Boolean diabetes;

    @Column(name = "hypertension")
    private Boolean hypertension;

    @Column(name = "coronary_artery_disease")
    private Boolean coronaryArteryDisease;

    @Column(name = "bronchialAsthma")
    private Boolean bronchialAsthma;

    @Column(name = "bronchopulmonaryDisease")
    private Boolean bronchopulmonaryDisease;

    @Column(name = "psychopathy")
    private Boolean psychopathy;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "tuberculosis")
    private Boolean tuberculosis;

    @Column(name = "sexually_transmitted_infection")
    private Boolean sexuallyTransmittedInfection;

    @Column(name = "gout")
    private Boolean gout;

    @Column(name = "endocrine_disorders")
    private Boolean endocrineDisorders;

    // Otros campos para enfermedades metabólicas, nefrológicas, urológicas, etc.

    @Column(name = "nephropathies")
    private Boolean nephropathies;

    @Column(name = "uropathies")
    private Boolean uropathies;

    @Column(name = "hematopathies")
    private Boolean hematopathies;

    @Column(name = "ulcer")
    private Boolean ulcer;

    @Column(name = "hepatitis")
    private Boolean hepatitis;

    @Column(name = "fever")
    private Boolean fever;

    @Column(name = "other_medical_history")
    private String otherMedicalHistory;

    // Puedes agregar más campos según tus necesidades

    @Column(name = "cancer")
    private String cancer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;

}
