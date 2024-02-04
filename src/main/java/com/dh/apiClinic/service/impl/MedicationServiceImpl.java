package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.entity.Medication;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IMedicationRepository;
import com.dh.apiClinic.service.IMedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class MedicationServiceImpl implements IMedicationService {

    @Autowired
    private IMedicationRepository medicationRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(MedicationDTO medicationDTO) {
        if (medicationDTO != null) {
            Medication medication = mapper.convertValue(medicationDTO, Medication.class);
            medicationRepository.save(medication);
        } else {
            throw new ResourceNotFoundException("Medication", "id", "id not found: " + medicationDTO.getId());
        }

    }

    @Override
    public Collection<MedicationDTO> findAllMedication() {
        List<Medication> medications = medicationRepository.findAll();
        Set<MedicationDTO> medicationDTO = new HashSet<>();

        for (Medication medication : medications) {
            medicationDTO.add(mapper.convertValue(medication, MedicationDTO.class));
        }
        return medicationDTO;

    }

    @Override
    public MedicationDTO findMedicationById(Long id) {
        Medication medication = medicationRepository.findById(id).get();
        MedicationDTO medicationDTO = null;
        if (medication != null) {
            medicationDTO = mapper.convertValue(medication, MedicationDTO.class);
        }
        return medicationDTO;
    }

    @Override
    public void saveMedication(MedicationDTO newMedicationDTO) {
        saveMethod(newMedicationDTO);
    }

    @Override
    public void deleteMedication(Long id) {
        Medication medication = medicationRepository.findById(id).get();
        medicationRepository.deleteById(id);

    }

    @Override
    public void updateMedication(MedicationDTO newMedicationDTO) {
        saveMethod(newMedicationDTO);
    }

    @Override
    public MedicationDTO convertEntityToDto(Medication medication) {
        if (medication == null) {
            return null;
        }

        MedicationDTO medicationDTO = new MedicationDTO();
        medicationDTO.setId(medication.getId());
        medicationDTO.setVademecum(medication.getVademecum());
        medicationDTO.setNotes(medication.getNotes());


        return medicationDTO;
    }


}
