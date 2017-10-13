package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Thais Camacho
 */

@Service
@Primary
public class PropostaServiceImple implements PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Override
    public List<Proposta> findAll() {
        return propostaRepository.findAll();
    }

    @Override
    public Proposta findById(Long id) {
        return propostaRepository.findOne(id);
    }

    @Override
    public Proposta create(Proposta proposta) {
        return propostaRepository.save(proposta);
    }

    @Override
    public Proposta edit(Proposta proposta) {
        return propostaRepository.save(proposta);
    }

    @Override
    public void deleteById(Long id) {
        propostaRepository.delete(id);
    }
}
