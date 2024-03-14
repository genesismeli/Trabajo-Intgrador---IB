package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.MedicDTO;
import com.dh.apiClinic.DTO.PageDTO;
import com.dh.apiClinic.security.entity.Rol;
import com.dh.apiClinic.security.entity.User;
import com.dh.apiClinic.security.enums.NameRol;
import com.dh.apiClinic.security.jwt.JwtProvider;
import com.dh.apiClinic.security.service.RolService;
import com.dh.apiClinic.security.service.UserService;
import com.dh.apiClinic.service.IMedicService;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;



@Tag(name = "Medic", description = "Operations about medic")
@RequestMapping("/medic")
@RestController
public class MedicController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IMedicService iMedicService;

    @Operation(summary = "Find all medics")
    @GetMapping("/all")
    public ResponseEntity<PageDTO<MedicDTO>> getAllMedics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        PageDTO<MedicDTO> medicsPage = iMedicService.findAllMedics(page, size);

        return ResponseEntity.ok(medicsPage);
    }

    @Operation(summary = "Find medic by id",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getMedic(@PathVariable Long id) {
        MedicDTO medicDTO = iMedicService.findMedicById(id);
        return ResponseEntity.ok(medicDTO);
    }

    @Operation(summary = "Add new medic",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<MedicDTO>> saveMedic(@RequestBody MedicDTO medicDTO) {
        User user =
                new User(medicDTO.getName(), medicDTO.getUserName(), medicDTO.getEmail(),
                        passwordEncoder.encode(medicDTO.getPassword()));
        Set<Rol> rols = new HashSet<>();
        rols.add(rolService.getByNameRol(NameRol.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found"))); // Agregar el rol de administrador

        user.setRoles(rols);
        userService.save(user);
        iMedicService.saveMedic(medicDTO);
        ApiResponse<MedicDTO> response = new ApiResponse<>("Medic created successfully!!", medicDTO);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Find medic by username",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/username/{username}")
    public ResponseEntity<Long> getMedicIdByUsername(@PathVariable String username) {
        Long patientId = iMedicService.findMedicIdByUserName(username);
        return new ResponseEntity<>(patientId, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing medic",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateMedic(@RequestBody MedicDTO medicDTO) {
        ResponseEntity<String> response;
        if (iMedicService.findMedicById(medicDTO.getId()) != null) {
            iMedicService.updateMedic(medicDTO);
            response = ResponseEntity.ok("Update medic");
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @Operation(summary = "Update for id an existing medic",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")  // Agrega el parámetro {id} en la URL
    public ResponseEntity<?> updateMedic(@PathVariable Long id, @RequestBody MedicDTO medicDTO) {
        ResponseEntity<String> response;
        MedicDTO existingMedic = iMedicService.findMedicById(id);

        if (existingMedic != null) {
            // Actualiza los datos del médico con los valores de medicDTO
            medicDTO.setId(id); // Asegúrate de establecer el ID del DTO con el ID de la URL
            iMedicService.updateMedic(medicDTO);
            response = ResponseEntity.ok("Medic updated");
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @Operation(summary = "Delete a existing medic",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMedic(@PathVariable Long id) {
        ResponseEntity<String> response;
        if (iMedicService.findMedicById(id) != null) {
            iMedicService.deleteMedic(id);
            response = ResponseEntity.ok("Deleted medic with id: " + id);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/search")
    public List<MedicDTO> searchMedics(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String registrationNumber) {
        return iMedicService.searchMedics(name, lastName, registrationNumber);
    }
}
