package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.PersonalHistoryDTO;
import com.dh.apiClinic.DTO.PhysicalExamDTO;
import com.dh.apiClinic.service.IPersonalHistoryService;
import com.dh.apiClinic.service.IPhysicalExamService;
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

@Tag(name = "PersonalHistory", description = "Operaciones con Antecedentes personales")
@RestController
@RequestMapping("/personal-history")
public class PersonalHistoryController {

    @Autowired
    private IPersonalHistoryService personalHistoryService;

    @Operation(summary = "Find all PersonalHistory")
    @GetMapping("/all")
    public ResponseEntity<Collection<PersonalHistoryDTO>> getAllPersonalHistory() {
        Collection<PersonalHistoryDTO> personalHistorys = personalHistoryService.findAllPersonalHistory();
        return new ResponseEntity<>(personalHistorys, HttpStatus.OK);
    }

    @Operation(summary = "Find personalHistory by id")
    @GetMapping("/{id}")
    public ResponseEntity<PersonalHistoryDTO> getPhysicalExamById(@PathVariable Long id) {
        PersonalHistoryDTO personalHistoryDTO = personalHistoryService.findPersonalHistoryById(id);
        if (personalHistoryDTO != null) {
            return new ResponseEntity<>(personalHistoryDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add new personalHistory")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PersonalHistoryDTO>> addPersonalHistory(@RequestBody PersonalHistoryDTO personalHistoryDTO) {
        try {
            personalHistoryService.savePersonalHistory(personalHistoryDTO);
            ApiResponse<PersonalHistoryDTO> response = new ApiResponse<>("PersonalHistory created successfully!!", personalHistoryDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Puedes imprimir el stack trace para obtener más detalles en la consola
            return new ResponseEntity<>(new ApiResponse<>("Error al crear PersonalHistory: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @Operation(summary = "Update an existing personalHistory",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<String> updatePersonalHistory(@RequestBody PersonalHistoryDTO personalHistoryDTO) {
        personalHistoryService.updatePersonalHistory(personalHistoryDTO);
        return new ResponseEntity<>("Personal History updated successfully.", HttpStatus.OK);
    }

    @Operation(summary = "Update for id an existing PersonalHistory",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}") // Agrega el parámetro {id} en la URL
    public ResponseEntity<String> updatePersonalHistory(@PathVariable Long id, @RequestBody PersonalHistoryDTO personalHistoryDTO) {
        ResponseEntity<String> response;

        // Verifica si el examen físico con el ID proporcionado existe
        PersonalHistoryDTO existingPersonalHistory = personalHistoryService.findPersonalHistoryById(id);

        if (existingPersonalHistory != null) {
            // Actualiza los datos del examen físico con los valores de physicalExamDTO
            personalHistoryDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            personalHistoryService.updatePersonalHistory(personalHistoryDTO);
            response = new ResponseEntity<>("PersonalHistory updated successfully.", HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("Failed to update personalHistory, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Operation(summary = "Delete personalHistory by id")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePersonalHistory(@PathVariable Long id) {
        personalHistoryService.deletePersonalHistory(id);
        return new ResponseEntity<>("Personal History deleted successfully.", HttpStatus.OK);
    }


}
