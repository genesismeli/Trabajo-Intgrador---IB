package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.enums.Speciality;
import com.dh.apiClinic.service.IClinicalRecordService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pdf", description = "Metodo para pdf")
@RestController
@RequestMapping("/pdf")
public class PdfMedicationsController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfMedicationsController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Find all clinical record for medications")
    @GetMapping("/clinical-record/medications/{recordId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long recordId) {
        // Lógica para obtener los datos de la ficha clínica con el ID proporcionado
        // Puedes utilizar tu servicio de fichas clínicas para obtener los datos necesarios
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);

        // Obtener el contenido de la ficha clínica
        if (clinicalRecord != null && clinicalRecord.getMedications() != null && !clinicalRecord.getMedications().isEmpty()){
             String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);

            // Verificar si el contenido es el mensaje de falta de diagnósticos
            if (!clinicalRecordContent.equals("No hay datos de diagnóstico disponibles")) {
                byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "diagnosis.pdf");

                return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            }
        }
        // Enviar una respuesta con un mensaje de error en formato de array de bytes
        String errorMessage = "No hay datos de diagnóstico disponibles";
        return new ResponseEntity<>(errorMessage.getBytes(), HttpStatus.NOT_FOUND);

    }


    private String generateClinicalRecordContent(ClinicalRecordDTO clinicalRecord) {
        StringBuilder contentBuilder = new StringBuilder();
        if (clinicalRecord != null && clinicalRecord.getMedications() != null && !clinicalRecord.getMedications().isEmpty()) {
            boolean hasValidMedication = false;

            // Sección de Medicamentos
            for (MedicationDTO medication : clinicalRecord.getMedications()) {
                if (isValidMedication(medication)) {
                    hasValidMedication = true;
                    break;  // Rompe el bucle al encontrar un diagnóstico válido
                }
            }
            if (!hasValidMedication) {
                return "No hay datos de diagnóstico disponibles";
            }
            contentBuilder.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(clinicalRecord.getDate())).append("\n");
            contentBuilder.append("_________________________Datos del Paciente______________________________________\n");
            contentBuilder.append("Nombre del Paciente: ").append(clinicalRecord.getPatient().getName()).append("\n");
            contentBuilder.append("Apellido del Paciente: ").append(clinicalRecord.getPatient().getLastName()).append("\n");
            contentBuilder.append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(clinicalRecord.getPatient().getBirthdate())).append("\n");
            contentBuilder.append("Email: ").append(clinicalRecord.getPatient().getEmail()).append("\n\n");
            contentBuilder.append("_________________________Datos del Profesional____________________________________\n");
            contentBuilder.append("Nombre del Profesional: ").append(clinicalRecord.getMedic().getName()).append("\n");
            contentBuilder.append("Apellido del Profesional: ").append(clinicalRecord.getMedic().getLastName()).append("\n");
            contentBuilder.append("Especialidad: ").append(getSpecialityValue(clinicalRecord.getMedic().getSpeciality())).append("\n");
            contentBuilder.append("Email: ").append(clinicalRecord.getMedic().getEmail()).append("\n\n");

            // Sección de Medicamentos
            for (MedicationDTO medication : clinicalRecord.getMedications()) {
                appendMedicationData(contentBuilder, medication);
            }

            return contentBuilder.toString();
        } else {
            return "No hay datos de diagnóstico disponibles";
        }
    }

    private String getSpecialityValue(Speciality speciality) {
        return speciality != null ? speciality.getValue() : "";
    }

    // Función para verificar si un diagnóstico tiene datos válidos
    private boolean isValidMedication(MedicationDTO medication) {
        return medication.getVademecum() != null;
    }



    private void appendMedicationData(StringBuilder contentBuilder, MedicationDTO medication) {
        if (medication.getVademecum() != null) {
            contentBuilder.append("_______________________________Medicamentos____________________________________\n");
            contentBuilder.append("Concentración: ").append(medication.getVademecum().getConcentracion()).append("\n");
            contentBuilder.append("Nombre Comercial: ").append(medication.getVademecum().getNombre_comercial()).append("\n");
            contentBuilder.append("Presentación: ").append(medication.getVademecum().getPresentacion()).append("\n");
            contentBuilder.append("Forma Farmaceútica: ").append(medication.getVademecum().getForma_farmaceutica()).append("\n\n");
        }
    }

    private byte[] generatePdfBytes(String content) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph(content));
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }
    @Operation(summary = "recordId Pdf",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PATIENT') ")
    @GetMapping("/clinical-record/medications/{recordId}/pdf")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "medications.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
