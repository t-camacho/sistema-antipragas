jQuery(function(){
    var idProposta = jQuery('.id_proposta').val();

    function verifica() {
        var manipulador;
        jQuery.ajax({
            url: 'http://localhost:8080/proposta/stream',
            type: 'GET',
            data: {idProposta: idProposta},
            success: function (obj) {
                var retorno = JSON.parse(obj);
                clearInterval(manipulador);
                if(retorno.status == 'cancelada'){
                    $(location).attr('href', 'http://localhost:8080/proposta/visualizar?cancelada2');
                }
                if(retorno.status == 'alterada' || retorno.status == 'nao_alterada'){
                    manipulador = setTimeout(function () {
                        verifica();
                    },100);
                    if(retorno.status == 'alterada'){
                        jQuery.each(retorno.servicos, function (i, servico) {
                            $('.orcamentoProp').html('<div style="float: right" th:class="orcamentoProp">Orçamento: R$ '+ retorno.proposta.orcamento+'</div>');
                            $('.funcionario'+servico.id).html('<span style="font-weight: bold">Funcionário Técnico: </span>'+ servico.funcionarioTecnico.nome +'<br />\n');
                            $('.orcamento'+servico.id).html('Orçamento: R$ '+ servico.orcamento);
                            $('.descricao'+servico.id).html('<span style="font-weight: bold">Descricão: </span>'+ servico.descricao +'<br />');
                            $('.data'+servico.id).html('<span style="font-weight: bold" >Data e Horário: </span>'+servico.dataHorario+'<br />');
                        });
                    }
                }
            },
            error: function () {
                clearInterval(manipulador);
                manipulador = setTimeout(function () {
                    verifica();
                },15000);
            }
        })
    }

    verifica();
});