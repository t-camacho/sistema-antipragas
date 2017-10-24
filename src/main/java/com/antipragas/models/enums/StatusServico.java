package com.antipragas.models.enums;

public enum StatusServico {
    PENDENTE("Pendente"),
    CONCLUIDO("Concluido"),
    CANCELADO("Cancelado");

    private String status;

    StatusServico(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
