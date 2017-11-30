package com.antipragas.controllers;

import com.antipragas.models.*;
import com.antipragas.models.enums.*;
import com.antipragas.models.json.PropostaAlteradaJson;
import com.antipragas.models.json.PropostaJson;
import com.antipragas.services.*;
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

import java.sql.Timestamp;
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
    private FuncionarioTecnicoService funcionarioTecnicoService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private ServicoPrototypeService servicoPrototypeService;

    private Usuario getUsuarioSession(){
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication != null)
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
        Usuario usuario;
        Set<Endereco> enderecos = null;
        Set<Praga> pragas = new HashSet<Praga>();

        try{
            Proposta proposta = new Proposta();
            usuario = getUsuarioSession();
            if(LOGGER.isInfoEnabled()){
                assert usuario != null;
                LOGGER.info(String.format("Nova proposta pelo usuário: [%s]", usuario.getEmail()));
            }
            enderecos = usuario.getEnderecos();

            proposta.setQuantidade(Integer.parseInt(qtd));
            proposta.setTipo(Tipo.valueOf(tipo));
            proposta.setDescricao(descricao);
            proposta.setFrequencia(Frequencia.valueOf(freq));
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_EM_ABERTO);
            proposta.setOrcamento(0.0);
            proposta.setCanceladoPor(Cancelado.CANCELADO_NINGUEM);
            proposta.setAlterada(Alterada.ALTERADA_FALSE);
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
            System.out.println("aquiii1");

            proposta.setUsuario(usuario);
            System.out.println("anteste");
            propostaService.create(proposta);

            return "redirect:/usuario/painel?nova_proposta";
        }catch (Exception e){
            System.out.println(e);
            return "redirect:/usuario/painel?nova_error";
        }
    }

    //cliente e funcionario
    @RequestMapping("/visualizar")
    public String goProposta(){
        return "/proposta/proposta";
    }

    //aceitar prop
    @RequestMapping(value = "/aceitar", method = RequestMethod.GET)
    public String aceitarPropostaEmAberto(@RequestParam String id){
        try{
            Proposta proposta = propostaService.findById(Long.parseLong(id));
            proposta.setFuncionario(getUsuarioSession());
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_PENDENTE);
            propostaService.edit(proposta);
        }catch (Exception e){
            return "redirect:/proposta/abertas?error";
        }
        return "redirect:/proposta/abertas?aceita";
    }

    private void gerarServico(){

    }

    @RequestMapping(value = "/aceitarc", method = RequestMethod.POST)
    public String aceitarPropostaCliente(@RequestParam String id){
        try{
            Proposta proposta = propostaService.findById(Long.parseLong(id));
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_APROVADA);
            propostaService.edit(proposta);
        }catch (Exception e){
            return "redirect:/proposta/visualizar?error";
        }
        return "redirect:/proposta/visualizar?aceita";
    }

    @RequestMapping(value = "/aceitarf", method = RequestMethod.POST)
    public String aceitarPropostaFuncionario(@RequestParam String id){
        try{
            Proposta proposta = propostaService.findById(Long.parseLong(id));
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_DELIBERADA);
            propostaService.edit(proposta);
        }catch (Exception e){
            return "redirect:/proposta/visualizar?error";
        }
        return "redirect:/proposta/visualizar?aceitaf";
    }

    @RequestMapping(value = "/cancelarc", method = RequestMethod.POST)
    public String cancelarPropostaCliente(@RequestParam String id){
        try{
            Proposta proposta = propostaService.findById(Long.parseLong(id));
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_CANCELADA);
            proposta.setCanceladoPor(Cancelado.CANCELADO_PELO_CLIENTE);
            propostaService.edit(proposta);
        }catch (Exception e){
            return "redirect:/proposta/visualizar?error";
        }
        return "redirect:/proposta/visualizar?cancelada";
    }

    @RequestMapping(value = "/cancelarf", method = RequestMethod.POST)
    public String cancelarPropostaFuncionario(@RequestParam String id){
        try{
            Proposta proposta = propostaService.findById(Long.parseLong(id));
            proposta.setStatus(StatusProposta.STATUS_PROPOSTA_CANCELADA);
            proposta.setCanceladoPor(Cancelado.CANCELADO_PELA_EMPRESA);
            propostaService.edit(proposta);
        }catch (Exception e){
            return "redirect:/proposta/visualizar?error";
        }
        return "redirect:/proposta/visualizar?cancelada";
    }

    //funcionario
    @RequestMapping("/abertas")
    public String goPropostasAbertas(){
        return ("/proposta/em_aberto");
    }

    //funcionario
    @RequestMapping("/pabertas")
    public @ResponseBody String propostasAbertas(){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        List<Proposta> propostas = propostaService.findByStatus(StatusProposta.STATUS_PROPOSTA_EM_ABERTO);

        return gson.toJson(propostas);
    }

    //cliente, funcionario e administrador
    private List<Proposta> selecionarPropostas(Usuario usuario, Long inicio, int categoria){
        List<Proposta> propostas;
        if(usuario.getNivel() == Nivel.NIVEL_CLIENTE){
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
                case 4://category = em_aberto
                    propostas = propostaService.findByUsuarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_EM_ABERTO);
                    break;
                default://category = todas
                    propostas = propostaService.findByUsuarioAndIdGreaterThan(usuario, inicio);
                    break;
            }
        }else if(usuario.getNivel() == Nivel.NIVEL_FUNCIONARIO){
            switch (categoria){
                case 0://category = aprovada
                    propostas = propostaService.findByFuncionarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_APROVADA);
                    break;
                case 1://category = cancelada
                    propostas = propostaService.findByFuncionarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_CANCELADA);
                    break;
                case 2://category = pedente
                    propostas = propostaService.findByFuncionarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_PENDENTE);
                    break;
                case 3://category = deliberada
                    propostas = propostaService.findByFuncionarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_DELIBERADA);
                    break;
                case 4://category = em_aberto
                    propostas = propostaService.findByFuncionarioAndIdGreaterThanAndStatus(usuario, inicio,
                            StatusProposta.STATUS_PROPOSTA_EM_ABERTO);
                    break;
                default://category = todas
                    propostas = propostaService.findByFuncionarioAndIdGreaterThan(usuario, inicio);
                    break;
            }
        }else{
            switch (categoria){
                case 0://category = aprovada
                    propostas = propostaService.findByIdGreaterThanAndStatus(inicio, StatusProposta.STATUS_PROPOSTA_APROVADA);
                    break;
                case 1://category = cancelada
                    propostas = propostaService.findByIdGreaterThanAndStatus(inicio, StatusProposta.STATUS_PROPOSTA_CANCELADA);
                    break;
                case 2://category = pedente
                    propostas = propostaService.findByIdGreaterThanAndStatus(inicio, StatusProposta.STATUS_PROPOSTA_PENDENTE);
                    break;
                case 3://category = deliberada
                    propostas = propostaService.findByIdGreaterThanAndStatus(inicio, StatusProposta.STATUS_PROPOSTA_DELIBERADA);
                    break;
                case 4://category = em_aberto
                    propostas = propostaService.findByIdGreaterThanAndStatus(inicio, StatusProposta.STATUS_PROPOSTA_EM_ABERTO);
                    break;
                default://category = todas
                    propostas = propostaService.findByIdGreaterThan(inicio);
                    break;
            }
        }
        return propostas;
    }

    //cliente e funcionario
    @RequestMapping(value = "/carregar", method = RequestMethod.GET)
    public @ResponseBody
    String carregarDemanda(@RequestParam(value = "inicio", required=true) Long inicio,
                           @RequestParam(value = "qtd", required=true) Long qtd,
                           @RequestParam(value = "categoria", required=true) Integer categoria,
                           @RequestParam(value = "cpf", required = false) String searchCPF,
                           @RequestParam(value = "funcionario", required = false) String searchFuncionario){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Usuario usuario = getUsuarioSession();
        List<Proposta> propostasPaginada = new ArrayList<Proposta>();
        List<Proposta> propostas;
        PropostaJson resultado;
        int conta = 0;
        Long ultimoId = inicio;

        Filtro filtro = new Filtro(searchCPF, searchFuncionario);
        LOGGER.info("FILTRO: " + searchCPF + " | " + searchFuncionario);
        propostas = filtro.aplicarFiltro(selecionarPropostas(usuario, inicio, categoria));

        for(Proposta proposta : propostas){
            if(conta >= qtd){
                break;
            }
//            for(Proposta p : propostas){
                LOGGER.info("PROPOSTA CARREGADA: " + proposta.getId());
//            }
            propostasPaginada.add(proposta);
            ultimoId = proposta.getId();
            conta++;
        }
        resultado = new PropostaJson(ultimoId, propostasPaginada);
        return gson.toJson(resultado);
    }

    private StringBuilder preparaPraga(Proposta proposta){
        StringBuilder pragasS = new StringBuilder();

        int i = 0;
        for(Praga p : proposta.getPragas()){
            if(i+1 < proposta.getPragas().size()){
                pragasS.append(p.getNome()).append(", ");
            }else{
                pragasS.append(p.getNome());
            }
            i++;
        }
        if(pragasS.length() == 0){
            pragasS.append("Não definido");
        }
        return pragasS;
    }

    //cliente e funcionário
    @RequestMapping(value = "/negociacao", method = RequestMethod.GET)
    public ModelAndView goNegociacao(@RequestParam String id){
        int quantidade;
        Map<String, Object> model = new HashMap<String, Object>();
        List<ServicoPrototype> preServicos;

        Proposta proposta = propostaService.findById(Long.parseLong(id));

        if(proposta != null){
            quantidade = proposta.getQuantidade();
            preServicos = servicoPrototypeService.findByProposta(proposta);

            if(preServicos.isEmpty()){
                Servico s;
                s = new Servico(proposta, proposta.getUsuario(), proposta.getDescricao(), StatusServico.PRE_SERVICO, proposta.getEndereco());
                while(quantidade > 0){
                    ServicoPrototype servicoN = s.clonar();
                    servicoPrototypeService.create(servicoN);
                    quantidade--;
                }

                preServicos = servicoPrototypeService.findByProposta(proposta);
            }

            StringBuilder pragasS = preparaPraga(proposta);

            model.put("proposta", proposta);
            model.put("preServicos", preServicos);
            model.put("pragas", pragasS);

            return new ModelAndView("/proposta/negociacao", model);
        }
        return new ModelAndView("redirect:/proposta/visualizar", model);
    }

    //editar um pré-servico, apenas funcionário
    @RequestMapping(value = "/preservico", method = RequestMethod.GET)
    public ModelAndView goEditarPreServico(@RequestParam String id){
        Map<String, Object> model = new HashMap<String, Object>();
        List<FuncionarioTecnico> funcionariosTecnicos = funcionarioTecnicoService.findAll();
        Servico servico = servicoService.findById(Long.parseLong(id));

        model.put("servico", servico);
        model.put("funcionariosTecnicos", funcionariosTecnicos);

        return new ModelAndView("/proposta/editar_pre_servico", model);
    }

    //confirmar alteração
    @RequestMapping(value = "/cpreservico", method = RequestMethod.POST)
    public String editarPreServico(@RequestParam( required=false ) Double orcamento,
                                    @RequestParam( required=false ) Long funcionario,
                                    @RequestParam( required=false ) String data,
                                    @RequestParam( required=false ) String horario,
                                    @RequestParam( required=false ) String descricao,
                                    @RequestParam Long id,
                                    @RequestParam( required=false ) String acao){
        FuncionarioTecnico funcionarioTecnico = funcionarioTecnicoService.findById(funcionario);
        Servico servico = servicoService.findById(id);
        if(acao.equals("confirmar")){
            try{
                Timestamp h;

                try{
                    h = Timestamp.valueOf(data+" "+horario+":00");
                }catch (Exception e){
                    h = Timestamp.valueOf(data+" "+horario);
                }
                double orcamentoAntigo = servico.getOrcamento();
                servico.setOrcamento(orcamento);
                servico.setFuncionarioTecnico(funcionarioTecnico);
                servico.setDescricao(descricao);
                servico.setDataHorario(h);

                Proposta p = servico.getProposta();
                p.setOrcamento(p.getOrcamento()+orcamento-orcamentoAntigo);
                p.setAlterada(Alterada.ALTERADA_TRUE);

                propostaService.edit(p);
                servicoService.edit(servico);

                return "redirect:/proposta/negociacao?alterada&id="+servico.getProposta().getId();
            }catch (Exception e){
                return "redirect:/proposta/negociacao?error";
            }
        }else{
            return "redirect:/proposta/negociacao?id="+servico.getProposta().getId();
        }
    }

    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    private @ResponseBody String stream(@RequestParam(value = "idProposta", required=true)
                                                    Long idProposta){
        PropostaAlteradaJson resultado;
        List<ServicoPrototype> servicos;
        int tempoGasto = 0;
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Proposta proposta = propostaService.findById(idProposta);

        if(getUsuarioSession().getNivel() != Nivel.NIVEL_CLIENTE){
            resultado = new PropostaAlteradaJson("stop");
            return gson.toJson(resultado);
        }

        if(proposta.getStatus() == StatusProposta.STATUS_PROPOSTA_CANCELADA){
            resultado = new PropostaAlteradaJson("cancelada");
            return gson.toJson(resultado);
        }

        if(proposta.getAlterada() == Alterada.ALTERADA_TRUE){
            servicos = servicoPrototypeService.findByProposta(proposta);
            resultado = new PropostaAlteradaJson("alterada", proposta, servicos);
            proposta.setAlterada(Alterada.ALTERADA_FALSE);
            propostaService.edit(proposta);
            return gson.toJson(resultado);
        }else{
            proposta = propostaService.findById(idProposta);
            while(proposta.getAlterada() == Alterada.ALTERADA_FALSE){
                if(tempoGasto >= 5){
                    resultado = new PropostaAlteradaJson("nao_alterada");
                    return gson.toJson(resultado);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                tempoGasto++;
                proposta = propostaService.findById(idProposta);
            }
        }

        servicos = servicoPrototypeService.findByProposta(proposta);
        resultado = new PropostaAlteradaJson("alterada", proposta, servicos);
        proposta.setAlterada(Alterada.ALTERADA_FALSE);
        propostaService.edit(proposta);
        return gson.toJson(resultado);
    }
}
