package com.dh.apiClinic.service.impl;


import com.dh.apiClinic.DTO.FamilyHistoryDTO;
import com.dh.apiClinic.DTO.PersonalHistoryDTO;
import com.dh.apiClinic.entity.FamilyHistory;
import com.dh.apiClinic.entity.PersonalHistory;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IFamilyHistoryRepository;
import com.dh.apiClinic.repository.IPersonalHistoryRepository;
import com.dh.apiClinic.service.IFamilyHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FamilyHistoryServiceImpl implements IFamilyHistoryService {

    @Autowired
    private IFamilyHistoryRepository familyHistoryRepository;

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(FamilyHistoryDTO familyHistoryDTO) {
        if (familyHistoryDTO != null) {
            FamilyHistory familyHistory = mapper.convertValue(familyHistoryDTO, FamilyHistory.class);
            familyHistoryRepository.save(familyHistory);
        } else {
            throw new ResourceNotFoundException("PersonalHistory", "id", "id not found: " + familyHistoryDTO.getId());
        }

    }


    @Override
    public Collection<FamilyHistoryDTO> findAllFamilyHistory() {
        List<FamilyHistory> familyHistorys = familyHistoryRepository.findAll();
        Set<FamilyHistoryDTO> familyHistoryDTO = new HashSet<>();
        for (FamilyHistory familyHistory : familyHistorys) {
            familyHistoryDTO.add(mapper.convertValue(familyHistorys, FamilyHistoryDTO.class));
        }
        return familyHistoryDTO;

    }


    @Override
    public FamilyHistoryDTO findFamilyHistoryById(Long id) {

        FamilyHistory familyHistory = familyHistoryRepository.findById(id).get();
        FamilyHistoryDTO familyHistoryDTO = null;
        if (familyHistory != null) {
            familyHistoryDTO = mapper.convertValue(familyHistory, FamilyHistoryDTO
                    .class);
        }
        return familyHistoryDTO;
    }

    public void saveFamilyHistory(FamilyHistoryDTO newFamilyHistoryDTO) {
        saveMethod(newFamilyHistoryDTO);
    }

    @Override
    public void deleteFamilyHistory(Long id) {
        FamilyHistory familyHistory = familyHistoryRepository.findById(id).get();
        familyHistoryRepository.deleteById(id);
    }

    @Override
    public void updateFamilyHistory(FamilyHistoryDTO newFamilyHistoryDTO) {
        saveMethod(newFamilyHistoryDTO);
    }

    @Override
    public FamilyHistoryDTO convertEntityToDto(FamilyHistory familyHistory) {
        if (familyHistory == null) {
            return null;
        }

        FamilyHistoryDTO familyHistoryDTO = new FamilyHistoryDTO();
        familyHistoryDTO.setId(familyHistory.getId());
        familyHistoryDTO.setCoronaryArteryDisease(familyHistory.getCoronaryArteryDisease());
        familyHistoryDTO.setMyocardialInfarction(familyHistory.getMyocardialInfarction());
        familyHistoryDTO.setHypertension(familyHistory.getHypertension());
        familyHistoryDTO.setAsthma(familyHistory.getAsthma());
        familyHistoryDTO.setCopd(familyHistory.getCopd());
        familyHistoryDTO.setTuberculosis(familyHistory.getTuberculosis());
        familyHistoryDTO.setDiabetes(familyHistory.getDiabetes());
        familyHistoryDTO.setObesity(familyHistory.getObesity());
        familyHistoryDTO.setMetabolicSyndrome(familyHistory.getMetabolicSyndrome());
        familyHistoryDTO.setAnemia(familyHistory.getAnemia());
        familyHistoryDTO.setHemophilia(familyHistory.getHemophilia());
        familyHistoryDTO.setStroke(familyHistory.getStroke());
        familyHistoryDTO.setAlzheimer(familyHistory.getAlzheimer());
        familyHistoryDTO.setMultipleSclerosis(familyHistory.getMultipleSclerosis());
        familyHistoryDTO.setHemochromatosis(familyHistory.getHemochromatosis());
        familyHistoryDTO.setCysticFibrosis(familyHistory.getCysticFibrosis());
        familyHistoryDTO.setDepression(familyHistory.getDepression());
        familyHistoryDTO.setSchizophrenia(familyHistory.getSchizophrenia());
        familyHistoryDTO.setRheumatoidArthritis(familyHistory.getRheumatoidArthritis());
        familyHistoryDTO.setLupus(familyHistory.getLupus());
        familyHistoryDTO.setCeliacDisease(familyHistory.getCeliacDisease());
        familyHistoryDTO.setChronicKidneyDisease(familyHistory.getChronicKidneyDisease());
        familyHistoryDTO.setDiabeticNephropathy(familyHistory.getDiabeticNephropathy());
        familyHistoryDTO.setBreastCancer(familyHistory.getBreastCancer());
        familyHistoryDTO.setColonCancer(familyHistory.getColonCancer());
        familyHistoryDTO.setLungCancer(familyHistory.getLungCancer());
        familyHistoryDTO.setInflammatoryBowelDisease(familyHistory.getInflammatoryBowelDisease());
        familyHistoryDTO.setHypothyroidism(familyHistory.getHypothyroidism());
        familyHistoryDTO.setOtherMedicalHistory(familyHistory.getOtherMedicalHistory());

        return familyHistoryDTO;
    }


}

