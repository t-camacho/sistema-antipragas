package com.antipragas.repositories;

import com.antipragas.models.Proposta;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Thais Camacho
 */

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    List<Proposta> findByUsuario(Usuario usuario);
    List<Proposta> findByUsuarioAndIdGreaterThan(Usuario usuario, Long id);
    List<Proposta> findByUsuarioAndIdGreaterThanAndStatus(Usuario usuario, Long id, StatusProposta status);
    List<Proposta> findByIdGreaterThanAndStatus(Long id, StatusProposta status);
    List<Proposta> findByFuncionarioAndIdGreaterThanAndStatus(Usuario funcionario, Long id, StatusProposta status);
    List<Proposta> findByFuncionarioAndIdGreaterThan(Usuario usuario, Long id);
    List<Proposta> findByStatus(StatusProposta status);
}
