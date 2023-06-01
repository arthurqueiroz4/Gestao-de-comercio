var listProduto = [];
function buscarProduto(){
    const codigoBarra = $('#pesquisarProduto').val();
    $.ajax({
        url: '/api/estoque/produto?codigo='+codigoBarra+'&mercado='+localStorage.getItem("login"),
        type: 'GET',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+ localStorage.getItem("token") 
        },
        success: function(data) {
            $("#descricao").val(data.nomeProduto)
            $("#codigoBarras").val(data.codigoBarras)
            $("#precoUnitario").val(data.precoUnitario)
            $("#quantidade").attr("placeholder", "Digite a quantidade a ser vendida")
        },
        error: function(jqXHR) {
          var list = JSON.parse(jqXHR.responseText)
                if(list.status == 403) {
                    window.location.href = "../index.html";
                }
        }
      });
}

function adicionarCarrinho(){
    const data = {
        codigoBarras : $("#codigoBarras").val(),
        quantidade : Number($("#quantidade").val()),
        login : localStorage.getItem("login")
    }
    console.log(JSON.stringify(data))
    $.ajax({
        url: '/api/estoque/verificar?codigoBarras='+$("#codigoBarras").val()+
        '&quantidade='+Number($("#quantidade").val())+'&login='+localStorage.getItem("login"),
        type: 'GET',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+ localStorage.getItem("token") 
        },
        success: function(data) {
            console.log(data)
        },
        error: function(jqXHR) {
          var list = JSON.parse(jqXHR.responseText).errors
                if(list.status == 403) {
                    window.location.href = "../index.html";
                }
            list.array.forEach(element => {
                console.log(element)
            });
        }
      });
}
