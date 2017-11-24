$(document).ready(function () {
    var ultimoId = 0;
    var selecionado = 'todas';

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
            default://todas
                return 5;
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

    function carregar(inicio, qtd, category, tipo_user) {
        jQuery.ajax({
            url: 'http://localhost:8080/proposta/carregar',
            type: 'GET',
            data: {inicio: inicio, qtd: qtd, categoria: getCategoryInteger(category)},
            success: function (resultado) {
                var propostas = JSON.parse(resultado);
                var status, btn, tipo;
                ultimoId = propostas.ultimoId;
                $.each(propostas.dados, function (i, proposta) {
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
                    }
                    if(proposta.funcionario === undefined){
                        btn = '<button class="btn-detalhes bloqueada" type="submit" disabled="true">Detalhes</button>\n';
                    }else{
                        btn = '<button class="btn-detalhes" type="submit" >Detalhes</button>\n';
                    }
                    if(proposta.tipo === 'TIPO_EXTERMINIO'){
                        tipo = 'Extermínio';
                    }else{
                        tipo = 'Prevenção';
                    }
                    if(tipo_user.localeCompare("Cliente")){
                        jQuery('.todas_propostas').append(
                            '<div class="item" category="'+ status +'">' +
                            '   <div class="title-item" >' +
                            '      <span class="status '+ status+'"></span>' +
                            '      <p>Orçamento: 0,00</p>' +
                            '   </div>' +
                            '   <p class="subtitulo">Endereço de Realização</p>' +
                            '   <p class="informacao">'+ proposta.endereco.rua +' '+ proposta.endereco.numero +' '+ proposta.endereco.bairro +' - '+ proposta.endereco.cidade +'/'+ proposta.endereco.uf +'</p>\n' +
                            '   <p class="subtitulo">Tipo</p>' +
                            '   <p class="informacao">'+ tipo +'</p>' +
                            '   <p class="subtitulo">Descrição</p>' +
                            '   <p class="informacao">'+ proposta.descricao +'</p>' +
                            '   <form method="get" action="/proposta/negociacao">' +
                            '      <input type="hidden" value="'+ proposta.id +'" name="id" />' +
                            btn +
                            '   </form>' +
                            '</div>'
                        );
                    }else{
                        jQuery('.todas_propostas').append(
                            '<div class="item" category="'+ status +'">' +
                            '   <p class="cliente">'+ proposta.usuario.nome +'</p>' +
                            '   <div class="title-item" >' +
                            '      <span class="status '+ status+'"></span>' +
                            '      <p>Orçamento: 0,00</p>' +
                            '   </div>' +
                            '   <p class="subtitulo">Endereço de Realização</p>' +
                            '   <p class="informacao">'+ proposta.endereco.rua +' '+ proposta.endereco.numero +' '+ proposta.endereco.bairro +' - '+ proposta.endereco.cidade +'/'+ proposta.endereco.uf +'</p>\n' +
                            '   <p class="subtitulo">Tipo</p>' +
                            '   <p class="informacao">'+ tipo +'</p>' +
                            '   <p class="subtitulo">Descrição</p>' +
                            '   <p class="informacao">'+ proposta.descricao +'</p>' +
                            '   <form method="get" action="/proposta/negociacao">' +
                            '      <input type="hidden" value="'+ proposta.id +'" name="id" />' +
                            btn +
                            '   </form>' +
                            '</div>'
                        );
                    }
                });
                semProposta();
            },
            error: function () {
                alert("Ocorreu um erro no carregamento. Tente novamente.");
            }
        });
    }

    //carregamento por demanda (rolagem do scroll)
    $(window).scroll(function() {
        if($(window).scrollTop() + $(window).height() == $(document).height()) {
            if(selecionado === 'todas'){
                var item = $('.item');
                carregar(ultimoId,6, selecionado);
            }
        }
    });

    //atualiza o filtro quando clicado em um item da categoria
    $('.categoria_item').click(function (evento) {
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