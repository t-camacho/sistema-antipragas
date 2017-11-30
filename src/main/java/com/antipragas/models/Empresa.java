package com.antipragas.models;

import javax.persistence.*;

@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String nome;

    @Column(length = 255)
    private String inicioFuncionamentoSemana;

    @Column(length = 255)
    private String fimFuncionamentoSemana;

    @Column(length = 255)
    private String inicioFuncionamentoHorario;

    @Column(length = 255)
    private String fimFuncionamentoHorario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInicioFuncionamentoSemana() {
        return inicioFuncionamentoSemana;
    }

    public void setInicioFuncionamentoSemana(String inicioFuncionamentoSemana) {
        this.inicioFuncionamentoSemana = inicioFuncionamentoSemana;
    }

    public String getFimFuncionamentoSemana() {
        return fimFuncionamentoSemana;
    }

    public void setFimFuncionamentoSemana(String fimFuncionamentoSemana) {
        this.fimFuncionamentoSemana = fimFuncionamentoSemana;
    }

    public String getInicioFuncionamentoHorario() {
        return inicioFuncionamentoHorario;
    }

    public void setInicioFuncionamentoHorario(String inicioFuncionamentoHorario) {
        this.inicioFuncionamentoHorario = inicioFuncionamentoHorario;
    }

    public String getFimFuncionamentoHorario() {
        return fimFuncionamentoHorario;
    }

    public void setFimFuncionamentoHorario(String fimFuncionamentoHorario) {
        this.fimFuncionamentoHorario = fimFuncionamentoHorario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empresa empresa = (Empresa) o;

        if (!id.equals(empresa.id)) return false;
        if (!nome.equals(empresa.nome)) return false;
        if (!inicioFuncionamentoSemana.equals(empresa.inicioFuncionamentoSemana)) return false;
        if (!fimFuncionamentoSemana.equals(empresa.fimFuncionamentoSemana)) return false;
        if (!inicioFuncionamentoHorario.equals(empresa.inicioFuncionamentoHorario)) return false;
        return fimFuncionamentoHorario.equals(empresa.fimFuncionamentoHorario);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        result = 31 * result + inicioFuncionamentoSemana.hashCode();
        result = 31 * result + fimFuncionamentoSemana.hashCode();
        result = 31 * result + inicioFuncionamentoHorario.hashCode();
        result = 31 * result + fimFuncionamentoHorario.hashCode();
        return result;
    }
}
