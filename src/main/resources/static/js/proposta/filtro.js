$(document).ready(function () {
    //inicia com o filtro todas
    $('.categoria_lista .categoria_item[category="todas"]').addClass('selecionada');
    //atualiza o filtro quando clicado em um item da categoria
    $('.categoria_item').click(function () {
        var catSelecionada = $(this).attr('category');

        $('.categoria_item').removeClass('selecionada');
        $(this).addClass('selecionada');

        $('.item').hide();

        switch(catSelecionada){
            case 'todas':
                $('.item').show();
                break;
            default:
                $('.item[category="'+catSelecionada+'"]').show();
        }
    });
});