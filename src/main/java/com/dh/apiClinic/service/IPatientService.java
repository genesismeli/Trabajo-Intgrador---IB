package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.DTO.PatientDTO;
import com.dh.apiClinic.entity.Patient;

import java.util.List;


public interface IPatientService {


    PageDTO<PatientDTO> findAllPatients(int page, int size);

    PatientDTO findPatientById(Long id);

    Long findPatientIdByUserName(String userName);

    void savePatient(PatientDTO newPatientDTO);

    void deletePatient(Long id);

    void updatePatient(PatientDTO newPatientDTO);

    PatientDTO convertEntityToDto(Patient patient);

    Patient findById(Long id);

    List<PatientDTO> searchPatients(String name, String lastName, String dni);

}
