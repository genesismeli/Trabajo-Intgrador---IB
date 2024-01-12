package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.DiagnosisDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pdf", description = "Metodo para pdf")
@RestController
@RequestMapping("/pdf")
public class PdfDiagnosisController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfDiagnosisController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Find all clinical record for diagnosis")
    @GetMapping("/clinical-record/diagnosis/{recordId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long recordId) {
        // Lógica para obtener los datos de la ficha clínica con el ID proporcionado
        // Puedes utilizar tu servicio de fichas clínicas para obtener los datos necesarios
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);

        // Verificar si hay diagnósticos disponibles
        if (clinicalRecord != null && clinicalRecord.getDiagnoses() != null && !clinicalRecord.getDiagnoses().isEmpty()) {
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
        if (clinicalRecord != null && clinicalRecord.getDiagnoses() != null && !clinicalRecord.getDiagnoses().isEmpty()) {
            boolean hasValidDiagnosis = false;

            // Sección de Diagnósticos
            for (DiagnosisDTO diagnosis : clinicalRecord.getDiagnoses()) {
                if (isValidDiagnosis(diagnosis)) {
                    hasValidDiagnosis = true;
                    break;  // Rompe el bucle al encontrar un diagnóstico válido
                }
            }
            if (!hasValidDiagnosis) {
                return "No hay datos de diagnóstico disponibles";
            }

            // Sección de Información General
            contentBuilder.append("Información General:\n");
            contentBuilder.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(clinicalRecord.getDate())).append("\n");
            contentBuilder.append("Nombre del Paciente: ").append(clinicalRecord.getPatient().getName()).append("\n");
            contentBuilder.append("Apellido del Paciente: ").append(clinicalRecord.getPatient().getLastName()).append("\n");
            contentBuilder.append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(clinicalRecord.getPatient().getBirthdate())).append("\n");
            contentBuilder.append("Email: ").append(clinicalRecord.getPatient().getEmail()).append("\n\n");


            for (DiagnosisDTO diagnosis : clinicalRecord.getDiagnoses()) {
                appendDiagnosisData(contentBuilder, diagnosis);
            }

            return contentBuilder.toString();
        } else {
            return "No hay datos de diagnóstico disponibles";
        }
    }

    // Función para verificar si un diagnóstico tiene datos válidos
    private boolean isValidDiagnosis(DiagnosisDTO diagnosis) {
        return diagnosis.getNotes() != null || (diagnosis.getCodeCie10() != null && diagnosis.getCodeCie10().getCode() != null);
    }

    private void appendDiagnosisData(StringBuilder contentBuilder, DiagnosisDTO diagnosis) {
        if (diagnosis.getCodeCie10() != null || diagnosis.getNotes() != null) {
            contentBuilder.append("Diagnósticos:\n");
        }
        if (diagnosis.getCodeCie10() != null) {
            contentBuilder.append("Código: ").append(diagnosis.getCodeCie10().getCode()).append("\n");
            contentBuilder.append("Descripción: ").append(diagnosis.getCodeCie10().getDescription()).append("\n");
        }
        if (diagnosis.getNotes() != null) {
            contentBuilder.append("Notas: ").append(diagnosis.getNotes()).append("\n\n");
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
    @Operation(summary = "recordId Pdf")
    @GetMapping("/clinical-record/diagnosis/{recordId}/pdf")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "diagnosis.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}