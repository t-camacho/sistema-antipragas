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
public class NoticaficacaoServiceImple implements NotificacaoService {
    private JavaMailSender javaMailSender;

    @Autowired
    public NoticaficacaoServiceImple(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendNotification(Usuario usuario, ChaveDeConfirmacao chaveCrip) {
        String msg = "clique no link para ativar sua conta http://localhost:8080/usuario/confirmar?id=" + chaveCrip.getIdCriptografado();

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(usuario.getEmail());
        mail.setFrom("empresaantipragas@gmail.com");
        mail.setSubject("Confirme seu Cadastro na Empresa Antipragas");
        mail.setText(msg);
        javaMailSender.send(mail);
    }

}
