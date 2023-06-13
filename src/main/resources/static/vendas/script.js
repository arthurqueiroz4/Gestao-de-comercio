var listProduto = [];
var produto;

function insertTable(listProduto){
    $('#tabela > tbody > tr').remove()
    listProduto.forEach(function(c) {
        $('#tabela > tbody').append('<tr> <td>' + c.descricao + '</td> <td>' + c.codigoBarras + '</td> <td>' + c.precoUnitario + '</td> <td>' + c.quantidade + '</td></tr>')
    })
}

function toastMessage(message, idText, idToast) {
	const toastLiveExample = document.getElementById(idToast)
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
	$(idText).text(message)
	toastBootstrap.show()
}

function buscarProduto(){
    const codigoBarra = $('#pesquisarProduto').val();
    if(codigoBarra == ""){
        toastMessage("Código de barras não pode ser vazio!", '#toast-text', 'liveToast')  
        return 0;
    }
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
            toastMessage('Produto não cadastrado no seu Estoque. Cadastre o produto para poder adicionar ao carrinho!', '#toast-text', 'liveToast')
        }
      });
}

function adicionarCarrinho(){
    const data = {
        codigoBarras : $("#codigoBarras").val(),
        quantidade : Number($("#quantidade").val()),
    }
    if($("#descricao").val()!= "" && $("#quantidade").val()!= "" && $("#quantidade").val() > 0){
        $.ajax({
        url: '/api/estoque/verificar?codigoBarras='+$("#codigoBarras").val()+
        '&quantidade='+Number($("#quantidade").val())+'&login='+localStorage.getItem("login"),
        type: 'GET',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+ localStorage.getItem("token") 
        },
        success: function(data) {
            produto = {
                descricao : $("#descricao").val(),
                codigoBarras : $("#codigoBarras").val(),
                precoUnitario : $("#precoUnitario").val(),
                quantidade : $("#quantidade").val()
            };
            var possui = false;
            
            listProduto.forEach(function(produto1){
                if(produto1.codigoBarras === produto.codigoBarras){
                    possui = true;
                } 
            })
            if(!possui){
                listProduto.push(produto);
                insertTable(listProduto);
                $("#exampleModal").modal('hide');
            } else{
                toastMessage("Produto já está no carrinho!", '#toast-text', 'liveToast')
            }
        },
        error: function(jqXHR) {
            var list = JSON.parse(jqXHR.responseText).errors
            var list1 = JSON.parse(jqXHR.responseText)
            if(list1.status == 403) {
                window.location.href = "../index.html";
            }
           
            toastMessage(list[0], '#toast-text', 'liveToast')  
            
        }
      });
    }
    else{
        if($("#pesquisarProduto").val() == ""){
            toastMessage("Não há produto indicado!", '#toast-text', 'liveToast')
        }
        else if($("#quantidade").val() == ""){
            toastMessage("Não há quantidade indicada!", '#toast-text', 'liveToast')
        }
        else if($("#quantidade").val() <= 0){
            toastMessage("Insira a quantidade correta!", '#toast-text', 'liveToast')
        }
    }
    
    
}
function fechar(){
    $("#descricao").val("")
    $("#precoUnitario").val("")
    $("#quantidade").val("")
    $("#codigoBarras").val("")
    $("#pesquisarProduto").val("")
}
function cancelar(){
    var elemento = document.querySelector('#tabela > tbody > tr');
    if(elemento){
        $('#tabela > tbody > tr').remove()
        listProduto.splice(0, listProduto.length)  
    }else{
        toastMessage('Não há produtos para cancelar a venda!', '#toast-text1', 'liveToast1')
    }
    
}

function vender(){
    let listRequest = [];
    let produtoDict
    listProduto.forEach(produto => {
        produtoDict = {
            codigoBarras : produto.codigoBarras,
            quantidade : produto.quantidade
        }
        listRequest.push(produtoDict)
    })
    const data = {
        "login" : localStorage.getItem("login"),
        "list" : listRequest
    }
    
    
    $.ajax({
        url: '/api/estoque/vender',
        type: 'POST',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+ localStorage.getItem("token") 
        },
        data: JSON.stringify(data),
        success: function(data) {
            window.location.href = "../home/index.html"
        },
        error: function(jqXHR) {
            var list = JSON.parse(jqXHR.responseText)
            if(list.status == 403) {
                window.location.href = "../index.html";
            }
            
            toastMessage('Adicione produtos antes de vender!', '#toast-text1', 'liveToast1')
            
        }
      });
}

var meuModal = document.getElementById("exampleModal");
meuModal.addEventListener("hidden.bs.modal", function (event) {
  fechar();
});