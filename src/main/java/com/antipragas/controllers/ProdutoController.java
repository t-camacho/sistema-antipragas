package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Fornecedor;
import com.antipragas.models.Produto;
import com.antipragas.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * @author Ricardo Henrique Brunetto
 * 24 de Novembro de 2017
 */

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;


    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("produtos", produtoService.findAll());
        return "/produto/listar";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        produtoService.deleteById(id);
        return "redirect:/produto/listar";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        Produto p = new Produto();
//        model.addAttribute("produto", f);
        return "/produto/cadastrar";
    }
}
