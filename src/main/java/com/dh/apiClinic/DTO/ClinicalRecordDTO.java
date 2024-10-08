package com.dh.apiClinic.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClinicalRecordDTO {
    private Long id;
    private Date date;
    private PatientDTO patient;
    private MedicDTO medic;
    private List<PhysicalExamDTO> physicalExams;
    private List<MedicationDTO> medications;
    private List<DiagnosisDTO> diagnoses;
    private String notes;
    private String reason;
    private String anamnesis;
    private String medicalCertificate;
    private List<PersonalHistoryDTO> personalHistorys;
    private List<FamilyHistoryDTO> familyHistorys;
    private String complementaryExams;

}

