package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.FamilyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFamilyHistoryRepository extends JpaRepository<FamilyHistory, Long> {

}
