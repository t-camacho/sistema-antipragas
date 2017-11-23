package com.antipragas.models.enums;

public enum StatusServico {
    PRE_SERVICO("Pré-Serviço"),
    PENDENTE("Pendente"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private String status;

    StatusServico(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
