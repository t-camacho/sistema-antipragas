package com.antipragas.controllers;


import com.antipragas.models.Servico;
import com.antipragas.models.ServicoPrototype;
import com.antipragas.models.enums.StatusServico;
import com.antipragas.services.ServicoServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/renegociando")
public class RenegociandoController {

    @Autowired
    private ServicoServiceImple servicoService;


    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public ModelAndView listarServicoRenegociando(){
        List<Servico> servico = servicoService.findAllByStatus(StatusServico.RENEGOCIANDO);
        return new ModelAndView("/servicos/renegociando","servicos",servico);
    }

}
