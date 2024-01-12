package com.dh.apiClinic.converters;

import com.dh.apiClinic.enums.DiagnosisStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DiagnosisStatusConverter implements AttributeConverter<DiagnosisStatus, String> {
    @Override
    public String convertToDatabaseColumn(DiagnosisStatus status) {
        return status.getValue();
    }

    @Override
    public DiagnosisStatus convertToEntityAttribute(String value) {
        for (DiagnosisStatus status : DiagnosisStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
