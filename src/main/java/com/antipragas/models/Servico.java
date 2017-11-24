package com.antipragas.models;

/*
    @author Rafael
 */

import com.antipragas.models.enums.StatusServico;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "servicos")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @Column
    private Double orcamento;

    @Column(length = 255)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusServico status;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "funcionario_tecnico_id")
    private FuncionarioTecnico funcionarioTecnico;

    @Column(name = "data_horario")
    private Timestamp dataHorario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "pragas_previstas_servico",
            joinColumns = @JoinColumn(name = "servico_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "praga_id", referencedColumnName = "id")
    )
    private Set<Praga> pragas;

    public Servico() {}

    public Servico(Proposta proposta, Usuario cliente, String descricao, StatusServico status, Endereco endereco, Set<Praga> pragas, double orcamento) {
        this.proposta = proposta;
        this.cliente = cliente;
        this.orcamento = orcamento;
        this.descricao = descricao;
        this.status = status;
        this.endereco = endereco;
        this.pragas = pragas;
    }

    public Servico(Proposta proposta, Usuario cliente, String descricao, StatusServico status, Endereco endereco, double orcamento) {
        this.proposta = proposta;
        this.cliente = cliente;
        this.orcamento = orcamento;
        this.descricao = descricao;
        this.status = status;
        this.endereco = endereco;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusServico getStatus() {
        return status;
    }

    public void setStatus(StatusServico status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public FuncionarioTecnico getFuncionarioTecnico() {
        return funcionarioTecnico;
    }

    public void setFuncionarioTecnico(FuncionarioTecnico funcionarioTecnico) {
        this.funcionarioTecnico = funcionarioTecnico;
    }

    public Timestamp getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(Timestamp dataHorario) {
        this.dataHorario = dataHorario;
    }

    public Set<Praga> getPragas() {
        return pragas;
    }

    public void setPragas(Set<Praga> pragas) {
        this.pragas = pragas;
    }
}
