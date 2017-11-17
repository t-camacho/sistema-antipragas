package com.antipragas.models;

/**
 * @author Thais Camacho
 */

import com.antipragas.models.enums.Frequencia;
import com.antipragas.models.enums.StatusProposta;
import com.antipragas.models.enums.Tipo;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "propostas")
public class Proposta {
    @Id
    @Expose
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantidade;

    @Column
    @Expose
    private Double orcamento;

    @Column(length = 250)
    @Expose
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Expose
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Frequencia frequencia;

    @Enumerated(EnumType.STRING)
    @Expose
    private StatusProposta status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    @Expose
    private Endereco endereco;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "praga_proposta", joinColumns = @JoinColumn(name = "proposta_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "praga_id", referencedColumnName = "id"))
    private Set<Praga> pragas;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    @Expose
    private Usuario funcionario;

//    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL)
//    private Set<Mensagem> mensagens;

    public Proposta() {
    }

    public Proposta(Integer quantidade, String descricao, Tipo tipo, Frequencia frequencia, StatusProposta status, Usuario usuario, Endereco endereco, Set<Praga> pragas) {
        this.quantidade = quantidade;
        this.orcamento = orcamento;
        this.descricao = descricao;
        this.tipo = tipo;
        this.frequencia = frequencia;
        this.status = status;
        this.usuario = usuario;
        this.endereco = endereco;
        this.pragas = pragas;
    }

    public Proposta(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Praga> getPragas() {
        return pragas;
    }

    public void setPragas(Set<Praga> pragas) {
        this.pragas = pragas;
    }

    public Usuario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Usuario funcionario) {
        this.funcionario = funcionario;
    }
}
