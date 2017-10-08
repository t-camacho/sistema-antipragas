package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Fornecedor;
import com.antipragas.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "/fornecedor/listar";
    }

    @RequestMapping("/cadastrar")
    public String cadastrar(Model model) {
        Fornecedor f = new Fornecedor();
        f.getEnderecos().add(new Endereco());
        model.addAttribute("fornecedor", f);
        return "/fornecedor/cadastrar";
    }

    /*
    @RequestMapping(value = "/cadastrar", params = {"novoEndereco"})
    public String adicionarEndereco(final Fornecedor fornecedor) {
        fornecedor.getEnderecos().add(new Endereco());
        return "cadastrar";
    }

    @RequestMapping(value = "/cadastrar", params = {"removerEndereco"})
    public String removerEndereco(final Fornecedor fornecedor, final HttpServletRequest request) {
        final Integer idEndereco = Integer.valueOf(request.getParameter("removerEndereco"));
        fornecedor.getEnderecos().remove(idEndereco.intValue());
        return "cadastrar";
    } */

    public String salvarCadastro(Fornecedor fornecedor) {
        for(Endereco e: fornecedor.getEnderecos()) {
            e.setFornecedor(fornecedor);
        }
        fornecedorRepository.save(fornecedor);
        return "redirect:/fornecedor/listar";
    }
}
