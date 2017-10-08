package com.antipragas.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ricardo Henrique Brunetto
 * 05 de Outubro de 2017
 */
@Controller
@PreAuthorize("hasAuthority('NIVEL_FUNCIONARIO') AND hasAuthority('NIVEL_ADMINISTRADOR')")
@RequestMapping("/funcionario")
public class FuncionarioController {

}
