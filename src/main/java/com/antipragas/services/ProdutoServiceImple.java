package com.antipragas.services;

import com.antipragas.models.Produto;
import com.antipragas.repositories.ProdutoRepository;
import com.antipragas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */

@Service
@Primary
public class ProdutoServiceImple implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public List<Produto> findAll() {
        return this.produtoRepository.findAll();
    }

    @Override
    public Produto findById(Long id) {
        return this.produtoRepository.findOne(id);
    }

    @Override
    public Produto create(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Override
    public Produto edit(Produto produto) {
        return this.produtoRepository.save(produto);
    }

    @Override
    public void deleteById(Long id) {
        this.produtoRepository.delete(id);
    }
}
