package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum StatusMensagem {
    STATUS_MENSAGEM_LIDA("Lida"),
    STATUS_MENSAGEM_NAO_LIDA("Não lida");

    private String status;

    StatusMensagem(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
