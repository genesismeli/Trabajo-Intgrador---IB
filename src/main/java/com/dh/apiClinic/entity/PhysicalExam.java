package com.dh.apiClinic.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;

@Entity
@Table(name = "physical_exams") // Nombre de la tabla en la base de datos
@Data
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value={"hibernateLazyInitializer"})
public class PhysicalExam {
    @Id
    @Column(name = "physical_exam_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "heart_rate")
    private Integer heartRate; // Frecuencia Cardiaca

    @Column(name = "oxygen_saturation")
    @DecimalMin(value = "0.0", message = "La saturación de oxígeno no puede ser menor a 0")
    private Double oxygenSaturation; // Saturación parcial de Oxígeno

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate; // Frecuencia Respiratoria

    @Column(name = "systolic_pressure")
    private Double systolicPressure; // Presión Sistólica

    @Column(name = "diastolic_pressure")
    private Double diastolicPressure; // Presión Diastólica

    @Column(name = "beats_per_minute")
    private Integer beatsPerMinute; // Latidos por minuto

    @Column(name = "glucose")
    @DecimalMin(value = "0.0", message = "La glucosa no puede ser menor a 0")
    private Double glucose; // Glucosa

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinical_records_id")
    private ClinicalRecord clinicalRecord;


}
