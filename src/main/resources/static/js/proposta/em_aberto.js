$(document).ready(function () {
    var ultimoId = 0;

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
            data: {inicio: inicio, qtd: qtd, categoria: 4},
            success: function (resultado) {
                var propostas = JSON.parse(resultado);
                var status, btn, tipo;
                ultimoId = propostas.ultimoId;
                $.each(propostas.dados, function (i, proposta) {
                    status = "pendente";
                    btn = '<button class="btn-detalhes" type="submit">Aceitar</button>\n';
                    if(proposta.tipo === 'TIPO_EXTERMINIO'){
                        tipo = 'Extermínio';
                    }else{
                        tipo = 'Prevenção';
                    }
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
                        '   <form method="get" action="/proposta/aceitar">' +
                        '      <input type="hidden" value="'+ proposta.id +'" name="id" />' +
                        btn +
                        '   </form>' +
                        '</div>'
                    );
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