package com.antipragas.services;

import com.antipragas.models.Mensagem;
import com.antipragas.models.Proposta;
import com.antipragas.models.enums.StatusMensagem;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Thais Camacho
 */

public interface MensagemService {
    List<Mensagem> findAll();
    Mensagem findById(Long id);
    Mensagem create(Mensagem msg);
    Mensagem edit(Mensagem msg);
    void deleteById(Long id);
    List<Mensagem> findByIdDeAndIdPara(Long idUsuario, Long idFuncionario);
    List<Mensagem> findByProposta(Proposta proposta);
    List<Mensagem> findByStatusAndProposta(StatusMensagem status, Proposta proposta);
    List<Mensagem> findByHorarioGreaterThanAndStatusAndProposta(Timestamp horario, StatusMensagem status, Proposta proposta);
}
