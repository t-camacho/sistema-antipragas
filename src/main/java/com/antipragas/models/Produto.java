package com.antipragas.models;

import com.antipragas.models.enums.Ambiente;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 60)
    @Expose
    String nome;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedor")
    Fornecedor fornecedor;

    @Enumerated(EnumType.STRING)
    Ambiente ambiente;

    public Produto() {
    }

    public Produto(String nome, Fornecedor fornecedor, Ambiente ambiente) {
        this.nome = nome;
        this.fornecedor = fornecedor;
        this.ambiente = ambiente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }
}
