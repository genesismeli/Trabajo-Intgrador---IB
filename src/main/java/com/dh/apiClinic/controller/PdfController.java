package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.DiagnosisDTO;
import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.DTO.PhysicalExamDTO;
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
public class PdfController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Find all clinical record")
    @GetMapping("/clinical-record/{recordId}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long recordId) {
        // Lógica para obtener los datos de la ficha clínica con el ID proporcionado
        // Puedes utilizar tu servicio de fichas clínicas para obtener los datos necesarios
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);

        // Obtener el contenido de la ficha clínica
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);

        // Generar el PDF
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        // Configurar la respuesta HTTP para la descarga del PDF
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ficha_clinica.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }


    private String generateClinicalRecordContent(ClinicalRecordDTO clinicalRecord) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Información General:\n");
        contentBuilder.append("Fecha: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(clinicalRecord.getDate())).append("\n");
        contentBuilder.append("Nombre del Paciente: ").append(clinicalRecord.getPatient().getName()).append("\n");
        contentBuilder.append("Apellido del Paciente: ").append(clinicalRecord.getPatient().getLastName()).append("\n");
        contentBuilder.append("Fecha de Nacimiento: ").append(new SimpleDateFormat("dd/MM/yyyy").format(clinicalRecord.getPatient().getBirthdate())).append("\n");
        contentBuilder.append("Email: ").append(clinicalRecord.getPatient().getEmail()).append("\n\n");

        // Sección de Exámenes Físicos
        for (PhysicalExamDTO exam : clinicalRecord.getPhysicalExams()) {
            appendPhysicalExamData(contentBuilder, exam);
        }
        // Sección de Medicamentos
        for (MedicationDTO medication : clinicalRecord.getMedications()) {
            appendMedicationData(contentBuilder, medication);
        }
        // Sección de Diagnósticos
        for (DiagnosisDTO diagnosis : clinicalRecord.getDiagnoses()) {
            appendDiagnosisData(contentBuilder, diagnosis);
        }
        return contentBuilder.toString();
    }

    private void appendPhysicalExamData(StringBuilder contentBuilder, PhysicalExamDTO exam) {
        if (exam.getHeartRate() != null || exam.getOxygenSaturation() != null || exam.getRespiratoryRate() != null
                || exam.getSystolicPressure() != null || exam.getDiastolicPressure() != null || exam.getBeatsPerMinute() != null ||
                exam.getGlucose() != null) {
            contentBuilder.append("Exámenes Físicos:\n");
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

    private void appendMedicationData(StringBuilder contentBuilder, MedicationDTO medication) {
        if (medication.getVademecum() != null) {
            contentBuilder.append("Medicamentos:\n");
            contentBuilder.append("Concentración: ").append(medication.getVademecum().getConcentracion()).append("\n");
            contentBuilder.append("Nombre Comercial: ").append(medication.getVademecum().getNombre_comercial()).append("\n");
            contentBuilder.append("Presentación: ").append(medication.getVademecum().getPresentacion()).append("\n");
            contentBuilder.append("Forma Farmaceútica: ").append(medication.getVademecum().getForma_farmaceutica()).append("\n\n");
        }
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
    @GetMapping("/clinical-record/{recordId}/pdf")
    public ResponseEntity<byte[]> generateClinicalRecordPdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);
        String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
        byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "ficha_clinica.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
