package com.antipragas.models;

import com.antipragas.models.enums.Acao;
import com.antipragas.models.enums.Nivel;

import javax.persistence.*;

/**
 * @author Thais Camacho
 */

@Entity
@Table(name = "chaves_de_confirmacao")
public class ChaveDeConfirmacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long idUsuario;

    @Column(length = 150)
    private String idCriptografado;

    @Enumerated(EnumType.STRING)
    private Acao acao;

    public ChaveDeConfirmacao() {
    }

    public ChaveDeConfirmacao(Long idUsuario, String chaveCrip, Acao acao) {
        this.idUsuario = idUsuario;
        this.idCriptografado = chaveCrip;
        this.acao = acao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdCriptografado() {
        return idCriptografado;
    }

    public void setIdCriptografado(String idCriptografado) {
        this.idCriptografado = idCriptografado;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }
}
