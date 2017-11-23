package com.antipragas.controllers;

import com.antipragas.models.Mensagem;
import com.antipragas.models.Proposta;
import com.antipragas.models.ResultadoChat;
import com.antipragas.models.enums.StatusMensagem;
import com.antipragas.services.MensagemService;
import com.antipragas.services.PropostaService;
import com.antipragas.services.UsuarioService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author Thais Camacho
 */

@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/enviar", method = RequestMethod.GET)
    public @ResponseBody void enviarMensagem(@RequestParam(value = "mensagem", required=true) String mensagem,
                                             @RequestParam(value = "id", required=true) String id,
                                             @RequestParam(value = "id_proposta", required=true) String id_proposta/*,
                                             @RequestParam(value = "data", required=true) String data*/){
        System.out.println("olar");
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp horario = new java.sql.Timestamp(calendar.getTime().getTime());

        String array_ids[] = id.split(":");
        Proposta proposta = propostaService.findById(Long.parseLong(id_proposta));
        mensagemService.create(new Mensagem(Long.parseLong(array_ids[0]), Long.parseLong(array_ids[1]),mensagem, horario, StatusMensagem.STATUS_MENSAGEM_NAO_LIDA, proposta));

    }

    @RequestMapping(value="/historico", method = RequestMethod.GET)
    public @ResponseBody String recuperarHistorico(@RequestParam(value = "idProposta", required=true) String idProposta) {
        Proposta proposta = propostaService.findById(Long.parseLong(idProposta));

        List<Mensagem> l = mensagemService.findByProposta(proposta);

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        return gson.toJson(l);
    }

    @RequestMapping(value="/stream", method = RequestMethod.GET)
    public @ResponseBody String stream(@RequestParam(value = "timestamp", required=true) String timestamp,
                                       @RequestParam(value = "ultimoId", required=true) String ultimoId,
                                       @RequestParam(value = "idProposta", required=true) String idProposta){
        List<Mensagem> mensagens;
        int tempoGasto = 0;
        Timestamp h = null, horario;
        ResultadoChat resultado;

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Calendar calendar = Calendar.getInstance();

        if(timestamp.equals("0")){
            mensagens = mensagemService.findByStatusAndProposta(StatusMensagem.STATUS_MENSAGEM_NAO_LIDA,
                    propostaService.findById(Long.parseLong(idProposta)));
        }else{
            h = Timestamp.valueOf(timestamp);
            mensagens = mensagemService.findByHorarioGreaterThanAndStatusAndProposta(h,
                    StatusMensagem.STATUS_MENSAGEM_NAO_LIDA, propostaService.findById(Long.parseLong(idProposta)));
        }

        if(mensagens.size() <= 0){
            while(mensagens.size() <= 0) {
                System.out.println(tempoGasto);
                if(tempoGasto >= 30){
                    horario = new Timestamp(calendar.getTime().getTime());
                    resultado = new ResultadoChat("vazio", horario.toString(), ultimoId, idProposta);
                    return gson.toJson(resultado);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }

                if (!timestamp.equals("0")) {
                    mensagens = mensagemService.findByHorarioGreaterThanAndStatusAndProposta(h,
                            StatusMensagem.STATUS_MENSAGEM_NAO_LIDA, propostaService.findById(Long.parseLong(idProposta)));
                }
                tempoGasto++;
            }
        }

        horario = new Timestamp(calendar.getTime().getTime());
        resultado = new ResultadoChat("resultados", horario.toString(), ultimoId, idProposta, mensagens);

        return gson.toJson(resultado);
    }
}
