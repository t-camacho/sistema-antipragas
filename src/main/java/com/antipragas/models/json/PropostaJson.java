package com.antipragas.models.json;

import com.antipragas.models.Proposta;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author Thais Camacho
 */
public class PropostaJson {
    @Expose
    private Long ultimoId;
    @Expose
    private List<Proposta> dados;

    public PropostaJson(Long ultimoId, List<Proposta> dados) {
        this.ultimoId = ultimoId;
        this.dados = dados;
    }

    public Long getUltimoId() {
        return ultimoId;
    }

    public void setUltimoId(Long ultimoId) {
        this.ultimoId = ultimoId;
    }

    public List<Proposta> getDados() {
        return dados;
    }

    public void setDados(List<Proposta> dados) {
        this.dados = dados;
    }
}
