package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;
import com.antipragas.models.Usuario;

/**
 * @author Thais Camacho
 */

public interface NotificacaoService {
    public void enviarNotificacaoDeCadastro(Usuario usuario, ChaveDeConfirmacao chaveCrip);
    public void enviarNotificacaoDeSenha(Usuario usuario, ChaveDeConfirmacao chaveCrip);
}
