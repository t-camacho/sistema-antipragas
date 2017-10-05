package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */

public enum Status {
    STATUS_ATIVADA("Ativada"), STATUS_DESATIVADA("Desativada");

    Status(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
