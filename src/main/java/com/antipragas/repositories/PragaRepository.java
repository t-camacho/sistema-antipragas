package com.antipragas.repositories;

import com.antipragas.models.Praga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Thais Camacho
 */
@Repository
public interface PragaRepository extends JpaRepository<Praga, Long> {

}
