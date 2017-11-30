package com.antipragas.models;

import com.antipragas.models.enums.StatusServico;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Thais Camacho
 */
@Entity
@Inheritance
public abstract class ServicoPrototype {
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    protected Proposta proposta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    protected Usuario cliente;

    @Column
    @Expose
    protected Double orcamento;

    @Column(length = 255)
    @Expose
    protected String descricao;

    @Enumerated(EnumType.STRING)
    protected StatusServico status;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    protected Endereco endereco;

    @ManyToOne
    @JoinColumn(name = "funcionario_tecnico_id")
    @Expose
    protected FuncionarioTecnico funcionarioTecnico;

    @Column(name = "data_horario")
    @Expose
    protected Timestamp dataHorario;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "pragas_previstas_servico",
            joinColumns = @JoinColumn(name = "servico_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "praga_id", referencedColumnName = "id")
    )
    protected Set<Praga> pragas;

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

    public abstract ServicoPrototype clonar();
}
