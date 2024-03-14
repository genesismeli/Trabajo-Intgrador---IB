package com.dh.apiClinic.service.impl;


import com.dh.apiClinic.DTO.*;
import com.dh.apiClinic.entity.*;
import com.dh.apiClinic.exception.ResourceNotFoundException;

import com.dh.apiClinic.repository.IClinicalRecordRepository;
import com.dh.apiClinic.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClinicalRecordServiceImpl implements IClinicalRecordService {

    @Autowired
    public ClinicalRecordServiceImpl(IClinicalRecordRepository clinicalRecordRepository) {
        this.clinicalRecordRepository = clinicalRecordRepository;
    }

    @Autowired
    private IClinicalRecordRepository clinicalRecordRepository;

    @Autowired
    private IDiagnosisService diagnosisService;

    @Autowired
    private IMedicationService medicationService;

    @Autowired
    private IPhysicalExamService physicalExamService;

    @Autowired
    private IPersonalHistoryService personalHistoryService;

    @Autowired
    private IPatientService patientService;

    @Autowired
    private IMedicService medicService;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(ClinicalRecordDTO clinicalRecordDTO) {
        if (clinicalRecordDTO != null) {
            ClinicalRecord clinicalRecord = mapper.convertValue(clinicalRecordDTO, ClinicalRecord.class);
            clinicalRecordRepository.save(clinicalRecord);
        } else {
            throw new ResourceNotFoundException("ClinicalRecord", "id", "id not found: " + clinicalRecordDTO.getId());
        }

    }

    @Override
    public Collection<ClinicalRecordDTO> findAllClinicalRecord(Pageable pageable) {
        Page<ClinicalRecord> clinicalRecords = clinicalRecordRepository.findAll(pageable);
        return clinicalRecords.map(clinicalRecord -> mapper.convertValue(clinicalRecord, ClinicalRecordDTO.class)).getContent();
    }

    @Override
    public ClinicalRecordDTO findClinicalRecordById(Long id) {
        ClinicalRecord clinic = clinicalRecordRepository.findById(id).get();
        ClinicalRecordDTO clinicalRecordDTO = null;
        if (clinic != null) {
            clinicalRecordDTO = mapper.convertValue(clinic, ClinicalRecordDTO.class);
        }
        return clinicalRecordDTO;
    }

    @Override
    public void saveClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO) {
        saveMethod(newClinicalRecordDTO);
    }

    @Override
    public void deleteClinicalRecord(Long id) {
        ClinicalRecord clinicalRecord = clinicalRecordRepository.findById(id).get();
        clinicalRecordRepository.deleteById(id);


    }

    @Override
    public void updateClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO) {
        saveMethod(newClinicalRecordDTO);
    }

    @Override
    public PageDTO<ClinicalRecordDTO> findClinicalRecordsByPatientId(Long patientId, int page, int size, Sort sort) {
        Page<ClinicalRecord> clinicalRecordsPage = clinicalRecordRepository.findByPatientId(patientId, PageRequest.of(page, size, sort));

        List<ClinicalRecordDTO> clinicalRecordDTOs = clinicalRecordsPage.getContent().stream()
                .map(clinicalRecord -> mapper.convertValue(clinicalRecord, ClinicalRecordDTO.class))
                .collect(Collectors.toList());

        return new PageDTO<>(
                clinicalRecordDTOs,
                clinicalRecordsPage.getTotalPages(),
                clinicalRecordsPage.getTotalElements(),
                clinicalRecordsPage.getNumber(),
                clinicalRecordsPage.getSize()
        );
    }

    @Override
    public ClinicalRecordDTO convertEntityToDto(ClinicalRecord clinicalRecord) {
        ClinicalRecordDTO clinicalRecordDTO = new ClinicalRecordDTO();
        clinicalRecordDTO.setId(clinicalRecord.getId());

        // Mapea el ID del paciente
        if (clinicalRecord.getPatient() != null) {
            clinicalRecordDTO.setPatient(patientService.convertEntityToDto(clinicalRecord.getPatient()));
        }
        // Mapea el ID del paciente
        if (clinicalRecord.getMedic() != null) {
            clinicalRecordDTO.setMedic(medicService.convertEntityToDto(clinicalRecord.getMedic()));
        }

        // Mapea los PhysicalExams
        List<PhysicalExamDTO> physicalExamDTOs = new ArrayList<>();
        for (PhysicalExam physicalExam : clinicalRecord.getPhysicalExams()) {
            PhysicalExamDTO physicalExamDTO = physicalExamService.convertEntityToDto(physicalExam);
            physicalExamDTOs.add(physicalExamDTO);
        }
        clinicalRecordDTO.setPhysicalExams(physicalExamDTOs);

        // Mapea las Medications
        List<MedicationDTO> medicationDTOs = new ArrayList<>();
        for (Medication medication : clinicalRecord.getMedications()) {
            MedicationDTO medicationDTO = medicationService.convertEntityToDto(medication);
            medicationDTOs.add(medicationDTO);
        }
        clinicalRecordDTO.setMedications(medicationDTOs);

        // Mapea los Diagnoses
        List<DiagnosisDTO> diagnosisDTOs = new ArrayList<>();
        for (Diagnosis diagnosis : clinicalRecord.getDiagnoses()) {
          DiagnosisDTO diagnosisDTO = diagnosisService.convertEntityToDto(diagnosis);
          diagnosisDTOs.add(diagnosisDTO);
        }
        clinicalRecordDTO.setDiagnoses(diagnosisDTOs);

        // Mapea las PersonalHistory
        List<PersonalHistoryDTO> personalHistoryDTOs = new ArrayList<>();
        for (PersonalHistory personalHistory : clinicalRecord.getPersonalHistorys()) {
            PersonalHistoryDTO personalHistoryDTO = personalHistoryService.convertEntityToDto(personalHistory);
        personalHistoryDTOs.add(personalHistoryDTO);
        }
        clinicalRecordDTO.setPersonalHistorys(personalHistoryDTOs);

        return clinicalRecordDTO;
        }


    @Override
    public MedicDTO findMedicByClinicalRecordId(Long id) {
        ClinicalRecord clinicalRecord = clinicalRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClinicalRecord", "id", id.toString()));

        if (clinicalRecord.getMedic() != null) {
            return medicService.convertEntityToDto(clinicalRecord.getMedic());
        } else {
            throw new ResourceNotFoundException("Medic", "id", "No se encontró médico asociado a la ficha clínica con ID: " + id);
        }
    }


}



