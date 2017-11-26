package com.antipragas.models.json;

import com.antipragas.models.Mensagem;
import com.antipragas.models.Proposta;
import com.antipragas.models.Servico;
import com.antipragas.models.ServicoPrototype;
import com.google.gson.annotations.Expose;

import javax.xml.ws.Service;
import java.util.List;

/**
 * @author Thais Camacho
 */
public class PropostaAlteradaJson {
    @Expose
    private String status;

    @Expose
    private Proposta proposta;

    @Expose
    private List<ServicoPrototype> servicos;

    public PropostaAlteradaJson(String status, Proposta proposta, List<ServicoPrototype> servicos) {
        this.status = status;
        this.proposta = proposta;
        this.servicos = servicos;
    }

    public PropostaAlteradaJson(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }

    public List<ServicoPrototype> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoPrototype> servicos) {
        this.servicos = servicos;
    }
}
