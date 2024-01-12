package com.dh.apiClinic.security.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NameRol {
    ROLE_ADMIN("ROL_ADMIN"),
    ROLE_USER("ROL_USER");

    private String roleName;

    NameRol(String roleName) {
        this.roleName = roleName;
    }

    @JsonValue
    public String getRoleName() {
        return roleName;
    }

    @JsonCreator
    public static NameRol fromString(String value) {
        for (NameRol rol : NameRol.values()) {
            if (rol.getRoleName().equalsIgnoreCase(value)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol inválido: " + value);
    }
}