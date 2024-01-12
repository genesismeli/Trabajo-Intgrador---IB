package com.dh.apiClinic.enums;

public enum DiagnosisStatus {
    ACTIVO("Activo"),
    RECUPERADO("Recuperado"),
    RECURRENCIA("Recurrencia"),
    RECAIDA("Recaída"),
    INACTIVO("Inactivo"),
    REMISION("Remisión"),
    RESUELTO("Resuelto");
    private final String value;

    DiagnosisStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
