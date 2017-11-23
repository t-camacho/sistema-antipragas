package com.antipragas.services;

import com.antipragas.models.Servico;
import com.antipragas.models.enums.StatusServico;

import java.util.List;

public interface ServicoService {
    List<Servico> findAllByStatus(StatusServico statusServico);

}
