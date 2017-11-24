package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum Cancelado {
    CANCELADO_PELO_CLIENTE("Cancelado pelo cliente"),
    CANCELADO_PELA_EMPRESA("Cancelado pela empresa"),
    CANCELADO_NINGUEM("Proposta nãoainda cancelada");

    String cancelado;

    Cancelado(String cancelado) {
        this.cancelado = cancelado;
    }

    public String getCancelado() {
        return cancelado;
    }

    public void setCancelado(String cancelado) {
        this.cancelado = cancelado;
    }
}
