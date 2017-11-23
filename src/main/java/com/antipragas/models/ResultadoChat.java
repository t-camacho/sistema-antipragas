package com.antipragas.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author Thais Camacho
 */
public class ResultadoChat {
    @Expose
    private String status;
    @Expose
    private String timestamp;
    @Expose
    private String ultimoId;
    @Expose
    private String idProposta;
    @Expose
    private List<Mensagem> dados;


    public ResultadoChat(Long ultimoId, List<Proposta> propostas_paginada) {
    }

    public ResultadoChat(String status, String timestamp, String ultimoId, String idProposta) {
        this.status = status;
        this.timestamp = timestamp;
        this.ultimoId = ultimoId;
        this.idProposta = idProposta;
    }

    public ResultadoChat(String status, String timestamp, String ultimoId, String idProposta, List<Mensagem> dados) {
        this.status = status;
        this.timestamp = timestamp;
        this.ultimoId = ultimoId;
        this.idProposta = idProposta;
        this.dados = dados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUltimoId() {
        return ultimoId;
    }

    public void setUltimoId(String ultimoId) {
        this.ultimoId = ultimoId;
    }

    public List<Mensagem> getDados() {
        return dados;
    }

    public void setDados(List<Mensagem> dados) {
        this.dados = dados;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}
