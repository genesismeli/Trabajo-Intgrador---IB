package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.PersonalHistoryDTO;
import com.dh.apiClinic.DTO.PhysicalExamDTO;
import com.dh.apiClinic.entity.PersonalHistory;
import com.dh.apiClinic.entity.PhysicalExam;

import java.util.Collection;

public interface IPersonalHistoryService {

    Collection<PersonalHistoryDTO> findAllPersonalHistory();

    PersonalHistoryDTO findPersonalHistoryById(Long id);

    void savePersonalHistory(PersonalHistoryDTO newPersonalHistoryDTO);

    void deletePersonalHistory(Long id);

    void updatePersonalHistory(PersonalHistoryDTO newPersonalHistoryDTO);

    PersonalHistoryDTO convertEntityToDto(PersonalHistory personalHistory);

}
