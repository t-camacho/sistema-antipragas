package com.antipragas.services;

import com.antipragas.models.Praga;
import com.antipragas.repositories.PragaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Thais Camacho
 */
public class PragaServiceImple implements PragaService {
    @Autowired
    private PragaRepository pragaRepository;

    @Override
    public List<Praga> findAll() {
        return this.pragaRepository.findAll();
    }

    @Override
    public Praga findById(Long id) {
        return this.pragaRepository.findOne(id);
    }

    @Override
    public Praga create(Praga praga) {
        return this.pragaRepository.save(praga);
    }

    @Override
    public Praga edit(Praga praga) {
        return this.pragaRepository.save(praga);
    }

    @Override
    public void deleteById(Long id) {
        this.pragaRepository.delete(id);
    }
}
