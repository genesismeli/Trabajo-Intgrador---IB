package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.PhysicalExamDTO;
import com.dh.apiClinic.enums.Speciality;
import com.dh.apiClinic.service.IClinicalRecordService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;


import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pdf", description = "Metodos para pdf")
@RestController
@RequestMapping("/pdf")
public class PdfExamController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfExamController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Find all clinical record for Signs Vitals")
    @GetMapping("/clinical-record/vital-signs/{recordId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long recordId) {
        // Lógica para obtener los datos de la ficha clínica con el ID proporcionado
        // Puedes utilizar tu servicio de fichas clínicas para obtener los datos necesarios
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        if (clinicalRecord != null && clinicalRecord.getPhysicalExams() != null && !clinicalRecord.getPhysicalExams().isEmpty()) {
            // Obtener el contenido de la ficha clínica
            String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);

            byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Signos_vitales.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        }

        // Enviar una respuesta con un mensaje de error en formato de array de bytes
        String errorMessage = "No hay datos de Signos Vitales disponibles";
        return new ResponseEntity<>(errorMessage.getBytes(), HttpStatus.NOT_FOUND);

    }

    private String generateClinicalRecordContent(ClinicalRecordDTO clinicalRecord) {
        StringBuilder contentBuilder = new StringBuilder();
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
        // Sección de Signos Vitales
        for (PhysicalExamDTO physicalExam : clinicalRecord.getPhysicalExams()) {
            appendPhysicalExamData(contentBuilder, physicalExam);
        }
        return contentBuilder.toString();
    }

    private String getSpecialityValue(Speciality speciality) {
        return speciality != null ? speciality.getValue() : "";
    }

    private void appendPhysicalExamData(StringBuilder contentBuilder, PhysicalExamDTO exam) {
        if (exam.getHeartRate() != null || exam.getOxygenSaturation() != null || exam.getRespiratoryRate() != null
                || exam.getSystolicPressure() != null || exam.getDiastolicPressure() != null || exam.getBeatsPerMinute() != null ||
                exam.getGlucose() != null) {
            contentBuilder.append("______________________________Signos Vitales____________________________________\n");
            if (exam.getHeartRate() != null) {
                contentBuilder.append("F. Cardíaca: ").append(exam.getHeartRate()).append("\n");
            }
            if (exam.getOxygenSaturation() != null) {
                contentBuilder.append("Saturación de Oxígeno: ").append(exam.getOxygenSaturation()).append("\n");
            }
            if (exam.getRespiratoryRate() != null) {
                contentBuilder.append("F. Respiratoria: ").append(exam.getRespiratoryRate()).append("\n");
            }
            if (exam.getSystolicPressure() != null) {
                contentBuilder.append("P. Sistólica: ").append(exam.getSystolicPressure()).append("\n");
            }
            if (exam.getDiastolicPressure() != null) {
                contentBuilder.append("P. Diastólica: ").append(exam.getDiastolicPressure()).append("\n");
            }
            if (exam.getBeatsPerMinute() != null) {
                contentBuilder.append("LPM: ").append(exam.getBeatsPerMinute()).append("\n");
            }
            if (exam.getGlucose() != null) {
                contentBuilder.append("Glucosa: ").append(exam.getGlucose()).append("\n");
            }
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
    @Operation(summary = "recordId Pdf for signs vitals",
            parameters = @Parameter(name = "Authorization", in = HEADER, description = "Json web token required", required = true),
            security = @SecurityRequirement(name = "jwtAuth"))
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PATIENT') ")
    @GetMapping("/clinical-record/vital-signs/{recordId}/pdf")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Examen_signos_vitales.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}

