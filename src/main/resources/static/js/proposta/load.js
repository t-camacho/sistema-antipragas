$(document).ready(function () {
    var ultimoId = 0;
    var selecionado = 'todas';
    var desativaScroll = false;
    //inicia com o filtro todas
    $('.categoria_lista .categoria_item[category="todas"]').addClass('selecionada');

    function getCategoryInteger(category) {
        switch (category){
            case 'aprovada':
                return 0;
            case 'cancelada':
                return 1;
            case 'pendente':
                return 2;
            case 'deliberada':
                return 3;
            case 'aberta':
                return 4;
            default://todas
                return 6;
        }
    }

    function semProposta() {
        var item = $('.item');

        if(item.length === 0){
            $('.nenhuma_proposta .aviso').html('<h1 class="aviso">Nenhuma Proposta</h1>');
            $('.nenhuma_proposta').css('display', 'block');
        }else{
            $('.nenhuma_proposta .aviso').html('<h1 class="aviso"></h1>');
            $('.nenhuma_proposta').css('display', 'none');
        }
    }

    function carregar(inicio, qtd, category) {
        jQuery.ajax({
            url: 'http://localhost:8080/proposta/carregar',
            type: 'GET',
            data: {inicio: inicio, qtd: qtd, categoria: getCategoryInteger(category)},
            success: function (resultado) {
                var propostas = JSON.parse(resultado);
                var status, btn, tipo, pragas = "";
                ultimoId = propostas.ultimoId;
                $.each(propostas.dados, function (i, proposta) {
                    pragas = "";
                    switch (proposta.status){
                        case 'STATUS_PROPOSTA_PENDENTE':
                            status = 'pendente';
                            break;
                        case 'STATUS_PROPOSTA_APROVADA':
                            status = 'aprovada';
                            break;
                        case 'STATUS_PROPOSTA_CANCELADA':
                            status = 'cancelada';
                            break;
                        case 'STATUS_PROPOSTA_DELIBERADA':
                            status = 'deliberada';
                            break;
                        case 'STATUS_PROPOSTA_EM_ABERTO':
                            status = 'aberta';
                            break;
                    }
                    var v = 0;
                    $.each(proposta.pragas, function (i, praga) {
                        if((v+1) < proposta.pragas.length){
                            pragas += (praga.nome + ", ");
                        }else{
                            pragas += (praga.nome);
                        }
                        v++;
                    });
                    if(pragas == ""){
                        pragas = "Não definido";
                    }
                    if(proposta.funcionario === undefined || status == 'cancelada' || status == 'deliberada'){
                        btn = '<button class="btn-detalhes bloqueada" type="submit" disabled="true">Detalhes</button>\n';
                    }else{
                        btn = '<button class="btn-detalhes" type="submit" >Detalhes</button>\n';
                    }
                    if(proposta.tipo === 'TIPO_EXTERMINIO'){
                        tipo = 'Extermínio';
                    }else{
                        tipo = 'Prevenção';
                    }
                    jQuery('.todas_propostas').append(
                        '<div class="item" category="'+ status +'">' +
                        '   <div class="title-item" >' +
                        '      <span class="status '+ status+'"></span>' +
                        '      <p>Orçamento: R$ '+proposta.orcamento+'</p>' +
                        '   </div>' +
                        '   <p class="subtitulo">Endereço de Realização</p>' +
                        '   <p class="informacao">'+ proposta.endereco.rua +' '+ proposta.endereco.numero +' '+ proposta.endereco.bairro +' - '+ proposta.endereco.cidade +'/'+ proposta.endereco.uf +'</p>\n' +
                        '   <p class="subtitulo">Tipo</p>' +
                        '   <p class="informacao">'+ tipo +'</p>' +
                        '   <p class="subtitulo">Praga(s)</p>' +
                        '   <p class="informacao">'+ pragas +'</p>' +
                        '   <p class="subtitulo">Descrição</p>' +
                        '   <p class="informacao">'+ proposta.descricao +'</p>' +
                        '   <form method="get" action="/proposta/negociacao">' +
                        '      <input type="hidden" value="'+ proposta.id +'" name="id" />' +
                        btn +
                        '   </form>' +
                        '</div>'
                    );
                });
                semProposta();
                desativaScroll = false;
            },
            error: function () {
                alert("Ocorreu um erro no carregamento. Tente novamente.");
            }
        });
    }

    //carregamento por demanda (rolagem do scroll)
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() == $(document).height()) {
            if(!desativaScroll){
                carregar(ultimoId,6, selecionado);
            }
        }
    });

    //atualiza o filtro quando clicado em um item da categoria
    $('.categoria_item').click(function (evento) {
        desativaScroll = true;
        evento.preventDefault();
        selecionado = $(this).attr('category');
        ultimoId = 0;
        $('.categoria_item').removeClass('selecionada');
        $(this).addClass('selecionada');
        $('.todas_propostas').empty();
        carregar(ultimoId, 6, selecionado);
    });

    carregar(ultimoId, 6, 'todas');
});