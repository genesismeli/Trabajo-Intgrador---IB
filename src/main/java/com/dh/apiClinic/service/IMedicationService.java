package com.dh.apiClinic.service;


import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.entity.Medication;

import java.util.Collection;

public interface IMedicationService {

    Collection<MedicationDTO> findAllMedication();
    MedicationDTO findMedicationById(Long id);

    void saveMedication(MedicationDTO newMedicationDTO);

    void deleteMedication(Long id);

    void updateMedication(MedicationDTO newMedicationDTO);

    MedicationDTO convertEntityToDto(Medication medication);


}
