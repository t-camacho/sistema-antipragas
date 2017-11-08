package com.antipragas.controllers;

import com.antipragas.models.*;
import com.antipragas.models.enums.Frequencia;
import com.antipragas.models.enums.StatusProposta;
import com.antipragas.models.enums.Tipo;
import com.antipragas.services.MensagemService;
import com.antipragas.services.PragaService;
import com.antipragas.services.PropostaService;
import com.antipragas.services.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author Thais Camacho
 */

@Controller
@RequestMapping("/proposta")
public class PropostaController {

    private Logger LOGGER = Logger.getLogger(PropostaController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PragaService pragaService;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private MensagemService mensagemService;

    //cliente
    @RequestMapping("/nova/proposta")
    public ModelAndView goNovaProposta(){
        Map<String, Object> model = new HashMap<String, Object>();
        Usuario usuario = null;
        Set enderecos = null;

        List pragas = pragaService.findAll();

        SecurityContext context = SecurityContextHolder.getContext();
        if(context instanceof SecurityContext)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication)
            {
                usuario = usuarioService.findByEmail(authentication.getName());
                enderecos = usuario.getEnderecos();
            }
        }

        model.put("pragas", pragas);
        model.put("enderecos", enderecos);

        return new ModelAndView("/outros/nova_proposta", model);
    }

    //cliente
    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    public String registrarProposta(@RequestParam String endereco,
                                    @RequestParam String freq,
                                    @RequestParam String qtd,
                                    @RequestParam String tipo,
                                    @RequestParam String praga,
                                    @RequestParam String descricao){

        Usuario usuario = null;
        Set<Endereco> enderecos = null;
        Set<Praga> pragas = new HashSet<Praga>();
        Proposta proposta = new Proposta();

        SecurityContext context = SecurityContextHolder.getContext();
        if(context instanceof SecurityContext)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication)
            {
                usuario = usuarioService.findByEmail(authentication.getName());
                enderecos = usuario.getEnderecos();
            }
        }

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(String.format("Nova proposta pelo usuário: [%s]", usuario.getEmail()));
        }


        proposta.setQuantidade(Integer.parseInt(qtd));
        proposta.setTipo(Tipo.valueOf(tipo));
        proposta.setDescricao(descricao);
        proposta.setFrequencia(Frequencia.valueOf(freq));
        proposta.setStatus(StatusProposta.STATUS_PROPOSTA_PENDENTE);


        if(!tipo.equals("TIPO_PREVENCAO")){
            String array_pragas[] = praga.split(",");

            for(int i = 0; i < array_pragas.length; i++){
                if(!array_pragas[i].equals("0")){
                    pragas.add(pragaService.findById(Long.parseLong(array_pragas[i])));
                }
            }

            proposta.setPragas(pragas);
        }


        for(Endereco end : enderecos){
            if(end.getId() == Long.parseLong(endereco)){
                proposta.setEndereco(end);
            }
        }

        proposta.setUsuario(usuario);

        try{
            propostaService.create(proposta);
        }catch (Exception e){

        }

        return "redirect:/usuario/painel";
    }

    @RequestMapping("/visualizar")
    public ModelAndView goProposta(){
        Usuario usuario = null;
        List propostas;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context instanceof SecurityContext)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication)
            {
                usuario = usuarioService.findByEmail(authentication.getName());
            }
        }
        if(usuario != null){
            //propostas = propostaService.findByUsuario(usuario);
        }

        propostas = propostaService.findAll();

        return new ModelAndView("/proposta/proposta", "propostas", propostas);
    }

    @RequestMapping(value = "/visualizar/negociacao", method = RequestMethod.POST)
    public ModelAndView goNegociacao(@RequestParam String id){
        Map<String, Object> model = new HashMap<String, Object>();

        Proposta proposta = propostaService.findById(Long.parseLong(id));

        model.put("proposta", proposta);

        return new ModelAndView("/proposta/negociacao", model);
    }
}
