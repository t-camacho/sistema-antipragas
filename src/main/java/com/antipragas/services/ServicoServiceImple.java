package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.models.Servico;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;
import com.antipragas.repositories.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServicoServiceImple implements ServicoService {

    @Autowired
    private ServicoRepository serviceRepository;

    @Override
    public List<Servico> findAllByStatus(StatusServico statusServico) {
        return serviceRepository.findAllByStatus(statusServico);
    }

    @Override
    public Servico create(Servico servico) {
        return serviceRepository.save(servico);
    }

    @Override
    public List<Servico> findByCliente(Usuario cliente) {
        return null;
    }

    @Override
    public List<Servico> findByProposta(Proposta proposta) {

        return serviceRepository.findByProposta(proposta);
    }

    @Override
    public Servico findById(Long id) {
        return serviceRepository.findById(id);
    }

    @Override
    public void edit(Servico servico) {
        serviceRepository.save(servico);
    }
}
