package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.service.IMedicationService;
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

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "Medication", description = "Operaciones con Medication")
@RestController
@RequestMapping("/medication")
public class MedicationController {

    @Autowired
    private IMedicationService medicationService;

    @Operation(summary = "Find all Medication")
    @GetMapping("/all")
    public ResponseEntity<Collection<MedicationDTO>> getAllMedications() {
        Collection<MedicationDTO> medications = medicationService.findAllMedication();
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @Operation(summary = "Find medication by id",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<MedicationDTO> getMedicationById(@PathVariable Long id) {
        MedicationDTO medicationDTO = medicationService.findMedicationById(id);
        if (medicationDTO != null) {
            return new ResponseEntity<>(medicationDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add new medication")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<MedicationDTO>> addMedication(@RequestBody MedicationDTO medicationDTO) {
        medicationService.saveMedication(medicationDTO);
        ApiResponse<MedicationDTO> response = new ApiResponse<>("Medication created successfully!!", medicationDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing medication",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updateMedication(@RequestBody MedicationDTO medicationDTO) {
        medicationService.updateMedication(medicationDTO);
        return new ResponseEntity<>("Medication updated successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Update for id an existing medication",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}") // Agrega el parámetro {id} en la URL
    public ResponseEntity<String> updateMedicationById(@PathVariable Long id, @RequestBody MedicationDTO medicationDTO) {
        ResponseEntity<String> response;

        // Verifica si la medicación con el ID proporcionado existe
        MedicationDTO existingMedication = medicationService.findMedicationById(id);

        if (existingMedication != null) {
            // Actualiza los datos de la medicación con los valores de medicationDTO
            medicationDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            medicationService.updateMedication(medicationDTO);
            response = new ResponseEntity<>("Medication updated successfully.", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Failed to update medication, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "Delete medication by id",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return new ResponseEntity<>("Medication deleted successfully.", HttpStatus.OK);
    }
}
