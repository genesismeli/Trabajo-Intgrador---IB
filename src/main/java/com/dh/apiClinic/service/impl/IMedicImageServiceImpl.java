package com.dh.apiClinic.service.impl;

import com.dh.apiClinic.entity.ImagMedic;
import com.dh.apiClinic.entity.Medic;
import com.dh.apiClinic.repository.IMedicRepository;
import com.dh.apiClinic.repository.IMedicImageRepository;
import com.dh.apiClinic.service.IMedicImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
public class IMedicImageServiceImpl implements IMedicImageService {

    @Autowired
    private IMedicImageRepository IMedicImageRepository;

    @Autowired
    private IMedicRepository medicRepository;

    @Override
    public void uploadImage(Long medicId, MultipartFile file) throws IOException {
        Medic medic = medicRepository.findById(medicId)
                .orElseThrow(() -> new EntityNotFoundException("Medic not found with id: " + medicId));

        ImagMedic medicImage = new ImagMedic();
        medicImage.setMedic(medic);
        medicImage.setImageData(file.getBytes());

        IMedicImageRepository.save(medicImage);
    }

    @Override
    public byte[] getMedicImageBytes(Long medicId) {
        // Buscar la imagen del médico en la base de datos por su ID
        ImagMedic imagMedic = IMedicImageRepository.findByMedicId(medicId);
        if (imagMedic != null && imagMedic.getImageData() != null) {
            // Si se encuentra la imagen, devolver los bytes de la imagen
            return imagMedic.getImageData();
        } else {
            // Si no se encuentra la imagen, devolver null
            return null;
        }
    }
}
