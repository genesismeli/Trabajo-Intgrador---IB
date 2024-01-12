package com.dh.apiClinic.enums;

public enum Gender {

    FEMENINO("FEMENINO"),
    MASCULINO("MASCULINO"),
    NOESPECIFICAR("NO ESPECIFICAR");


    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}