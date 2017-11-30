package com.antipragas.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ricardo Henrique Brunetto
 * 21 de Novembro de 2017
 */
public class Filtro {
    boolean ativo;
    String cpf, nomeFuncionario;

    public Filtro(String cpf, String nomeFuncionario) {
        if(cpf.isEmpty() && nomeFuncionario.isEmpty())
            ativo = false;
        else {
            this.cpf = cpf;
            this.nomeFuncionario = nomeFuncionario;
            this.ativo = true;
        }
    }

    public List<Proposta> aplicarFiltro(List<Proposta> propostas){
        if(!this.ativo) return propostas;
        List<Proposta> propostas_filtradas = new ArrayList<>();

        for(Proposta p : propostas){
            if(!this.cpf.isEmpty())
                if(p.getUsuario().getCPF().contains(this.cpf)) propostas_filtradas.add(p);
            if(!this.nomeFuncionario.isEmpty())
                if(p.getFuncionario().getNome().contains(this.nomeFuncionario)) propostas_filtradas.add(p);
        }
        return propostas_filtradas;
    }
}
