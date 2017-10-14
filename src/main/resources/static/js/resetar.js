$(function () {
    var formulario = $('form[name=form-senha]');

    $('input[type=submit]').click(function(){
        var campos = formulario.serializeArray();

        if(campos[0].value == '' || campos[1].value == ''){
            if(campos[0].value == ''){
                $('.error-senha').html('<p class="error-senha"><span style="color: red">Campo Obrigatório</span></p>');
            }else{
                $('.error-senha').html('<p class="error-senha"></p>');
            }
            if(campos[1].value == ''){
                $('.error-csenha').html('<p class="error-csenha"><span style="color: red">Campo Obrigatório</span></p>');
            }else{
                $('.error-csenha').html('<p class="error-csenha"></p>');
            }
            return false;
        }else{
            if(campos[0].value != campos[1].value) {
                $('.error-csenha').html('<p class="error-csenha"><span style="color: red">Não corresponde com a senha</span></p>');
                return false;
            }else{
                $('.error-csenha').html('<p class="error-csenha"></p>');
            }
        }
    });
});
