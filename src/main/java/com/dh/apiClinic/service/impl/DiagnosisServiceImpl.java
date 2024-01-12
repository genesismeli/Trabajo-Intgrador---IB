package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.DTO.DiagnosisDTO;
import com.dh.apiClinic.entity.Diagnosis;
import com.dh.apiClinic.exception.ResourceNotFoundException;
import com.dh.apiClinic.repository.IClinicalRecordRepository;
import com.dh.apiClinic.repository.IDiagnosisRepository;
import com.dh.apiClinic.repository.IPatientRepository;
import com.dh.apiClinic.service.IDiagnosisService;
import com.dh.apiClinic.service.IPatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DiagnosisServiceImpl implements IDiagnosisService {

    @Autowired
    private IDiagnosisRepository diagnosisRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IClinicalRecordRepository clinicalRecordRepository;

    @Autowired
    IPatientService ipatientService;

    @Autowired
    public DiagnosisServiceImpl(IDiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Autowired
    ObjectMapper mapper;

    public void saveMethod(DiagnosisDTO diagnosisDTO) {
        if (diagnosisDTO != null) {
            Diagnosis diagnosis = mapper.convertValue(diagnosisDTO, Diagnosis.class);
            diagnosisRepository.save(diagnosis);
        } else {
            throw new ResourceNotFoundException("Diagnosis", "id", "id not found: " + diagnosisDTO.getId());
        }

    }

    @Override
    public List<Diagnosis> findByCodeOrDescription(String code, String description) {
        return diagnosisRepository.findByCodeCie10CodeOrCodeCie10Description(code, description);
    }

    @Override
    public Collection<DiagnosisDTO> findAllDiagnosis() {
        List<Diagnosis> diagnosis = diagnosisRepository.findAll();
        Set<DiagnosisDTO> diagnosisDTO = new HashSet<>();

        for (Diagnosis diag : diagnosis) {
            diagnosisDTO.add(mapper.convertValue(diag, DiagnosisDTO.class));
        }
        return diagnosisDTO;

    }

    @Override
    public DiagnosisDTO findDiagnosisById(Long id) {
        Diagnosis diag = diagnosisRepository.findById(id).get();
        DiagnosisDTO diagnosisDTO = null;
        if (diag != null) {
            diagnosisDTO = mapper.convertValue(diag, DiagnosisDTO.class);
        }
        return diagnosisDTO;
    }
    @Override
    public void saveDiagnosis(DiagnosisDTO newDiagnosisDTO) {
       saveMethod(newDiagnosisDTO);
    }




    @Override
    public void deleteDiagnosis(Long id) {
        Diagnosis diagnosis = diagnosisRepository.findById(id).get();
        diagnosisRepository.deleteById(id);
    }


    @Override
    public void updateDiagnosis(DiagnosisDTO newDiagnosisDTO) {
        saveMethod(newDiagnosisDTO);
    }

    @Override
    public DiagnosisDTO convertEntityToDto(Diagnosis diagnosis) {
        if (diagnosis == null) {
            return null;
        }

        DiagnosisDTO diagnosisDTO = new DiagnosisDTO();
        diagnosisDTO.setId(diagnosis.getId());
        diagnosisDTO.setCodeCie10(diagnosis.getCodeCie10());





        return diagnosisDTO;
    }


}


