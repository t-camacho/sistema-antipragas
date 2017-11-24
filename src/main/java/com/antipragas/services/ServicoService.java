package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.models.Servico;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;

import java.util.List;

public interface ServicoService {
    List<Servico> findAllByStatus(StatusServico statusServico);
    Servico create(Servico servico);
    List<Servico> findByCliente(Usuario cliente);
    List<Servico> findByProposta(Proposta proposta);
    Servico findById(Long id);
    void edit(Servico servico);
}
