package com.dh.apiClinic.repository;

import com.dh.apiClinic.entity.CodeCie10;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICodeCie10Repository extends JpaRepository<CodeCie10, Long> {

    List<CodeCie10> findAll();
}
