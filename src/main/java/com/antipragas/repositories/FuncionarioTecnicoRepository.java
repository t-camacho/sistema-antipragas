package com.antipragas.repositories;

import com.antipragas.models.FuncionarioTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thais Camacho
 */
@Repository
public interface FuncionarioTecnicoRepository extends JpaRepository<FuncionarioTecnico, Long> {
    FuncionarioTecnico findById(Long id);
}
