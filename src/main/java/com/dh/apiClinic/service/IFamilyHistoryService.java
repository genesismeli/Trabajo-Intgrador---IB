package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.FamilyHistoryDTO;
import com.dh.apiClinic.DTO.PersonalHistoryDTO;
import com.dh.apiClinic.entity.FamilyHistory;
import com.dh.apiClinic.entity.PersonalHistory;

import java.util.Collection;

public interface IFamilyHistoryService {

    Collection<FamilyHistoryDTO> findAllFamilyHistory();

    FamilyHistoryDTO findFamilyHistoryById(Long id);

    void saveFamilyHistory(FamilyHistoryDTO newFamilyHistoryDTO);

    void deleteFamilyHistory(Long id);

    void updateFamilyHistory(FamilyHistoryDTO newFamilyHistoryDTO);

    FamilyHistoryDTO convertEntityToDto(FamilyHistory familyHistory);

}
