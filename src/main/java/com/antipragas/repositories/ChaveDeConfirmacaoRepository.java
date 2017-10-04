package com.antipragas.repositories;

import com.antipragas.models.ChaveDeConfirmacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChaveDeConfirmacaoRepository extends JpaRepository<ChaveDeConfirmacao, Long> {
    ChaveDeConfirmacao findByIdCriptografado(String id);
}