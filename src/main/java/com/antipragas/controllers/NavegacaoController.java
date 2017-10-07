package com.antipragas.controllers;

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

    @RequestMapping(value = "/login")
    public String goLogin(){

        return "login";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }
}
