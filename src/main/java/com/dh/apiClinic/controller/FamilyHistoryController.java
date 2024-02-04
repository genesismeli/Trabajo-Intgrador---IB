package com.dh.apiClinic.controller;


import com.dh.apiClinic.DTO.FamilyHistoryDTO;
import com.dh.apiClinic.service.IFamilyHistoryService;
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

@Tag(name = "FamilyHistory", description = "Operaciones con Antecedentes Familiares")
@RestController
@RequestMapping("/family-history")
public class FamilyHistoryController {

    @Autowired
    private IFamilyHistoryService familyHistoryService;

    @Operation(summary = "Find all FamilyHistory")
    @GetMapping("/all")
    public ResponseEntity<Collection<FamilyHistoryDTO>> getAllFamilyHistory() {
        Collection<FamilyHistoryDTO> familyHistorys = familyHistoryService.findAllFamilyHistory();
        return new ResponseEntity<>(familyHistorys, HttpStatus.OK);
    }

    @Operation(summary = "Find familyHistory by id")
    @GetMapping("/{id}")
    public ResponseEntity<FamilyHistoryDTO> getPhysicalExamById(@PathVariable Long id) {
        FamilyHistoryDTO familyHistoryDTO = familyHistoryService.findFamilyHistoryById(id);
        if (familyHistoryDTO != null) {
            return new ResponseEntity<>(familyHistoryDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add new familyHistory")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<FamilyHistoryDTO>> addFamilyHistory(@RequestBody FamilyHistoryDTO familyHistoryDTO) {
        try {
            familyHistoryService.saveFamilyHistory(familyHistoryDTO);
            ApiResponse<FamilyHistoryDTO> response = new ApiResponse<>("FamilyHistory created successfully!!", familyHistoryDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ApiResponse<>("Error al crear FamilyHistory: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @Operation(summary = "Update an existing familyHistory",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePersonalHistory(@RequestBody FamilyHistoryDTO familyHistoryDTO) {
        familyHistoryService.updateFamilyHistory(familyHistoryDTO);
        return new ResponseEntity<>("Family History updated successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Update for id an existing FamilyHistory",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFamilyHistory(@PathVariable Long id, @RequestBody FamilyHistoryDTO familyHistoryDTO) {
        ResponseEntity<String> response;

        FamilyHistoryDTO existingFamilyHistory = familyHistoryService.findFamilyHistoryById(id);

        if (existingFamilyHistory != null) {
            // Actualiza los datos del examen físico con los valores de physicalExamDTO
            familyHistoryDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            familyHistoryService.updateFamilyHistory(familyHistoryDTO);
            response = new ResponseEntity<>("FamilyHistory updated successfully.", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Failed to update familyHistory, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "Delete familyHistory by id")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFamilyHistory(@PathVariable Long id) {
        familyHistoryService.deleteFamilyHistory(id);
        return new ResponseEntity<>("Family History deleted successfully.", HttpStatus.OK);
    }
}
