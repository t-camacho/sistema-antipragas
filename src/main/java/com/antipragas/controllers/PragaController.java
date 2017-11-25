package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Praga;
import com.antipragas.services.PragaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Controller
@RequestMapping("/praga")
public class PragaController {

    @Autowired
    private PragaService pragaService;

    @RequestMapping("/visualizar")
    public ModelAndView goPragas(){

        List pragas = pragaService.findAll();

        return new ModelAndView("/outros/gerenciar_pragas", "pragas", pragas);
    }

    @RequestMapping(value = "/adicionar", method = RequestMethod.POST)
    public String adicionarPraga(@RequestParam String nome){
        try {
            Praga praga = new Praga(nome);
            pragaService.create(praga);
        }catch (Exception e){
            return "redirect:/praga/visualizar?error";
        }

        return "redirect:/praga/visualizar?sucesso";
    }

    @RequestMapping(value = "/deletar", method = RequestMethod.POST)
    public String deletarPraga(@RequestParam String id){
        try {
            pragaService.deleteById(Long.parseLong(id));
        }catch (Exception e){
            return "redirect:/praga/visualizar?error_excluir";
        }

        return "redirect:/praga/visualizar?sucesso";
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public String alterarPraga(@RequestParam String idAltPraga, @RequestParam String nomePraga){
        try{
            Praga praga = pragaService.findById(Long.parseLong(idAltPraga));
            praga.setNome(nomePraga);
            pragaService.edit(praga);
        }catch (Exception e){
            return "redirect:/praga/visualizar?error";
        }
        return "redirect:/praga/visualizar?sucesso";
    }
}
