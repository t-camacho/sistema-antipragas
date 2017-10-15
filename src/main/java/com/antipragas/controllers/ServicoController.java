package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Fornecedor;
import com.antipragas.models.Servico;
import com.antipragas.models.enums.StatusServico;
import com.antipragas.repositories.FornecedorRepository;
import com.antipragas.repositories.ServicoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author Ricardo Henrique Brunetto
 * 12 de Outubro de 2017
 */
@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private Logger LOGGER = Logger.getLogger(ServicoController.class);

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        //model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "/servicos/listar";
    }

    @RequestMapping(value = "/listar", params = {"status"}, method = RequestMethod.GET)
    public String listarServicosStatus(Model model, final HttpServletRequest request){
        StatusServico st = StatusServico.valueOf(request.getParameter("status"));
        List<Servico> servicos = servicoRepository.findAllByStatus(st);
        if(st == StatusServico.PENDENTE)
            model.addAttribute("servicos_pendentes",servicos);
        else if(st == StatusServico.CONCLUIDO)
            model.addAttribute("servicos_concluidos",servicos);
        else if(st == StatusServico.CANCELADO)
            model.addAttribute("servicos_cancelados",servicos);
        return "/servicos/listar";
    }
}