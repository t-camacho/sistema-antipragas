package com.antipragas.models;

import javax.persistence.*;

/**
 * @author Thais Camacho
 */

@Entity
@Table(name = "telefones")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 13)
    private String telefone;

    @Column(length = 14)
    private String celular;

    @OneToOne(mappedBy = "telefone")
    private Usuario usuario;

    public Telefone() {
    }

    public Telefone(String telefone, String celular) {
        this.telefone = telefone;
        this.celular = celular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
