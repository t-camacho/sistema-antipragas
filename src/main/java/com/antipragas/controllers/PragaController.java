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

@RestController
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
    public ModelAndView adicionarPraga(@RequestParam String nome){
        String resp = "ok";

        Praga praga = new Praga(nome);

        try {
            pragaService.create(praga);
        }catch (Exception e){
            resp = "error_excluir";
        }

        return new ModelAndView("redirect:/praga/visualizar", resp, "add");
    }

    @RequestMapping(value = "/deletar", method = RequestMethod.POST)
    public  ModelAndView deletarPraga(@RequestParam String id){
        String resp = "ok";
        try {
            pragaService.deleteById(Long.parseLong(id));
        }catch (Exception e){
            resp = "error_excluir";
        }

        return new ModelAndView("redirect:/praga/visualizar", resp, "deletar");
    }

    @RequestMapping(value = "/alterar", method = RequestMethod.POST)
    public ModelAndView alterarPraga(@RequestParam String idAltPraga, @RequestParam String nomePraga){
        String resp = "ok";
        Praga praga = pragaService.findById(Long.parseLong(idAltPraga));

        praga.setNome(nomePraga);

        try{
            pragaService.edit(praga);
        }catch (Exception e){
            resp = "error";
        }

        return new ModelAndView("redirect:/praga/visualizar", resp, "alterar");
    }
}
