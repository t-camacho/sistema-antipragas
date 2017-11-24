package com.antipragas.services;

import com.antipragas.models.FuncionarioTecnico;
import com.antipragas.repositories.FuncionarioTecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Thais Camacho
 */
@Service
@Primary
public class FuncionarioTecnicoServiceImple implements FuncionarioTecnicoService {

    @Autowired
    private FuncionarioTecnicoRepository funcionarioTecnicoRepository;

    @Override
    public FuncionarioTecnico findById(Long id) {
        return funcionarioTecnicoRepository.findById(id);
    }

    @Override
    public List<FuncionarioTecnico> findAll() {
        return funcionarioTecnicoRepository.findAll();
    }
}
