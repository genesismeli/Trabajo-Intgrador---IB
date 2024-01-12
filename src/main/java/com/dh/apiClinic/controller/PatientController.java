package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.DTO.PatientDTO;
import com.dh.apiClinic.service.IClinicalRecordService;
import com.dh.apiClinic.service.IPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;


@Tag(name = "Patients", description = "Operations about patients")
@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    IPatientService ipatientService;

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Operation(summary = "Find all patients")
    @GetMapping("/all")
    public ResponseEntity<PageDTO<PatientDTO>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        PageDTO<PatientDTO> patientsPage = ipatientService.findAllPatients(page, size);

        return ResponseEntity.ok(patientsPage);
    }


    @Operation(summary = "Find patient by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        PatientDTO patientDTO = ipatientService.findPatientById(id);
        return new ResponseEntity<>(patientDTO, HttpStatus.OK);
    }

    @Operation(summary = "Add new patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PatientDTO>> savePatient(@RequestBody PatientDTO patientDTO) {
        ipatientService.savePatient(patientDTO);
        ApiResponse<PatientDTO> response = new ApiResponse<>("Patient created successfully!!", patientDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody PatientDTO patientDTO) {
        ResponseEntity<String> response;
        if (ipatientService.findPatientById(patientDTO.getId()) != null) {
            ipatientService.updatePatient(patientDTO);
            response = new ResponseEntity<>("Update patient", HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>("Failed to update address, check sent values and id", HttpStatus.BAD_REQUEST);
        }
        return response;

    }

    @Operation(summary = "Update  for id an existing patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/update/{id}") // Agrega el parámetro {id} en la URL
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody PatientDTO patientDTO) {
        ResponseEntity<String> response;

        // Verifica si el paciente con el ID proporcionado existe
        PatientDTO existingPatient = ipatientService.findPatientById(id);

        if (existingPatient != null) {
            // Actualiza los datos del paciente con los valores de patientDTO
            patientDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            ipatientService.updatePatient(patientDTO);
            response = new ResponseEntity<>("Patient updated", HttpStatus.CREATED);
        } else {
            response = new ResponseEntity<>("Failed to update patient, check sent values and id", HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    @Operation(summary = "Delete a existing patient",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @PreAuthorize("hasRole('USER') ")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        ResponseEntity<String> response;
        if (ipatientService.findPatientById(id) != null) {
            ipatientService.deletePatient(id);
            response = new ResponseEntity<>("Deleted patient with id: " + id, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>("It is not find the patient with the id: " + id, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/search")
    public List<PatientDTO> searchPatients(
           @RequestParam(required = false) String name,
           @RequestParam(required = false) String lastName,
           @RequestParam(required = false) String dni) {
        return ipatientService.searchPatients(name, lastName, dni);
    }

}
