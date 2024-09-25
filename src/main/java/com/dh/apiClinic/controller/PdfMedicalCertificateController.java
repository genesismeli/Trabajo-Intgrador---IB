package com.dh.apiClinic.controller;

import com.dh.apiClinic.DTO.ClinicalRecordDTO;
import com.dh.apiClinic.DTO.MedicationDTO;
import com.dh.apiClinic.service.IClinicalRecordService;
import com.dh.apiClinic.enums.Speciality;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Tag(name = "Pdf", description = "Método para PDF")
@RestController
@RequestMapping("/pdf")
public class PdfMedicalCertificateController {

    @Autowired
    IClinicalRecordService iclinicalRecordService;

    @Autowired
    public PdfMedicalCertificateController(IClinicalRecordService clinicalRecordService) {
        this.iclinicalRecordService = clinicalRecordService;
    }

    @Operation(summary = "Generar certificado médico en formato PDF")
    @GetMapping("/medical-certificate/{recordId}")
    public ResponseEntity<byte[]> generateMedicalCertificatePdf(@PathVariable Long recordId) {
        ClinicalRecordDTO clinicalRecord = iclinicalRecordService.findClinicalRecordById(recordId);

        // Verificar si hay datos de certificado médico disponibles
        if (clinicalRecord != null && clinicalRecord.getMedicalCertificate() != null && !clinicalRecord.getMedicalCertificate().isEmpty()) {
            String clinicalRecordContent = generateClinicalRecordContent(clinicalRecord);
            // Agregar el contenido del certificado médico al PDF
            byte[] pdfBytes = generatePdfBytes(clinicalRecordContent);

            // Configurar los encabezados de la respuesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "medical_certificate.pdf");

            // Retornar la respuesta con el PDF generado
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } else {
            // Si no hay datos de certificado médico disponibles, devolver una respuesta 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    private void addCertificateMedicData(StringBuilder contentBuilder, ClinicalRecordDTO clinicalRecord) {
        if(clinicalRecord.getMedicalCertificate() != null) {
            contentBuilder.append("___________________________Certificado Médico____________________________________\n");
            contentBuilder.append("Detalles: ").append(clinicalRecord.getMedicalCertificate()).append("\n");
        }
    }

    private String generateClinicalRecordContent(ClinicalRecordDTO clinicalRecord) {
        StringBuilder contentBuilder = new StringBuilder();
        if (clinicalRecord != null && clinicalRecord.getMedicalCertificate() != null && !clinicalRecord.getMedicalCertificate().isEmpty()) {


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


        addCertificateMedicData(contentBuilder, clinicalRecord);
            return contentBuilder.toString();
        } else {
            return "No hay datos de certificado médico disponibles";
        }
    }

    private String getSpecialityValue(Speciality speciality) {
        return speciality != null ? speciality.getValue() : "";
    }

    private byte[] generatePdfBytes(String content) {
        if (content == null) {
            return new byte[0];
        }

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

}
