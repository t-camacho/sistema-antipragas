package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Fornecedor;
import com.antipragas.repositories.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id, Model model) {
        fornecedorRepository.delete(id);
        return "redirect:/fornecedor/listar";
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        Fornecedor f = new Fornecedor();
        ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
        enderecos.add(new Endereco());
        f.setEnderecos(enderecos);
        model.addAttribute("fornecedor", f);
        return "/fornecedor/cadastrar";
    }

    @RequestMapping(value = "/cadastrar", params = {"adicionarEndereco"}, method = RequestMethod.POST)
    public String adicionarEndereco(final Fornecedor fornecedor,
                                    final BindingResult result) {
        fornecedor.getEnderecos().add(new Endereco());
        return "/fornecedor/cadastrar";
    }

    @RequestMapping(value = "/cadastrar", params = {"removerEndereco"}, method = RequestMethod.POST)
    public String removerEndereco(
            final Fornecedor fornecedor,
            final HttpServletRequest request,
            final BindingResult result) {
        final Integer idEndereco = Integer.valueOf(request.getParameter("removerEndereco"));
        fornecedor.getEnderecos().remove(idEndereco.intValue());
        return "/fornecedor/cadastrar";
    }

    @RequestMapping(value = "/cadastrar", params = {"salvar"}, method = RequestMethod.POST)
    public String salvarCadastro(Fornecedor fornecedor,
                                 final BindingResult result) {
        for(Endereco e: fornecedor.getEnderecos()) {
            e.setFornecedor(fornecedor);
        }
        fornecedorRepository.save(fornecedor);
        return "/fornecedor/listar";
    }
}
