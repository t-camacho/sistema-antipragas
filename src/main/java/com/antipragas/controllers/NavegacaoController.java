package com.antipragas.controllers;

import com.antipragas.models.enums.Nivel;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Thais Camacho
 */

@Controller
public class NavegacaoController {
    private Logger LOGGER = Logger.getLogger(NavegacaoController.class);

    @RequestMapping("/")
    public ModelAndView goHome(){

        return new ModelAndView("home", "resp", "normal");
    }

    @RequestMapping(value = "/registrar", method = RequestMethod.GET)
    public ModelAndView goHomeResp(@RequestParam String resp){
        return new ModelAndView("home", "resp", resp);
    }

    @RequestMapping(value = "/resetar", method = RequestMethod.GET)
    public ModelAndView goHLogin(@RequestParam String resp){

        return new ModelAndView("login", "resp", resp);
    }

    @RequestMapping("/registrar_funcionario")
    public ModelAndView goRegisterFuncionario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        role = role.replace("[", "");
        role = role.replace("]", "");
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(String.format("Role: [%s]", role));
        }
        if (role.equals(Nivel.NIVEL_ADMINISTRADOR.toString()))
            return new ModelAndView("administrador/registrar_funcionario", "resp", "normal");
        else
            return goHLogin("resp");
    }

    @RequestMapping(value = "/login")
    public String goLogin(){
        return "login";
    }

    @RequestMapping(value = "/teste")
    public String teste(){
        return "outros/chart";
    }

}
