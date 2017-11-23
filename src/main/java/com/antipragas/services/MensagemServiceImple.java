package com.antipragas.services;

import com.antipragas.models.Mensagem;
import com.antipragas.models.Proposta;
import com.antipragas.models.enums.StatusMensagem;
import com.antipragas.repositories.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Thais Camacho
 */

@Service
@Primary
public class MensagemServiceImple implements MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Override
    public List<Mensagem> findAll() {
        return mensagemRepository.findAll();
    }

    @Override
    public Mensagem findById(Long id) {
        return mensagemRepository.findOne(id);
    }

    @Override
    public Mensagem create(Mensagem msg) {
        return mensagemRepository.save(msg);
    }

    @Override
    public Mensagem edit(Mensagem msg) {
        return mensagemRepository.save(msg);
    }

    @Override
    public void deleteById(Long id) {
        mensagemRepository.delete(id);
    }

    @Override
    public List<Mensagem> findByIdDeAndIdPara(Long idUsuario, Long idFuncionario) {
        return mensagemRepository.findByIdDeAndIdPara(idUsuario, idFuncionario);
    }

    @Override
    public List<Mensagem> findByProposta(Proposta proposta) {
        return mensagemRepository.findByProposta(proposta);
    }

    @Override
    public List<Mensagem> findByStatusAndProposta(StatusMensagem status, Proposta proposta) {
        return mensagemRepository.findByStatusAndProposta(status, proposta);
    }

    @Override
    public List<Mensagem> findByHorarioGreaterThanAndStatusAndProposta(Timestamp horario, StatusMensagem status, Proposta proposta) {
        return mensagemRepository.findByHorarioGreaterThanAndStatusAndProposta(horario, status, proposta);
    }
}
