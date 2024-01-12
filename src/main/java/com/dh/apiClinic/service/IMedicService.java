package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.MedicDTO;
import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.entity.Medic;

import java.util.List;


public interface IMedicService {

    PageDTO<MedicDTO> findAllMedics(int page, int size);
    MedicDTO findMedicById(Long id);

    void saveMedic(MedicDTO newMedicDTO);

    void deleteMedic(Long id);

    void updateMedic(MedicDTO newMedicDTO);

    MedicDTO convertEntityToDto(Medic medic);

    List<MedicDTO> searchMedics(String name, String lastName, String registrationNumber);
}
