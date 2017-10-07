package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;
import com.antipragas.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Thais Camacho
 */

@Service
public class NotificacaoServiceImple implements NotificacaoService {
    private JavaMailSender javaMailSender;

    @Autowired
    public NotificacaoServiceImple(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void enviarNotificacaoDeCadastro(Usuario usuario, ChaveDeConfirmacao chaveCrip) {
        String http = "http://";
        String host = "localhost:8080";
        String controller = "/usuario/confirmar";
        String paramid = "?id=";
        String endereco = http + host + controller + paramid + chaveCrip.getIdCriptografado();
        String msg = "clique no link para ativar sua conta " + endereco;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usuario.getEmail());
        mail.setFrom("empresaantipragas@gmail.com");
        mail.setSubject("Confirme seu Cadastro na Empresa Antipragas");
        mail.setText(msg);
        javaMailSender.send(mail);
    }

    @Override
    public void enviarNotificacaoDeSenha(Usuario usuario, ChaveDeConfirmacao chaveCrip) {
        String http = "http://";
        String host = "localhost:8080";
        String controller = "/usuario/resetar/novasenha";
        String paramid = "?id=";
        String endereco = http + host + controller + paramid + chaveCrip.getIdCriptografado();

        String msg = "clique no link para escolher uma nova senha " + endereco;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usuario.getEmail());
        mail.setFrom("empresaantipragas@gmail.com");
        mail.setSubject("Solicitação de Nova Senha");
        mail.setText(msg);
        javaMailSender.send(mail);
    }
}
