package com.antipragas.models.enums;

/**
 * @author Ricardo Henrique Brunetto
 * 04 de Outubro de 2017
 */
public enum Nivel {
    NIVEL_CLIENTE("Cliente"),
    NIVEL_FUNCIONARIO("Funcionario"),
    NIVEL_ADMINISTRADOR("Administrador");

    private String nivel;

    public String getNivel() {
        return nivel;
    }

    Nivel(String nivel) {
        this.nivel = nivel;
    }
}
