package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;

public interface ChaveDeConfirmacaoService {
    ChaveDeConfirmacao create(ChaveDeConfirmacao encodeId);
    ChaveDeConfirmacao findByIdEncode(String id);
}
