$(function(){
    var atual_fs, next_fs, prev_fs;
    var formulario = $('form[name=form-proposta]');

    //função para passar para o próximo passo
    function next(elem){
        atual_fs = $(elem).parent();
        next_fs = $(elem).parent().next();

        $('#progress li').eq($('fieldset').index(next_fs)).addClass('ativo')
        atual_fs.hide(800);
        next_fs.show(800);
    }


    //função para voltar um passo
    $('.prev').click(function(){
        atual_fs = $(this).parent();
        prev_fs = $(this).parent().prev();

        $('#progress li').eq($('fieldset').index(atual_fs)).removeClass('ativo');
        atual_fs.hide(800);
        prev_fs.show(800);
    });

    $('input[name=next1]').click(function(){
        next($(this));
    });

    $('input[type=submit]').click(function (evento) {
        var campos = formulario.serializeArray();
        var praga_selecionada = false;
        if(campos[1].value > 1){
            document.getElementById("freq_nula").value = campos[3].value;
        }

        var pragas = document.getElementsByName("praga");
        for(var i =0; i < pragas.length; i++){
            if(pragas[i].checked){
                praga_selecionada = true;
            }
        }

        if(!praga_selecionada) {
            $('.error-praga').html('<p class="error-praga"><span style="color: red">Selecione um item</span></p>');
            return false;
        }else{
            $('.error-praga').html('<p class="error-praga"></p>');
        }

        if(document.getElementById("campoDescricao").value == ""){
            $('.error-desc').html('<p class="error-desc"><span style="color: red">Descrição Obrigatória</span></p>');
            return false;
        }else{
            $('.error-desc').html('<p class="error-desc"></p>');
        }
    });

    //só deixa escolher uma freq se visita > 1
    $('.qtdVisita').bind("click keyup" ,function () {
        if(this.value > 1){
            document.getElementById("frequencia").style.display="block";
        }else{
            document.getElementById("frequencia").style.display="none";
        }
    });

});

