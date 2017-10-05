$(function () {
    var formulario = $('form[name=form-login]');

    //validar e-mail
    function validaEmail(field) {
        usuario = field.value.substring(0, field.value.indexOf("@"));
        dominio = field.value.substring(field.value.indexOf("@")+ 1, field.value.length);

        if ((usuario.length >=1) &&
            (dominio.length >=3) &&
            (usuario.search("@")==-1) &&
            (dominio.search("@")==-1) &&
            (usuario.search(" ")==-1) &&
            (dominio.search(" ")==-1) &&
            (dominio.search(".")!=-1) &&
            (dominio.indexOf(".") >=1)&&
            (dominio.lastIndexOf(".") < dominio.length - 1)){
            return true;
        }

        return false;
    }

    //valida senha
    function validaSenha(field) {
        if((field.value).length < 8){
            return false;
        }
        return true;
    }

    $('input[type=submit]').click(function (evento) {
        var array_campos = formulario.serializeArray();

        if(array_campos[0].value == '' || array_campos[1].value == ''){
            if(array_campos[0].value == ''){
                $('.error-email').html('<p class="error-email"><span style="color: red">Informe um email</span></p>');
            }else{
                $('.error-email').html('<p class="error-email"></p>');
            }
            if(array_campos[1].value == ''){
                $('.error-senha').html('<p class="error-senha"><span style="color: red">Informe uma senha(pelo menos 8 caracteres)</span></p>');
            }else{
                $('.error-senha').html('<p class="error-senha"></span></p>');
            }
            return false;
        }else{
            $('.error-email').html('<p class="error-email"></span></p>');
            $('.error-senha').html('<p class="error-senha"></span></p>');

            if(!validaEmail(array_campos[0])){
                $('.error-email').html('<p class="error-email"><span style="color: red">Informe um email válido</span></p>');
                return false;
            }else{
                $('.error-email').html('<p class="error-email"></span></p>');
            }

            if(!validaSenha(array_campos[1])){
                $('.error-senha').html('<p class="error-senha"><span style="color: red">Informe uma senha válida</span></p>');
                return false;
            }else{
                $('.error-senha').html('<p class="error-senha"></span></p>');
            }
        }
    });
});