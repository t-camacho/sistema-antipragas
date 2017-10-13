package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum StatusProposta {
    STATUS_PROPOSTA_PENDENTE("Pendente"),
    STATUS_PROPOSTA_APROVADA("Aprovada"),
    STATUS_PROPOSTA_DELIBERADA("Deliberada"),
    STATUS_PROPOSTA_CANCELADA("Cancelada");

    private String status_proposta;

    StatusProposta(String status_proposta) {
        this.status_proposta = status_proposta;
    }

    public String getStatus_proposta() {
        return status_proposta;
    }

    public void setStatus_proposta(String status_proposta) {
        this.status_proposta = status_proposta;
    }
}
