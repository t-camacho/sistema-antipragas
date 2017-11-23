package com.antipragas.models;

import com.antipragas.models.enums.StatusMensagem;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Thais Camacho
 */

@Entity
@Table(name = "mensagens")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Expose
    private Long idDe;

    private Long idPara;

    @Column(length = 350)
    @Expose
    private String mensagem;

    private Timestamp horario;

    @Enumerated(EnumType.STRING)
    private StatusMensagem status;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    public Mensagem() {
    }

    public Mensagem(Long idDe, Long idPara, String mensagem, Timestamp horario, StatusMensagem status, Proposta proposta) {
        this.idDe = idDe;
        this.idPara = idPara;
        this.mensagem = mensagem;
        this.horario = horario;
        this.status = status;
        this.proposta = proposta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDe() {
        return idDe;
    }

    public void setIdDe(Long idDe) {
        this.idDe = idDe;
    }

    public Long getIdPara() {
        return idPara;
    }

    public void setIdPara(Long idPara) {
        this.idPara = idPara;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public StatusMensagem getStatus() {
        return status;
    }

    public void setStatus(StatusMensagem status) {
        this.status = status;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
