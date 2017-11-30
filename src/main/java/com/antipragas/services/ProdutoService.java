package com.antipragas.services;

import com.antipragas.models.Produto;

import java.util.List;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */
public interface ProdutoService {
    List<Produto> findAll();
    Produto findById(Long id);
    Produto create(Produto produto);
    Produto edit(Produto produto);
    void deleteById(Long id);
}
