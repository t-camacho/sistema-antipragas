package com.antipragas.models.enums;

/**
 * @author Thais Camacho
 */
public enum Frequencia {
    FREQUENCIA_DIARIAMENTE("Diariamente"),
    FREQUENCIA_SEMANALMENTE("Semanalmente"),
    FREQUENCIA_MENSALMENTE("Mensalmente"),
    FREQUENCIA_ANUALMENTE("Anualmente"),
    FREQUENCIA_NULA("Nula");

    String frequencia;

    Frequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }
}
