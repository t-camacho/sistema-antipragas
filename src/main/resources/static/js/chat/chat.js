jQuery(function(){
    var usuarioOnline = jQuery(".status").attr('id');
    var propostaNeg = jQuery('.id_proposta').val();

    //minimiza janela
    jQuery('body').on('click', '.cabecalho_janela', function(){
        var next = jQuery(this).next();
        next.toggle(100);
    });

    //envia msg p/ bd
    jQuery('body').on('keyup', '.msg', function(evento){
        if(evento.which == 13){
           var texto = jQuery(this).val();
           var id_proposta = jQuery('.id_proposta').val();
           var id = jQuery('.msg').attr('id');
           jQuery.ajax({
               url: 'http://localhost:8080/chat/enviar',
               data: {mensagem: texto, id: id, id_proposta: id_proposta},
               success: function () {
                   jQuery('.msg').val('');
               },
               error: function () {
                   alert("Não foi possivel enviar a mensagem. Tente novamente.");
               }
           });
       }
    });

    //recupera historico da conversa
    function retorna_historico(id_proposta){
        jQuery.ajax({
            url: 'http://localhost:8080/chat/historico',
            data: {idProposta: id_proposta},
            success: function (retorno) {
                var obj = JSON.parse(retorno);
                $.each(obj, function(i, msg) {
                    if(msg.idDe == usuarioOnline){
                        jQuery('.janela .mensagens ul').append('<li class="eu" id="msg'+msg.id+'"><p>'+msg.mensagem+'</p></li>');
                    }else{
                        jQuery('.janela .mensagens ul').append('<li class="outro" id="msg'+msg.id+'"><p>'+msg.mensagem+'</p></li>');
                    }
                });
                var altura = jQuery('.janela .corpo .mensagens')[0].scrollHeight;
                jQuery('.janela .corpo .mensagens').animate({scrollTop: altura}, '500');
            }
        });
    }

    function verifica(timestamp, ultimo_id, id_proposta){
        var manipulador;
        jQuery.ajax({
            url: 'http://localhost:8080/chat/stream',
            type: 'GET',
            data: {timestamp: timestamp, ultimoId: ultimo_id, idProposta: id_proposta},
            success: function (obj) {
                var retorno = JSON.parse(obj);
                clearInterval(manipulador);

                if(retorno.status == 'resultados' || retorno.status == 'vazio'){
                    manipulador = setTimeout(function () {
                        verifica(retorno.timestamp, retorno.ultimoId, retorno.idProposta);
                    },100);
                    if(retorno.status == 'resultados'){
                        jQuery.each(retorno.dados, function (i, msg) {
                            if(jQuery('.janela .mensagens ul li#msg'+msg.id).length == 0){
                                if(usuarioOnline == msg.idDe){
                                    jQuery('.janela .mensagens ul').append('<li class="eu" id="msg'+msg.id+'"><p>'+msg.mensagem+'</p></li>');
                                }else{
                                    jQuery('.janela .mensagens ul').append('<li class="outro" id="msg'+msg.id+'"><p>'+msg.mensagem+'</p></li>');
                                }
                            }
                        });
                    }
                    var altura = jQuery('.janela .corpo .mensagens')[0].scrollHeight;
                    jQuery('.janela .corpo .mensagens').animate({scrollTop: altura}, '500');
                }
            },
            error: function (retorno) {
                clearInterval(manipulador);
                manipulador = setTimeout(function () {
                    verifica(retorno.timestamp, retorno.ultimoId, retorno.idProposta);
                },15000);
            }
        })
    }
    //carrega o histórico da conversa quando a pagina negociação é solicitada
    //retorna_historico(propostaNeg);
    //inicia longpolling
    verifica("2016-11-24 10:02:54",0,propostaNeg);
});