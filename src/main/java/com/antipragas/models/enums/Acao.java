package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum Acao {
    ACAO_CONFIRMAR("Confirmar"), ACAO_RESETAR("Resetar");

    private String acao;

    Acao(String acao) {
        this.acao = acao;
    }
}
