package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;

/**
 * @author Thais Camacho
 */


public interface ChaveDeConfirmacaoService {
    ChaveDeConfirmacao create(ChaveDeConfirmacao encodeId);
    ChaveDeConfirmacao findByIdCriptografado(String id);
}
