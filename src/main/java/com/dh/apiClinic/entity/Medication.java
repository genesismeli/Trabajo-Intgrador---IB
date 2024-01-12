package com.dh.apiClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "medication")
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class Medication {

    @Id
    @Column(name = "medication_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vademecum_id")
    @JsonIgnoreProperties("hibernateLazyInitializer")
    private Vademecum vademecum;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;

    @Column(name= "notes")
    private String notes;


}
