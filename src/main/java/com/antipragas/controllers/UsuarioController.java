package com.antipragas.controllers;

import com.antipragas.models.*;
import com.antipragas.models.enums.Acao;
import com.antipragas.models.enums.Nivel;
import com.antipragas.models.enums.Sexo;
import com.antipragas.models.enums.Status;
import com.antipragas.services.ChaveDeConfirmacaoService;
import com.antipragas.services.NotificacaoServiceImple;
import com.antipragas.services.UsuarioService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private Logger LOGGER = Logger.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChaveDeConfirmacaoService chaveDeConfirmacaoService;

    @Autowired
    private NotificacaoServiceImple notificacaoService;

    @RequestMapping(value ="/registrar", method = RequestMethod.POST)
    public ModelAndView registrarUsuario(@RequestParam String nome, @RequestParam String email,
                                     @RequestParam String dnascimento,
                                     @RequestParam String cpf, @RequestParam String sexo, @RequestParam String senha,
                                     @RequestParam String telefone, @RequestParam String cell,
                                     @RequestParam String endereco){

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(String.format("Criando um novo cadastro com email: [%s]", email));
        }

        String resp = "register";

        Usuario usuario = new Usuario(nome, email, new BCryptPasswordEncoder().encode(senha), dnascimento, Nivel.NIVEL_CLIENTE, Status.STATUS_DESATIVADA, cpf, new Telefone(telefone, cell));

        if(sexo.equals("fem")){
            usuario.setSexo(Sexo.F);
        }else{
            usuario.setSexo(Sexo.M);
        }

        Set enderecos = new HashSet<Endereco>();

        String addresses_send[] = endereco.split(",");

        for(int i = 0; i < addresses_send.length; i++){
            String campos[] = addresses_send[i].split("/");
            if(campos.length == 7){
                enderecos.add(new Endereco(campos[0], campos[1], campos[3], campos[4], Integer.parseInt(campos[2]), campos[6], campos[5], usuario));
            }else{
                enderecos.add(new Endereco(campos[0], campos[1], campos[3], campos[4], Integer.parseInt(campos[2]), "", campos[5], usuario));
            }
            usuario.setEnderecos(enderecos);
        }

        if(usuarioService.findByEmail(email) == null){
            usuarioService.create(usuario);
            ChaveDeConfirmacao chaveCrip= new ChaveDeConfirmacao(usuario.getId(), new BCryptPasswordEncoder().encode(usuario.getId().toString()), Acao.ACAO_CONFIRMAR);
            chaveDeConfirmacaoService.create(chaveCrip);
            try{
                notificacaoService.enviarNotificacaoDeCadastro(usuario, chaveCrip);
            }catch (MailException e){
            }
        }else{
            resp = "exists";
        }

        return new ModelAndView("redirect:/registrar", "resp", resp);
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/confirmar", method = RequestMethod.GET)
    public ModelAndView confirmarRegistro(@RequestParam String id){

        ChaveDeConfirmacao chaveCrip = chaveDeConfirmacaoService.findByIdCriptografado(id);

        if(chaveCrip != null && chaveCrip.getAcao() != Acao.ACAO_RESETAR){
            Usuario usuario = usuarioService.findById(chaveCrip.getIdUsuario());

            if(LOGGER.isInfoEnabled()){
                LOGGER.info(String.format("Confirmação realizada para o email: [%s]", usuario.getEmail()));
            }

            usuario.setStatus(Status.STATUS_ATIVADA);
            usuarioService.edit(usuario);
        }

        return new ModelAndView("redirect:/registrar", "resp", "confirm");
    }

    @RequestMapping(value = "/resetar/novasenha", method = RequestMethod.GET)
    public ModelAndView resetarNovaSenha(@RequestParam String id){

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(String.format("Acesso ao link de resetar senha pelo usuário: [%s]", id));
        }

        ChaveDeConfirmacao chaveCrip = chaveDeConfirmacaoService.findByIdCriptografado(id);

        if(chaveCrip != null && chaveCrip.getAcao() != Acao.ACAO_CONFIRMAR){
            Usuario usuario = usuarioService.findById(chaveCrip.getIdUsuario());

            return new ModelAndView("resetar", "usuario", usuario.getId());
        }
        return new ModelAndView("resetar", "resp", "error");
    }

    @RequestMapping(value = "/resetar/novasenha", method = RequestMethod.POST)
    public ModelAndView alterarSenha(@RequestParam String id, @RequestParam String senha){

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(String.format("Alterando para a senha nova o usuário de id: [%s]", id));
        }

        Usuario usuario = usuarioService.findById(Long.parseLong(id));

        usuario.setSenha(new BCryptPasswordEncoder().encode(senha));

        usuarioService.edit(usuario);

        return new ModelAndView("redirect:/registrar", "resp", "reset");
    }

    @RequestMapping(value = "/resetar", method = RequestMethod.POST)
    public ModelAndView resetarSenha(@RequestParam String emailResetarSenha){

        String resp = "concluido";

        if(LOGGER.isInfoEnabled()){
            LOGGER.info(String.format("Solicitação para resetar senha email: [%s]", emailResetarSenha));
        }

        Usuario usuario = usuarioService.findByEmail(emailResetarSenha);

        if(usuario != null){
            ChaveDeConfirmacao chaveCrip= new ChaveDeConfirmacao(usuario.getId(), new BCryptPasswordEncoder().encode(usuario.getId().toString()), Acao.ACAO_RESETAR);
            chaveDeConfirmacaoService.create(chaveCrip);
            try{
                notificacaoService.enviarNotificacaoDeSenha(usuario, chaveCrip);
            }catch (MailException e){
            }
        }

        return new ModelAndView("redirect:/resetar", "resp", resp);
    }

    @RequestMapping("/painel")
    public String goPainel(){
        return "/usuario/painel";
    }
}
