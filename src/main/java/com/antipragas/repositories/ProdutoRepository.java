package com.antipragas.repositories;

import com.antipragas.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
