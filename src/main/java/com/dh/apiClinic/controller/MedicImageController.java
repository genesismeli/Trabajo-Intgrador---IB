package com.dh.apiClinic.controller;

import com.dh.apiClinic.service.IMedicImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "MedicImag", description = "Operations about MedicImag")
@RequestMapping("/medic-images")
@RestController
public class MedicImageController {

    @Autowired
    private IMedicImageService medicImageService;

    @Operation(summary = "Obtener firma del medico")
    @GetMapping("/{medicId}")
    public ResponseEntity<byte[]> getMedicImage(@PathVariable Long medicId) {
        // Lógica para obtener la imagen del médico por su ID
        byte[] imageBytes = medicImageService.getMedicImageBytes(medicId);

        if (imageBytes != null) {
            // Si se encuentra la imagen, devolverla en la respuesta
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } else {
            // Si no se encuentra la imagen, devolver una respuesta de error 404
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Cargar firma del medico")
    @PostMapping("/{medicId}")
    public ResponseEntity<?> uploadImage(@PathVariable Long medicId, @RequestParam("file") MultipartFile file) throws IOException {
        medicImageService.uploadImage(medicId, file);
        return ResponseEntity.ok().build();
    }
}
