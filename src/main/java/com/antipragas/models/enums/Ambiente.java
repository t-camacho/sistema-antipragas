package com.antipragas.models.enums;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */
public enum Ambiente {
    HOSPITALAR("Hospitalar"),
    SETOR_ALIMENTICIO("Setor Alimentício"),
    CONDOMINIO("Condomínio"),
    RESIDENCIA("Residência"),
    EMPRESA("Empresa");

    String ambiente;

    Ambiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }
}
