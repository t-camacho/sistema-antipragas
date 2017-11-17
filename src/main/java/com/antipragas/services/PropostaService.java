package com.antipragas.services;

import com.antipragas.models.Proposta;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusProposta;

import java.awt.print.Pageable;
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
    List<Proposta> findByUsuario(Usuario usuario);
    List<Proposta> findByUsuarioAndIdGreaterThan(Usuario usuario, Long id);
    List<Proposta> findByUsuarioAndIdGreaterThanAndStatus(Usuario usuario, Long id, StatusProposta status);

}
