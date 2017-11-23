package com.antipragas.repositories;

import com.antipragas.models.Mensagem;
import com.antipragas.models.Proposta;
import com.antipragas.models.enums.StatusMensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Thais Camacho
 */
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByIdDeAndIdPara(Long idUsuario, Long idFuncionario);
    List<Mensagem> findByProposta(Proposta proposta);
    List<Mensagem> findByStatusAndProposta(StatusMensagem status, Proposta proposta);
    List<Mensagem> findByHorarioGreaterThanAndStatusAndProposta(Timestamp horario, StatusMensagem status, Proposta proposta);
}