package com.antipragas.repositories;

import com.antipragas.models.Proposta;
import com.antipragas.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Thais Camacho
 */

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    List<Proposta> findByUsuario(Usuario usuario);
}
