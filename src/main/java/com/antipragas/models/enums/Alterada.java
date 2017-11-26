package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum Alterada {
    ALTERADA_TRUE("Proposta alterada"),
    ALTERADA_FALSE("Proposta não alterada");

    String alterada;

    Alterada(String alterada) {
        this.alterada = alterada;
    }

    public String getAlterada() {
        return alterada;
    }

    public void setAlterada(String alterada) {
        this.alterada = alterada;
    }
}
