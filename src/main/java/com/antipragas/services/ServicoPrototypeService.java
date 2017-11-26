package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.models.ServicoPrototype;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;

import java.util.List;

/**
 * @author Thais Camacho
 */
public interface ServicoPrototypeService {
    List<ServicoPrototype> findAllByStatus(StatusServico statusServico);
    ServicoPrototype create(ServicoPrototype servico);
    List<ServicoPrototype> findByCliente(Usuario cliente);
    List<ServicoPrototype> findByProposta(Proposta proposta);
    ServicoPrototype findById(Long id);
    void edit(ServicoPrototype servico);
}
