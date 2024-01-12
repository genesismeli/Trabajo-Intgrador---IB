package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.ClinicalRecord;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClinicalRecordRepository extends JpaRepository<ClinicalRecord, Long> {
    Page<ClinicalRecord> findByPatientId(Long patientId, Pageable pageable);
    }



