package com.antipragas.services;

import com.antipragas.models.Praga;

import java.util.List;

/**
 * @author Thais Camacho
 */
public interface PragaService {
    List<Praga> findAll();
    Praga findById(Long id);
    Praga create(Praga praga);
    Praga edit(Praga praga);
    void deleteById(Long id);
}
