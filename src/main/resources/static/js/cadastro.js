$(function(){
    var atual_fs, next_fs, prev_fs;
    var formu = $('form[name=form-register]');

    var count = 0; //nome dos buttons associado a posição do vetor div e hidden
    var controller = 0; //controla se existe algum endereço adicionado
    var array_div = [];
    var array_hidden = [];

    //função para passar pro próximo passo
    function next(elem){
        atual_fs = $(elem).parent();
        next_fs = $(elem).parent().next();

        $('#progress li').eq($('fieldset').index(next_fs)).addClass('ativo')
        atual_fs.hide(800);
        next_fs.show(800);
    }

    //função usada para deletar um endereço adicionado
    function excluirAddress(){
        array_div[this.name].parentNode.removeChild(array_div[this.name]);
        array_hidden[this.name].parentNode.removeChild(array_hidden[this.name]);
        this.parentNode.removeChild(this);
        controller--;
    }

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

    //função para validar o cpf
    function validarCPF(cpf) {
        cpf = cpf.replace(/[^\d]+/g,'');
        if(cpf == '') return false;
        // Elimina CPFs invalidos conhecidos
        if (cpf.length != 11 ||
            cpf == "00000000000" ||
            cpf == "11111111111" ||
            cpf == "22222222222" ||
            cpf == "33333333333" ||
            cpf == "44444444444" ||
            cpf == "55555555555" ||
            cpf == "66666666666" ||
            cpf == "77777777777" ||
            cpf == "88888888888" ||
            cpf == "99999999999")
            return false;
        // Valida 1o digito
        add = 0;
        for (i=0; i < 9; i ++)
            add += parseInt(cpf.charAt(i)) * (10 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(cpf.charAt(9)))
            return false;
        // Valida 2o digito
        add = 0;
        for (i = 0; i < 10; i ++)
            add += parseInt(cpf.charAt(i)) * (11 - i);
        rev = 11 - (add % 11);
        if (rev == 10 || rev == 11)
            rev = 0;
        if (rev != parseInt(cpf.charAt(10)))
            return false;
        return true;
    }

    //função para voltar um passo
    $('.prev').click(function(){
        atual_fs = $(this).parent();
        prev_fs = $(this).parent().prev();

        $('#progress li').eq($('fieldset').index(atual_fs)).removeClass('ativo');
        atual_fs.hide(800);
        prev_fs.show(800);
    });
    //função para passar do primeiro para o segundo apenas se todos os dados obrigatórios foram preenchidos
    $('input[name=next1]').click(function(){
        var array = formu.serializeArray();

        if(array[0].value == '' || array[3].value == '' || array[4].value == '' || array[5].value == ''){
            if(array[0].value == ''){
                $('.error-name').html('<p class="error-name"><span style="color: red">Seu nome é obrigatório</span></p>');
            }else{
                $('.error-name').html('<p class="error-name"></p>');
            }
            if(array[3].value == ''){
                $('.error-data').html('<p class="error-data"><span style="color: red">Sua data de nascimento é obrigatório</span></p>');
            }else{
                $('.error-data').html('<p class="error-data"></p>');
            }
            if(array[4].value == '') {
                $('.error-cpf').html('<p class="error-cpf"><span style="color: red">Seu CPF é obrigatório</span></p>');
            }else{
                $('.error-cpf').html('<p class="error-cpf"></p>');
            }
        }else{
            $('.error-name').html('<p class="error-name"></p>')
            $('.error-data').html('<p class="error-data"></p>');
            if(!validarCPF(array[4].value)) {
                $('.error-cpf').html('<p class="error-cpf"><span style="color: red">Informe um CPF válido</span></p>');
                return;
            }else{
                $('.error-cpf').html('<p class="error-cpf"></p>');
            }
            next($(this));
        }
    });

    //função para passar do primeiro para o terceiro apenas se todos os dados obrigatórios foram preenchidos
    $('input[name=next2]').click(function(){
        var array = formu.serializeArray();

        if(array[6].value == '' || array[7].value == '' || array[8].value == ''){
            if(array[6].value == ''){
                $('.error-email').html('<p class="error-email"><span style="color: red">Seu email é obrigatório</span></p>');
            }else{
                $('.error-email').html('<p class="error-email"></p>');
            }
            if(array[7].value == ''){
                $('.error-senha').html('<p class="error-senha"><span style="color: red">Escolha uma senha</span></p>');
            }else{
                $('.error-senha').html('<p class="error-senha"></p>');
            }
            if(array[8].value == ''){
                $('.error-csenha').html('<p class="error-csenha"><span style="color: red">Confirme sua senha</span></p>');
            }else{
                $('.error-csenha').html('<p class="error-csenha"></p>');
            }
        }else{

            $('.error-senha').html('<p class="error-senha"></p>');

            if(!validaEmail(array[6])){
                $('.error-email').html('<p class="error-email"><span style="color: red">Informe um email válido</span></p>');
                return;
            }else{
                $('.error-email').html('<p class="error-email"></p>');
            }

            if((array[7].value).length < 8){
                $('.error-senha').html('<p class="error-senha"><span style="color: red">Informe uma senha com pelo menos 8 caracteres</span></p>');
                return;
            }else{
                $('.error-senha').html('<p class="error-senha"></p>');
            }

            if(!(array[8].value === array[7].value)){
                $('.error-csenha').html('<p class="error-csenha"><span style="color: red">Não corresponde com a senha</span></p>');
                return;
            }else{
                $('.error-csenha').html('<p class="error-csenha"></p>');
            }
            next($(this));
        }
    });

    $('input[type=submit]').click(function (evento) {
        var array = formu.serializeArray();

        if(controller < 1) {
            $('.error-cep').html('<p class="error-cep"><span style="color: red">Informe pelo menos um endereço</span></p>');
            return false;
        }else{
            $('.error-cep').html('<p class="error-cep"></p>');
            evento.submit();
        }
    });

    $('.add').click(function(){
        var array = formu.serializeArray();
        if(array[9].value == '' || array[10].value == '...' || array[10].value == ''){
            $('.error-cep').html('<p class="error-cep"><span style="color: red">Informe o CEP do endereço</span></p>');
        }else{
            $('.error-cep').html('<p class="error-cep"></p>');
            var address = array[9].value + "/" + array[10].value + "/" + array[11].value + "/" + array[12].value + "/" +
                array[13].value + "/" + array[14].value + "/" + array[15].value;
            var address_display = array[9].value;
            //criando os objetos a serem adicionados na tela
            var div_address = document.createElement('div');
            var btn_delete = document.createElement('input');
            var input_hidden = document.createElement('input');
            //configuração da div
            var content = "<p><strong>CEP:</strong> " + array[9].value + " <strong>Rua:</strong> " + array[10].value + " <strong>Número:</strong> " + array[11].value + "</p>" +
            "<p><strong>Bairro: </strong>" + array[12].value + " <strong> Cidade: </strong>" + array[13].value +
                "<strong> UF: </strong>" + array[14].value;

            if(array[15].value == ''){
                content = content + "</p>";
            }else{
                content = content + "<strong> Complemento: </strong>"+ array[15].value +"</p>";
            }

            div_address.innerHTML = content;
            div_address.className = "list-of-address-item";
            //configuração do button
            btn_delete.type = "button";
            btn_delete.value = "Deletar";
            btn_delete.className = "acao add";
            btn_delete.addEventListener("click", excluirAddress);
            btn_delete.name = count;

            //configuração do hidden
            input_hidden.type = "hidden";
            input_hidden.name = "endereco";
            input_hidden.value = address;

            array_div[count] = div_address;
            array_hidden[count] = input_hidden;

            //add hidden, button e div do endereço na tela
            document.getElementById("list-of-address").appendChild(div_address);
            document.getElementById("list-of-address").appendChild(btn_delete);
            document.getElementById("list-of-address").appendChild(input_hidden);

            count++;
            controller++;
        }
    });
});

