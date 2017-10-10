$(function () {
    var formulario = $('form[name=form-add-praga]');

    $('.alterar').click(function() {
        document.getElementById("idNovoNome").value = this.value;
    });

    $('.add').click(function(evento){
        var campos = formulario.serializeArray();
        if(campos[0].value == ''){
            $('.error-nome').html('<p class="error-nome"><span style="color: red">Campo Obrigatório</span></p>');
            return false;
        }
        $('.error-nome').html('<p class="error-nome"></p>');
    });
});

function buscar() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("input-buscar");
    filter = input.value.toUpperCase();
    table = document.getElementById("dataTable");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}