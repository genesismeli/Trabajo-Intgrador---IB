package com.dh.apiClinic.controller;


import com.dh.apiClinic.DTO.PhysicalExamDTO;
import com.dh.apiClinic.service.IPhysicalExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "PhysicalExam", description = "Operaciones con Examen Fisico")
@RestController
@RequestMapping("/physical-exams")
public class PhysicalExamController {

    @Autowired
    private IPhysicalExamService physicalExamService;

    @Operation(summary = "Find all PhysicalExam")
    @GetMapping("/all")
    public ResponseEntity<Collection<PhysicalExamDTO>> getAllPhysicalExams() {
        Collection<PhysicalExamDTO> physicalExams = physicalExamService.findAllPhysicalExam();
        return new ResponseEntity<>(physicalExams, HttpStatus.OK);
    }

    @Operation(summary = "Find physicalExam by id")
    @GetMapping("/{id}")
    public ResponseEntity<PhysicalExamDTO> getPhysicalExamById(@PathVariable Long id) {
        PhysicalExamDTO physicalExamDTO = physicalExamService.findPhysicalExamById(id);
        if (physicalExamDTO != null) {
            return new ResponseEntity<>(physicalExamDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add new physicalExam")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PhysicalExamDTO>> addPhysicalExam(@RequestBody PhysicalExamDTO physicalExamDTO) {
        physicalExamService.savePhysicalExam(physicalExamDTO);
        ApiResponse<PhysicalExamDTO> response = new ApiResponse<>("PhysicalExam created successfully!!", physicalExamDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing physicalExam",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePhysicalExam(@RequestBody PhysicalExamDTO physicalExamDTO) {
        physicalExamService.updatePhysicalExam(physicalExamDTO);
        return new ResponseEntity<>("Physical exam updated successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Update for id an existing physicalExam",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}") // Agrega el parámetro {id} en la URL
    public ResponseEntity<String> updatePhysicalExam(@PathVariable Long id, @RequestBody PhysicalExamDTO physicalExamDTO) {
        ResponseEntity<String> response;

        // Verifica si el examen físico con el ID proporcionado existe
        PhysicalExamDTO existingPhysicalExam = physicalExamService.findPhysicalExamById(id);

        if (existingPhysicalExam != null) {
            // Actualiza los datos del examen físico con los valores de physicalExamDTO
            physicalExamDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            physicalExamService.updatePhysicalExam(physicalExamDTO);
            response = new ResponseEntity<>("Physical exam updated successfully.", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Failed to update physical exam, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "Delete physicalExam by id")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePhysicalExam(@PathVariable Long id) {
        physicalExamService.deletePhysicalExam(id);
        return new ResponseEntity<>("Physical exam deleted successfully.", HttpStatus.OK);
    }


}

