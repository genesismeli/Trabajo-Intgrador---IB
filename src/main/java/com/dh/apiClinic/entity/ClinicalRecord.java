package com.dh.apiClinic.entity;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clinical_records")
@Data
@NoArgsConstructor
@Getter
@Setter

public class ClinicalRecord {

    @Id
    @Column(name = "clinical_records_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "date")
    private Date date;

    @PrePersist
    protected void onCreate() {
        this.date = new Date(); // Establece la fecha de creación con la fecha actual al persistir por primera vez
    }

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "clinical_record_medications",
            joinColumns = @JoinColumn(name = "clinical_record_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<Medication> medications;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "clinical_record_diagnoses",
            joinColumns = @JoinColumn(name = "clinical_record_id"),
            inverseJoinColumns = @JoinColumn(name = "diagnosis_id")
    )
    private List<Diagnosis> diagnoses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "clinical_record_physical_exams",
            joinColumns = @JoinColumn(name = "clinical_record_id"),
            inverseJoinColumns = @JoinColumn(name = "physical_exam_id")
    )
    private List<PhysicalExam> physicalExams;

    @Column(name= "notes")
    private String notes;

    @Column(name= "reason")
    private String reason;

    @Column(name= "anamnesis")
    private String anamnesis;

    @Transient
    private String gptSearchQuery;


}

