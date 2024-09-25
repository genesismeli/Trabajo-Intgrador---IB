package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.MedicDTO;
import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.entity.ClinicalRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;

public interface IClinicalRecordService {

    PageDTO<ClinicalRecordDTO> findClinicalRecordsByPatientId(Long patientId, int page, int size, Sort sort);

    Collection<ClinicalRecordDTO> findAllClinicalRecord(Pageable pageable);

    ClinicalRecordDTO findClinicalRecordById(Long id);

    void saveClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO);

    void deleteClinicalRecord(Long id);

    void updateClinicalRecord(ClinicalRecordDTO newClinicalRecordDTO);

    ClinicalRecordDTO convertEntityToDto(ClinicalRecord clinicalRecord);

    MedicDTO findMedicByClinicalRecordId(Long id);
}
