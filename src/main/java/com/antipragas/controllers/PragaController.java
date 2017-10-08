package com.antipragas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Thais Camacho
 */

@Controller
@RequestMapping("/praga")
public class PragaController {
    @RequestMapping("/crud")
    public String goPragas(){
        return "/outros/gerenciar_pragas";
    }
}
