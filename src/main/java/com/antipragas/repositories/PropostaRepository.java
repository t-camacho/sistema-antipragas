package com.antipragas.repositories;

import com.antipragas.models.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thais Camacho
 */

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
}
