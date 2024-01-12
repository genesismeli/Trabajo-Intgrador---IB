package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.DiagnosisDTO;
import com.dh.apiClinic.entity.Diagnosis;
import com.dh.apiClinic.service.IDiagnosisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "Diagnosis", description = "Operaciones con Diagnosis")
@RestController
@RequestMapping("/diagnosis")

public class DiagnosisController {


    @Autowired
    private IDiagnosisService diagnosisService;

    @Operation(summary = "Find all Diagnosis")
    @GetMapping("/all")
    public ResponseEntity<Collection<DiagnosisDTO>> getAllDiagnoses() {
        Collection<DiagnosisDTO> diagnoses = diagnosisService.findAllDiagnosis();
        return new ResponseEntity<>(diagnoses, HttpStatus.OK);
    }


    @Operation(summary = "Find diagnosis by id",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisDTO> getDiagnosisById(@PathVariable Long id) {
        DiagnosisDTO diagnosisDTO = diagnosisService.findDiagnosisById(id);
        if (diagnosisDTO != null) {
            return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Add new diagnosis")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DiagnosisDTO>> addDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) {
        diagnosisService.saveDiagnosis(diagnosisDTO);
        ApiResponse<DiagnosisDTO> response = new ApiResponse<>("Diagnosis created successfully!!", diagnosisDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing diagnosis",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updateDiagnosis(@RequestBody DiagnosisDTO diagnosisDTO) {
        diagnosisService.updateDiagnosis(diagnosisDTO);
        return new ResponseEntity<>("Diagnosis updated successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Update for id an existing diagnosis",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}") // Agrega el parámetro {id} en la URL
    public ResponseEntity<String> updateDiagnosis(@PathVariable Long id, @RequestBody DiagnosisDTO diagnosisDTO) {
        ResponseEntity<String> response;

        // Verifica si el diagnóstico con el ID proporcionado existe
        DiagnosisDTO existingDiagnosis = diagnosisService.findDiagnosisById(id);

        if (existingDiagnosis != null) {
            // Actualiza los datos del diagnóstico con los valores de diagnosisDTO
            diagnosisDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            diagnosisService.updateDiagnosis(diagnosisDTO);
            response = new ResponseEntity<>("Diagnosis updated successfully.", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Failed to update diagnosis, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "Delete diagnosis by id",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return new ResponseEntity<>("Diagnosis deleted successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Busqueda diagnosis")
    @GetMapping("/search")
    public ResponseEntity<List<Diagnosis>> searchDiagnoses(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String description) {
        List<Diagnosis> diagnoses = diagnosisService.findByCodeOrDescription(code, description);
        return ResponseEntity.ok(diagnoses);
    }
}



