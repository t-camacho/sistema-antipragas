package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */

public enum Role {
    ROLE_NORMAL("Normal"), ROLE_ADMINISTRADOR("Administrador"),
    ROLE_FUNCIONARIO("Funcionario");

    private String role;

    public String getRole() {
        return role;
    }

    Role(String role) {
        this.role = role;
    }
}
