package com.antipragas.services;

import com.antipragas.models.Servico;
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
}
