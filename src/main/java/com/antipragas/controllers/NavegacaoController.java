package com.antipragas.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Thais Camacho
 */

@Controller
public class NavegacaoController {
    private Logger LOGGER = Logger.getLogger(NavegacaoController.class);

    @RequestMapping("/")
    public ModelAndView goHome(){

        if(LOGGER.isInfoEnabled()){
            LOGGER.info("solicitação para home");
        }

        return new ModelAndView("home", "resp", "normal");
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView goHomeResp(@RequestParam String resp){

        return new ModelAndView("home", "resp", resp);
    }

    @RequestMapping(value = "/login")
    public String goLogin(){
        return "login";
    }

    @RequestMapping("/usuario/painel")
    public String goPainel(){
        return "/usuario/painel";
    }
}
