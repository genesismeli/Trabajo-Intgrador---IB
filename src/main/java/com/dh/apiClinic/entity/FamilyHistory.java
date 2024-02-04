package com.dh.apiClinic.entity;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "family_history")
public class FamilyHistory {

    @Id
    @Column(name = "family_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coronary_artery_disease")
    private Boolean coronaryArteryDisease;

    @Column(name = "myocardial_infarction")
    private Boolean myocardialInfarction;

    @Column(name = "hypertension")
    private Boolean hypertension;

    @Column(name = "asthma")
    private Boolean asthma;

    @Column(name = "copd")
    private Boolean copd;

    @Column(name = "tuberculosis")
    private Boolean tuberculosis;

    @Column(name = "diabetes")
    private Boolean diabetes;

    @Column(name = "obesity")
    private Boolean obesity;

    @Column(name = "metabolic_syndrome")
    private Boolean metabolicSyndrome;

    @Column(name = "anemia")
    private Boolean anemia;

    @Column(name = "hemophilia")
    private Boolean hemophilia;

    @Column(name = "stroke")
    private Boolean stroke;

    @Column(name = "alzheimer")
    private Boolean alzheimer;

    @Column(name = "multiple_sclerosis")
    private Boolean multipleSclerosis;

    @Column(name = "hemochromatosis")
    private Boolean hemochromatosis;

    @Column(name = "cystic_fibrosis")
    private Boolean cysticFibrosis;

    @Column(name = "depression")
    private Boolean depression;

    @Column(name = "schizophrenia")
    private Boolean schizophrenia;

    @Column(name = "rheumatoid_arthritis")
    private Boolean rheumatoidArthritis;

    @Column(name = "lupus")
    private Boolean lupus;

    @Column(name = "celiac_disease")
    private Boolean celiacDisease;

    @Column(name = "chronic_kidney_disease")
    private Boolean chronicKidneyDisease;

    @Column(name = "diabetic_nephropathy")
    private Boolean diabeticNephropathy;

    @Column(name = "breast_cancer")
    private Boolean breastCancer;

    @Column(name = "colon_cancer")
    private Boolean colonCancer;

    @Column(name = "lung_cancer")
    private Boolean lungCancer;

    @Column(name = "inflammatory_bowel_disease")
    private Boolean inflammatoryBowelDisease;

    @Column(name = "hypothyroidism")
    private Boolean hypothyroidism;

    @Column(name = "other_medical_history")
    private String otherMedicalHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;
}
