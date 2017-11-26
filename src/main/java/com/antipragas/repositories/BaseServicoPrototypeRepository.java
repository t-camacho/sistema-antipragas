package com.antipragas.repositories;

import com.antipragas.models.ServicoPrototype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author Thais Camacho
 */
@NoRepositoryBean
public interface BaseServicoPrototypeRepository<T extends ServicoPrototype> extends JpaRepository<T, Long> {
}
