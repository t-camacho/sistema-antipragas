package com.antipragas.models;

/*
    @author Rafael
 */

import com.antipragas.models.enums.StatusServico;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Servico extends ServicoPrototype {

    protected Servico(Servico servico){
        this.proposta = servico.getProposta();
        this.cliente = servico.getCliente();
        this.orcamento = servico.getOrcamento();
        this.descricao = servico.getDescricao();
        this.status = servico.getStatus();
        this.endereco = servico.getEndereco();
    }

    public Servico() {
    }

    public Servico(Proposta proposta, Usuario cliente, String descricao, StatusServico status, Endereco endereco) {
        this.proposta = proposta;
        this.cliente = cliente;
        this.orcamento = 0.0;
        this.descricao = descricao;
        this.status = status;
        this.endereco = endereco;
    }

    public ServicoPrototype clonar(){
        return new Servico(this);
    }
}
