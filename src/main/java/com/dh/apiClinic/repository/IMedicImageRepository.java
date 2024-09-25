package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.ImagMedic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicImageRepository extends JpaRepository<ImagMedic, Long> {
    ImagMedic findByMedicId(Long medicId);
}
