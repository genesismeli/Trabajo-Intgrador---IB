package com.dh.apiClinic.service;

import com.dh.apiClinic.DTO.PhysicalExamDTO;
import com.dh.apiClinic.entity.PhysicalExam;

import java.util.Collection;


public interface IPhysicalExamService {

        Collection<PhysicalExamDTO> findAllPhysicalExam();

        PhysicalExamDTO findPhysicalExamById(Long id);

        void savePhysicalExam(PhysicalExamDTO newPhysicalExamDTO);

        void deletePhysicalExam(Long id);

        void updatePhysicalExam(PhysicalExamDTO newPhysicalExamDTO);

        PhysicalExamDTO convertEntityToDto(PhysicalExam physicalExam);

}




