package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.DTO.PatientDTO;

import com.dh.apiClinic.entity.Patient;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IPatientRepository;
import com.dh.apiClinic.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(PatientDTO patientDTO) {
        if (patientDTO != null) {
            Patient patient = mapper.convertValue(patientDTO, Patient.class);
            patientRepository.save(patient);
        } else {
            throw new ResourceNotFoundException("Patient", "id", "id not found: " + patientDTO.getId());
        }

    }


    @Override
    public PageDTO<PatientDTO> findAllPatients(int page, int size) {
        Page<Patient> patientsPage = patientRepository.findAll(PageRequest.of(page, size));

        List<PatientDTO> patientDTOs = patientsPage.getContent().stream()
                .map(patient -> mapper.convertValue(patient, PatientDTO.class))
                .collect(Collectors.toList());

        return new PageDTO<>(
                patientDTOs,
                patientsPage.getTotalPages(),
                patientsPage.getTotalElements(),
                patientsPage.getNumber(),
                patientsPage.getSize()
        );
    }

    public Patient findById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }


    @Override
    public PatientDTO findPatientById(Long id) {

        Patient patient = patientRepository.findById(id).get();
        PatientDTO patientDTO = null;
        if (patient != null) {
            patientDTO = mapper.convertValue(patient, PatientDTO.class);
        }
        return patientDTO;
    }

    @Override
    public Long findPatientIdByUserName(String userName) {
        Patient patient = patientRepository.findByUserName(userName);
        if (patient != null) {
            return patient.getId();
        } else {
            throw new EntityNotFoundException("Patient not found with username: " + userName);
        }
    }

    @Override
    public void savePatient(PatientDTO newPatientDTO) {
        saveMethod(newPatientDTO);
    }

    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).get();
        patientRepository.deleteById(id);
    }

    @Override
    public void updatePatient(PatientDTO newPatientDTO) {
        saveMethod(newPatientDTO);
    }

    @Override
    public PatientDTO convertEntityToDto(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setName(patient.getName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setDni(patient.getDni());
        patientDTO.setBirthdate(patient.getBirthdate());
        patientDTO.setGender(patient.getGender());
        patientDTO.setAdress(patient.getAdress());
        patientDTO.setPhone(patient.getPhone());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setUserName(patient.getUserName());
        patientDTO.setPassword(patient.getPassword());

        return patientDTO;
    }

    @Override
    public List<PatientDTO> searchPatients(String name, String lastName, String dni) {
        List<Patient> patients = patientRepository.findByNameContainingOrLastNameContainingOrDniContaining(name, lastName, dni);

        // Mapear la lista de entidades Patient a una lista de DTOs PatientDTO
        List<PatientDTO> patientDTOs = patients.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return patientDTOs;
    }

}
