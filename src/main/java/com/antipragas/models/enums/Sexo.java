package com.antipragas.models.enums;

/**
 * @author Ricardo Henrique Brunetto
 * 04 de Outubro de 2017
 */
public enum Sexo {

    M("Masculino"), F("Feminino");

    private String sexo;

    public String getSexo() {
        return sexo;
    }

    Sexo(String sexo) {
        this.sexo = sexo;
    }
}