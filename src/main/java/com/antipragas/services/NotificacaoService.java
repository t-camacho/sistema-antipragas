package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;
import com.antipragas.models.Usuario;

/**
 * @author Thais Camacho
 */

public interface NotificacaoService {
    public void sendNotification(Usuario usuario, ChaveDeConfirmacao encodeId);
}
