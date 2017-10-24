package com.antipragas.controllers;

import com.antipragas.models.Endereco;
import com.antipragas.models.Fornecedor;
import com.antipragas.models.Servico;
import com.antipragas.models.Usuario;
import com.antipragas.models.enums.StatusServico;
import com.antipragas.repositories.FornecedorRepository;
import com.antipragas.repositories.ServicoRepository;
import com.antipragas.repositories.UsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ricardo Henrique Brunetto
 * 12 de Outubro de 2017
 */
@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private Logger LOGGER = Logger.getLogger(ServicoController.class);

    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/listar")
    public String listar(Model model) {
        //model.addAttribute("fornecedores", fornecedorRepository.findAll());
        return "/servicos/listar";
    }

    @RequestMapping(value = "/listar/Pendente", method = RequestMethod.GET)
        public String listarServicosPendentes(Model model, final HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String auth_name = auth.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(auth_name);

        Usuario usuario = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Error"));

        StatusServico st = StatusServico.PENDENTE;

        List<Servico> servicos = servicoRepository.findByClienteAndStatus(usuario, StatusServico.PENDENTE);

        model.addAttribute("resp", st.getStatus());

        model.addAttribute("servicos_pendentes", servicos);
        return "/servicos/listar/Pendente";
    }

    @RequestMapping(value = "/listar/Concluido", method = RequestMethod.GET)
    public String listarServicosConcluidos(Model model, final HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String auth_name = auth.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(auth_name);

        Usuario usuario = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Error"));

        StatusServico st = StatusServico.CONCLUIDO;

        List<Servico> servicos = servicoRepository.findByClienteAndStatus(usuario, st);

        model.addAttribute("resp", st.getStatus());

        model.addAttribute("servicos_concluidos", servicos);

        return "/servicos/listar/Concluido";
    }

    @RequestMapping(value = "/listar/Cancelado", method = RequestMethod.GET)
    public String listarServicosCancelados(Model model, final HttpServletRequest request){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String auth_name = auth.getName();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(auth_name);

        Usuario usuario = usuarioOpt.orElseThrow(() -> new UsernameNotFoundException("Error"));

        StatusServico st = StatusServico.CANCELADO;

        List<Servico> servicos = servicoRepository.findByClienteAndStatus(usuario, st);

        model.addAttribute("resp", st.getStatus());

         model.addAttribute("servicos_cancelados", servicos);
        return "/servicos/listar/Cancelado";
    }
}