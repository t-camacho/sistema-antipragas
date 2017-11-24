package com.antipragas.services;

import com.antipragas.models.FuncionarioTecnico;

import java.util.List;

/**
 * @author Thais Camacho
 */

public interface FuncionarioTecnicoService {
    FuncionarioTecnico findById(Long id);
    List<FuncionarioTecnico> findAll();
}
