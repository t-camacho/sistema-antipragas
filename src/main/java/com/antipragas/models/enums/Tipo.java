package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum Tipo {
    TIPO_EXTERMINIO("Exterminio"), TIPO_PREVENCAO("Prevencao");

    Tipo(String status) {
        this.tipo = status;
    }

    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
