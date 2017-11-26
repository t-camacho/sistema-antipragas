package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.models.ServicoPrototype;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;
import com.antipragas.repositories.ServicoPrototypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Thais Camacho
 */
@Service
public class ServicoPrototypeServiceImple implements ServicoPrototypeService {

    @Autowired
    ServicoPrototypeRepository servicoPrototypeRepository;

    @Override
    public List<ServicoPrototype> findAllByStatus(StatusServico statusServico) {
        return servicoPrototypeRepository.findAllByStatus(statusServico);
    }

    @Override
    public ServicoPrototype create(ServicoPrototype servico) {
        return servicoPrototypeRepository.save(servico);
    }

    @Override
    public List<ServicoPrototype> findByCliente(Usuario cliente) {
        return servicoPrototypeRepository.findByCliente(cliente);
    }

    @Override
    public List<ServicoPrototype> findByProposta(Proposta proposta) {
        return servicoPrototypeRepository.findByProposta(proposta);
    }

    @Override
    public ServicoPrototype findById(Long id) {
        return servicoPrototypeRepository.findById(id);
    }

    @Override
    public void edit(ServicoPrototype servico) {
        servicoPrototypeRepository.save(servico);
    }
}
