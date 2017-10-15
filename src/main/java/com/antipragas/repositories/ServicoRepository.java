package com.antipragas.repositories;

import com.antipragas.models.Servico;
import com.antipragas.models.enums.StatusServico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ricardo Henrique Brunetto
 *         14 de Outubro de 2017
 */
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findAllByStatus(StatusServico statusServico);
}
