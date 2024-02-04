package com.dh.apiClinic.repository;
import com.dh.apiClinic.entity.PersonalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonalHistoryRepository extends JpaRepository<PersonalHistory, Long> {
}