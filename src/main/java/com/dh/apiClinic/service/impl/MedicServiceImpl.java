package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.DTO.MedicDTO;
import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.entity.Medic;
import com.dh.apiClinic.entity.Patient;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IMedicRepository;
import com.dh.apiClinic.service.IMedicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicServiceImpl implements IMedicService {

    @Autowired
    private IMedicRepository medicRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(MedicDTO medicDTO) {
        if (medicDTO != null) {
            Medic medic = mapper.convertValue(medicDTO, Medic.class);
            medicRepository.save(medic);
        } else {
            throw new ResourceNotFoundException("Medic", "id", "id not found: " + medicDTO.getId());
        }

    }

    @Override
    public PageDTO<MedicDTO> findAllMedics(int page, int size) {
        Page<Medic> medicsPage = medicRepository.findAll(PageRequest.of(page, size));

        List<MedicDTO> medicDTOs = medicsPage.getContent().stream()
                .map(patient -> mapper.convertValue(patient, MedicDTO.class))
                .collect(Collectors.toList());

        return new PageDTO<>(
                medicDTOs,
                medicsPage.getTotalPages(),
                medicsPage.getTotalElements(),
                medicsPage.getNumber(),
                medicsPage.getSize()
        );
    }


    @Override
    public MedicDTO findMedicById(Long id) {
        Medic medic = medicRepository.findById(id).get();
        MedicDTO medicDTO = null;
        if (medic != null) {
            medicDTO = mapper.convertValue(medic, MedicDTO.class);
        }
        return medicDTO;
    }

    @Override
    public void saveMedic(MedicDTO newMedicDTO) {
        saveMethod(newMedicDTO);
    }

    @Override
    public void deleteMedic(Long id) {
        Medic medic = medicRepository.findById(id).get();
        medicRepository.deleteById(id);


    }

    @Override
    public void updateMedic(MedicDTO newMedicDTO) {
        saveMethod(newMedicDTO);
    }

    @Override
    public List<MedicDTO> searchMedics(String name, String lastName, String registrationNumber) {
        List<Medic> medics = medicRepository.findByNameContainingOrLastNameContainingOrRegistrationNumberContaining(name, lastName, registrationNumber);

        // Mapear la lista de entidades Patient a una lista de DTOs PatientDTO
        List<MedicDTO> medicDTOs = medics.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());

        return medicDTOs;
    }

    @Override
    public Long findMedicIdByUserName(String userName) {
        Medic medic = medicRepository.findByUserName(userName);
        if (medic != null) {
            return medic.getId();
        } else {
            throw new EntityNotFoundException("Medic not found with username: " + userName);
        }
    }

    @Override
    public MedicDTO convertEntityToDto(Medic medic) {
        MedicDTO medicDTO = new MedicDTO();
        medicDTO.setId(medic.getId());
        medicDTO.setName(medic.getName());
        medicDTO.setLastName(medic.getLastName());
        medicDTO.setEmail(medic.getEmail());
        medicDTO.setPassword(medic.getPassword());
        medicDTO.setRegistrationNumber(medic.getRegistrationNumber());
        medicDTO.setSpeciality(medic.getSpeciality());


        return medicDTO;
    }
}
