package com.dh.apiClinic.security.repository;

import com.dh.apiClinic.security.entity.Rol;
import com.dh.apiClinic.security.enums.NameRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNameRol(NameRol nameRol);
}
