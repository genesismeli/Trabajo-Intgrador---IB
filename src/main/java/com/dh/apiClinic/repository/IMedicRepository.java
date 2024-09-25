package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.Medic;
import com.dh.apiClinic.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMedicRepository extends JpaRepository<Medic, Long> {
    Page<Medic> findAll(Pageable pageable);
    Medic findByUserName(String userName);
    List<Medic> findByNameContainingOrLastNameContainingOrRegistrationNumberContaining(String name, String lastName, String registrationNumber);
}
