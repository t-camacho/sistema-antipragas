package com.antipragas.services;

import com.antipragas.models.Proposta;

import java.util.List;

/**
 * @author Thais Camacho
 */
public interface PropostaService {
    List<Proposta> findAll();
    Proposta findById(Long id);
    Proposta create(Proposta proposta);
    Proposta edit(Proposta proposta);
    void deleteById(Long id);
}
