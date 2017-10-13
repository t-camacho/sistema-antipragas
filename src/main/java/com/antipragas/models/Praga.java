package com.antipragas.models;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Entity
@Table(name = "pragas")
public class Praga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String nome;


    @ManyToMany(mappedBy = "pragas")
    private Set<Proposta> propostas;

    public Praga() {
    }

    public Praga(String nome) {
        this.nome = nome;
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
}
