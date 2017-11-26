package com.antipragas.repositories;

import com.antipragas.models.Proposta;
import com.antipragas.models.Servico;
import com.antipragas.models.ServicoPrototype;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;

import java.util.List;

/**
 * @author Thais Camacho
 */
public interface ServicoPrototypeRepository extends BaseServicoPrototypeRepository<ServicoPrototype> {
    List<ServicoPrototype> findAllByStatus(StatusServico statusServico);
    //Iterable<Servico> findByClienteAndStatus(Usuario cliente, StatusServico statusServico);
    List<ServicoPrototype> findByClienteAndStatus(Usuario cliente, StatusServico statusServico);
    List<ServicoPrototype> findByCliente(Usuario cliente);
    List<ServicoPrototype> findByProposta(Proposta proposta);
    ServicoPrototype findById(Long id);
}
