package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.DTO.PersonalHistoryDTO;
import com.dh.apiClinic.entity.PersonalHistory;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IPersonalHistoryRepository;
import com.dh.apiClinic.service.IPersonalHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersonalHistoryServiceImpl implements IPersonalHistoryService {

    @Autowired
    private IPersonalHistoryRepository personalHistoryRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(PersonalHistoryDTO personalHistoryDTO) {
        if (personalHistoryDTO != null) {
            PersonalHistory personalHistory = mapper.convertValue(personalHistoryDTO, PersonalHistory.class);
            personalHistoryRepository.save(personalHistory);
        } else {
            throw new ResourceNotFoundException("PersonalHistory", "id", "id not found: " + personalHistoryDTO.getId());
        }

    }


    @Override
    public Collection<PersonalHistoryDTO> findAllPersonalHistory() {
        List<PersonalHistory> personalHistorys = personalHistoryRepository.findAll();
        Set<PersonalHistoryDTO> personalHistoryDTO = new HashSet<>();
        for (PersonalHistory personalHistory : personalHistorys) {
            personalHistoryDTO.add(mapper.convertValue(personalHistorys, PersonalHistoryDTO.class));
        }
        return personalHistoryDTO;

    }


    @Override
    public PersonalHistoryDTO findPersonalHistoryById(Long id) {

        PersonalHistory personalHistory = personalHistoryRepository.findById(id).get();
        PersonalHistoryDTO personalHistoryDTO = null;
        if (personalHistory != null) {
            personalHistoryDTO = mapper.convertValue(personalHistory, PersonalHistoryDTO
                    .class);
        }
        return personalHistoryDTO;
    }

    public void savePersonalHistory(PersonalHistoryDTO newPersonalHistoryDTO) {
        saveMethod(newPersonalHistoryDTO);
    }

    @Override
    public void deletePersonalHistory(Long id) {
        PersonalHistory personalHistory = personalHistoryRepository.findById(id).get();
        personalHistoryRepository.deleteById(id);
    }

    @Override
    public void updatePersonalHistory(PersonalHistoryDTO newPersonalHistoryDTO) {
        saveMethod(newPersonalHistoryDTO);
    }

    @Override
    public PersonalHistoryDTO convertEntityToDto(PersonalHistory personalHistory) {
        if (personalHistory == null) {
            return null;
        }

        PersonalHistoryDTO personalHistoryDTO = new PersonalHistoryDTO();
        personalHistoryDTO.setId(personalHistory.getId());
        personalHistoryDTO.setDiabetes(personalHistory.getDiabetes());
        personalHistoryDTO.setHypertension(personalHistory.getHypertension());
        personalHistoryDTO.setCoronaryArteryDisease(personalHistory.getCoronaryArteryDisease());
        personalHistoryDTO.setBronchialAsthma(personalHistory.getBronchialAsthma());
        personalHistoryDTO.setBronchopulmonaryDisease(personalHistory.getBronchopulmonaryDisease());
        personalHistoryDTO.setPsychopathy(personalHistory.getPsychopathy());
        personalHistoryDTO.setAllergies(personalHistory.getAllergies());
        personalHistoryDTO.setTuberculosis(personalHistory.getTuberculosis());
        personalHistoryDTO.setSexuallyTransmittedInfection(personalHistory.getSexuallyTransmittedInfection());
        personalHistoryDTO.setGout(personalHistory.getGout());
        personalHistoryDTO.setEndocrineDisorders((personalHistory.getEndocrineDisorders()));
        personalHistoryDTO.setNephropathies(personalHistory.getNephropathies());
        personalHistoryDTO.setUropathies(personalHistory.getUropathies());
        personalHistoryDTO.setHematopathies(personalHistory.getHematopathies());
        personalHistoryDTO.setHepatitis(personalHistory.getHepatitis());
        personalHistoryDTO.setFever(personalHistory.getFever());
        personalHistoryDTO.setUlcer(personalHistory.getUlcer());
        personalHistoryDTO.setOtherMedicalHistory(personalHistory.getOtherMedicalHistory());
        personalHistoryDTO.setCancer(personalHistory.getCancer());

        return personalHistoryDTO;
    }


}



