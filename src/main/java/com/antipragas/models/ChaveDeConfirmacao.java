package com.antipragas.models;

import javax.persistence.*;

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

    public ChaveDeConfirmacao() {
    }

    public ChaveDeConfirmacao(Long idUser, String chaveCrip) {
        this.idUsuario = idUser;
        this.idCriptografado = chaveCrip;
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
}
