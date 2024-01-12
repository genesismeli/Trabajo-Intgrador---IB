package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMedicationRepository extends JpaRepository<Medication, Long> {



}
