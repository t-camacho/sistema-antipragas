package com.antipragas.controllers;

import com.antipragas.models.*;
import com.antipragas.models.enums.Nivel;
import com.antipragas.models.enums.Sexo;
import com.antipragas.models.enums.Status;
import com.antipragas.services.ChaveDeConfirmacaoService;
import com.antipragas.services.NotificacaoServiceImple;
import com.antipragas.services.UsuarioService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Thais Camacho
 */

@Controller
public class UsuarioController {
    private Logger LOGGER = Logger.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChaveDeConfirmacaoService chaveDeConfirmacaoService;

    @Autowired
    private NotificacaoServiceImple notificacaoService;

    @RequestMapping(value ="/usuario/registrar", method = RequestMethod.POST)
    public ModelAndView registerUser(@RequestParam String nome, @RequestParam String email,
                                     @RequestParam String dnascimento, @RequestParam Sexo sexo,
                                     @RequestParam String cpf, @RequestParam String senha,
                                     @RequestParam String telefone, @RequestParam String cell,
                                     @RequestParam String endereco){

        if(LOGGER.isInfoEnabled()){
            LOGGER.info("creating User");
        }

        String resp = "register";

        Usuario usuario = new Usuario(nome, email, new BCryptPasswordEncoder().encode(senha), dnascimento, Nivel.NIVEL_CLIENTE, sexo, Status.STATUS_DESATIVADA, cpf, new Telefone(telefone, cell));

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
            ChaveDeConfirmacao chaveCrip= new ChaveDeConfirmacao(usuario.getId(), new BCryptPasswordEncoder().encode(usuario.getId().toString()));
            chaveDeConfirmacaoService.create(chaveCrip);
            try{
                notificacaoService.sendNotification(usuario, chaveCrip);
            }catch (MailException e){
            }
        }else{
            resp = "exists";
        }

        return new ModelAndView("redirect:/registrar", "resp", resp);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // retornar para login é uma boa prática
    }


    @RequestMapping(value = "/usuario/confirmar", method = RequestMethod.GET)
    public ModelAndView confirmRegister(@RequestParam String id){

        ChaveDeConfirmacao chaveCrip = chaveDeConfirmacaoService.findByIdEncode(id);

        if(chaveCrip != null){
            Usuario usuario = usuarioService.findById(chaveCrip.getIdUsuario());
            usuario.setStatus(Status.STATUS_ATIVADA);
            usuarioService.edit(usuario);
        }

        return new ModelAndView("redirect:/registrar", "resp", "confirm");
    }
}
