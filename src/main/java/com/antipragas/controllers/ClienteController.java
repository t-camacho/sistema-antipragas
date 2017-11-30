package com.antipragas.controllers;

import com.antipragas.models.Servico;
import com.antipragas.models.ServicoPrototype;
import com.antipragas.models.Usuario;
import com.antipragas.repositories.ServicoRepository;
import com.antipragas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.*;

/*
    @author Rafael
 */

@Controller
@RequestMapping("/usuario")
public class ClienteController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/cliente/home")
    public String goPaginaInicial(Model model) {
        Usuario clienteLogado = getUsuarioLogado();
        model.addAttribute("cliente", clienteLogado);
        model.addAttribute("servicosDoDia", getServicosDoDia(clienteLogado));
        model.addAttribute("servicosDaSemana", getServicosDaSemana(clienteLogado));
        return "/usuario/cliente/home";
    }

    public List<Servico> getServicosDaSemana(Usuario cliente) {
        Calendar hoje = null, inicioDestaSemana = null, inicioProximaSemana = null;
        Date dataInicio = null, dataLimite = null;
        int offset = 0;
        List<Servico> servicosDaSemana = null, servicosDoCliente = null;

        hoje = Calendar.getInstance();

        // determina quando começa a próxima semana
        offset = Calendar.SATURDAY - hoje.get(Calendar.DAY_OF_WEEK) + 1;
        inicioProximaSemana = Calendar.getInstance();
        inicioProximaSemana.add(Calendar.DATE, offset);
        inicioProximaSemana.set(Calendar.HOUR, -12);
        inicioProximaSemana.set(Calendar.MINUTE, 0);
        inicioProximaSemana.set(Calendar.SECOND, 0);
        dataLimite = inicioProximaSemana.getTime();

        // determina o começo da semana
        inicioDestaSemana = Calendar.getInstance();
        inicioDestaSemana.setTime(dataLimite);
        inicioDestaSemana.add(Calendar.DATE, -7);
        dataInicio = inicioDestaSemana.getTime();

        // procura quais serviços do cliente estão agendados para a semana
        servicosDaSemana = new ArrayList<Servico>();
        servicosDoCliente = servicoRepository.findByCliente(cliente);
        ordenarServicosPorData(servicosDoCliente);

        for(Servico servico : servicosDoCliente) {
            if (servico.getDataHorario().after(dataInicio) && servico.getDataHorario().before(dataLimite)) {
                servicosDaSemana.add(servico);
            }
        }
        return servicosDaSemana;
    }

    public List<Servico> getServicosDoDia(Usuario cliente) {
        Calendar inicioDoDia = null, hoje = null, amanha = null;
        Date dataInicio = null, dataLimite = null;
        List<Servico> servicosDoDia = null, servicosDoCliente = null;

        hoje = Calendar.getInstance();

        // determina o início do dia de amanhã
        amanha = Calendar.getInstance();
        amanha.add(Calendar.DATE, 1);
        amanha.set(Calendar.HOUR, -12);
        amanha.set(Calendar.MINUTE, 0);
        amanha.set(Calendar.SECOND, 0);
        dataLimite = amanha.getTime();

        // determina o início do dia
        inicioDoDia = Calendar.getInstance();
        inicioDoDia.setTime(amanha.getTime());
        inicioDoDia.add(Calendar.DATE, -1);
        dataInicio = inicioDoDia.getTime();

        // verifica quais servicos do cliente são de hoje
        servicosDoDia = new ArrayList<Servico>();
        servicosDoCliente = servicoRepository.findByCliente(cliente);
        ordenarServicosPorData(servicosDoCliente);

        for (Servico servico : servicosDoCliente) {
            if (servico.getDataHorario().after(dataInicio) && servico.getDataHorario().before(dataLimite)) {
                servicosDoDia.add(servico);
            }
        }
        return servicosDoDia;
    }

    public void ordenarServicosPorData(List<Servico> servicos) {
        Collections.sort(servicos, (s1, s2) -> {
            Date data1 = s1.getDataHorario();
            Date data2 = s2.getDataHorario();
            return data1.compareTo(data2);
        });
    }

    public Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return (usuarioRepository.findByEmail(email));
    }

    @GetMapping("/cliente/edit")
    public String goPaginaEdit(Model model) {
        Usuario clienteLogado = getUsuarioLogado();
        model.addAttribute("cliente", clienteLogado);
        return "/usuario/cliente/edit";
    }
}
