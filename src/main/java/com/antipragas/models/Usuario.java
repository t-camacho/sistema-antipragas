package com.antipragas.models;

import com.antipragas.models.enums.Role;
import com.antipragas.models.enums.Status;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String nome;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column(length = 60)
    private String senha;

    @Column(length = 10)
    private String dataDeNascimento;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 14)
    private String CPF;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telefones_id")
    private Telefone telefone;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<Endereco> enderecos;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String dataDeNascimento, Role role, Status status, String CPF, Telefone telefone) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataDeNascimento = dataDeNascimento;
        this.status = status;
        this.CPF = CPF;
        this.telefone = telefone;
        this.role = role;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Set<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(Set<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
