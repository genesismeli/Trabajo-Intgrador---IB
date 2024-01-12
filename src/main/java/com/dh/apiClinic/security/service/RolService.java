package com.dh.apiClinic.security.service;

import com.dh.apiClinic.security.entity.Rol;
import com.dh.apiClinic.security.enums.NameRol;
import com.dh.apiClinic.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> getByNameRol(NameRol nameRol){
        return rolRepository.findByNameRol(nameRol);
    }

    public void save(Rol rol) {
        Optional<Rol> existingRol = rolRepository.findByNameRol(rol.getNameRol());
        if (existingRol.isEmpty()) {
            rolRepository.save(rol);
        }
    }


    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }


}
