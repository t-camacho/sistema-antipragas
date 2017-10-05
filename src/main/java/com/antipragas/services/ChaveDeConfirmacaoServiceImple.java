package com.antipragas.services;

import com.antipragas.models.ChaveDeConfirmacao;
import com.antipragas.repositories.ChaveDeConfirmacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author Thais Camacho
 */

@Service
@Primary
public class ChaveDeConfirmacaoServiceImple implements ChaveDeConfirmacaoService {
    @Autowired
    private ChaveDeConfirmacaoRepository chaveDeConfirmacaoRepository;

    @Override
    public ChaveDeConfirmacao create(ChaveDeConfirmacao encodeId) {

        return this.chaveDeConfirmacaoRepository.save(encodeId);

    }

    @Override
    public ChaveDeConfirmacao findByIdEncode(String id) {

        return chaveDeConfirmacaoRepository.findByIdCriptografado(id);
    }
}