$(document).ready(function() {
    //Quando o campo cep perde o foco.
    $('#cep').blur(function() {
        //Nova variável "cep" somente com dígitos.
        var cep = $("#cep").val() || '';
        if(!cep){
            return
        }
        $("#rua").val("...");
        $("#bairro").val("...");
        $("#cidade").val("...");
        $("#uf").val("...");
        $("#ibge").val("...");
        $.getJSON("//viacep.com.br/ws/"+ cep +"/json/?callback=?", function(data){
            if("error" in data){
                return;
            }
            $("#rua").val(data.logradouro);
            $("#bairro").val(data.bairro);
            $("#cidade").val(data.localidade);
            $("#uf").val(data.uf);
        });
        //Verifica se campo cep possui valor informado.
    });
});

function mascaraMutuario(o,f){
    v_obj=o;
    v_fun=f;
    setTimeout('execmascara()',1);
}

function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}

function cel(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d{0})(\d)/,"$1($2");
    v=v.replace(/(\d{2})(\d)/,"$1)$2");
    v=v.replace(/(\d{5})(\d)/,"$1-$2");
    return v
}

function phone(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d{0})(\d)/,"$1($2");
    v=v.replace(/(\d{2})(\d)/,"$1)$2");
    v=v.replace(/(\d{4})(\d)/,"$1-$2");
    return v
}

function cept(v){
    v=v.replace(/\D/g,"");
    v=v.replace(/(\d{5})(\d)/,"$1-$2");
    return v
}

function cpfCnpj(v){
    //Remove tudo o que não é dígito
    v=v.replace(/\D/g,"")

    if (v.length <= 14) { //CPF

        //Coloca um ponto entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")

        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        v=v.replace(/(\d{3})(\d)/,"$1.$2")

        //Coloca um hífen entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")

    } else { //CNPJ

        //Coloca ponto entre o segundo e o terceiro dígitos
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")

        //Coloca ponto entre o quinto e o sexto dígitos
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")

        //Coloca uma barra entre o oitavo e o nono dígitos
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")

        //Coloca um hífen depois do bloco de quatro dígitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")
    }

    return v

}