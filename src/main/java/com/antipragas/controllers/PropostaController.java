package com.antipragas.controllers;

import com.antipragas.models.*;
import com.antipragas.models.enums.Frequencia;
import com.antipragas.models.enums.StatusProposta;
import com.antipragas.models.enums.Tipo;
import com.antipragas.models.json.PropostaJson;
import com.antipragas.services.MensagemService;
import com.antipragas.services.PragaService;
import com.antipragas.services.PropostaService;
import com.antipragas.services.UsuarioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private Usuario getUsuarioSession(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(context instanceof SecurityContext)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication)
            {
                return usuarioService.findByEmail(authentication.getName());
            }
        }
        return null;
    }


    //cliente
    @RequestMapping("/nova/proposta")
    public ModelAndView goNovaProposta(){
        Map<String, Object> model = new HashMap<String, Object>();
        Usuario usuario;
        Set enderecos;

        List pragas = pragaService.findAll();


        usuario = getUsuarioSession();
        if(usuario != null){
            enderecos = usuario.getEnderecos();

            model.put("pragas", pragas);
            model.put("enderecos", enderecos);
        }

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

        usuario = getUsuarioSession();
        if(usuario != null){
            enderecos = usuario.getEnderecos();

            if(LOGGER.isInfoEnabled()){
                LOGGER.info(String.format("Nova proposta pelo usuário: [%s]", usuario.getEmail()));
            }
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
        List propostas = null;
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
            propostas = propostaService.findByUsuario(usuario);
        }

        return new ModelAndView("/proposta/proposta", "propostas", propostas);
    }

    @RequestMapping(value = "/carregar", method = RequestMethod.GET)
    public @ResponseBody
    String carregarDemanda(@RequestParam(value = "inicio", required=true) Long inicio,
                           @RequestParam(value = "qtd", required=true) Long qtd,
                           @RequestParam(value = "categoria", required=true) Integer categoria){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Usuario usuario = getUsuarioSession();
        List<Proposta> propostasPaginada = new ArrayList<Proposta>();
        List<Proposta> propostas;
        PropostaJson resultado;
        int conta = 0;
        Long ultimoId = inicio;

        switch (categoria){
            case 0://category = aprovada
                propostas = propostaService.findByUsuarioAndIdGreaterThanAndStatus(usuario, inicio,
                        StatusProposta.STATUS_PROPOSTA_APROVADA);
                break;
            case 1://category = cancelada
                propostas = propostaService.findByUsuarioAndIdGreaterThanAndStatus(usuario, inicio,
                        StatusProposta.STATUS_PROPOSTA_CANCELADA);
                break;
            case 2://category = pedente
                propostas = propostaService.findByUsuarioAndIdGreaterThanAndStatus(usuario, inicio,
                        StatusProposta.STATUS_PROPOSTA_PENDENTE);
                break;
            case 3://category = deliberada
                propostas = propostaService.findByUsuarioAndIdGreaterThanAndStatus(usuario, inicio,
                        StatusProposta.STATUS_PROPOSTA_DELIBERADA);
                break;
            default: //category = todas
                propostas = propostaService.findByUsuarioAndIdGreaterThan(usuario, inicio);
                break;
        }

        for(Proposta proposta : propostas){
            if(conta >= qtd){
                break;
            }
            propostasPaginada.add(proposta);
            ultimoId = proposta.getId();
            conta++;
        }

        resultado = new PropostaJson(ultimoId, propostasPaginada);

        return gson.toJson(resultado);
    }

    @RequestMapping(value = "/visualizar/negociacao", method = RequestMethod.POST)
    public ModelAndView goNegociacao(@RequestParam String id){
        Map<String, Object> model = new HashMap<String, Object>();

        Proposta proposta = propostaService.findById(Long.parseLong(id));
        model.put("proposta", proposta);

        return new ModelAndView("/proposta/negociacao", model);
    }
}
