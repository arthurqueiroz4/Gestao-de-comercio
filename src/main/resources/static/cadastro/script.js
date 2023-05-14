const form = $('#form2');
const button = $('#button');

function insereHTML(idSpan, erro, nomeAux, auxPosition, divId, chaveUsuario){
  if(chaveUsuario != ""){
    $("#"+idSpan).remove()
  }
  if(auxPosition == nomeAux){ 
    document.getElementById(divId).innerHTML = "<span id= " + idSpan + ">" + erro + "</span>"
  }
}

button.click(function(event) {
  event.preventDefault();

  const usuario = {
    cnpj: $('#cnpj').val(),
    login: $('#mercado').val(),
    senha: $('#senha').val(),
    admin: false
  };

  $.ajax({
    url: '/api/usuarios',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      $("#spanCnpjErro").remove()
      $("#spanCnpj").remove()
      $("#spanMercado").remove()
      $("#spanSubmit").remove()
      console.log('Cadastro realizado com sucesso!')
      window.location.href = "/index.html"
    },
		error: function(jqXHR) {
      var list = JSON.parse(jqXHR.responseText).errors
      list.forEach(function(error) {
        aux = error.split(' ')
        console.log(aux)
        cnpjsplit = usuario.cnpj.split("")

        if(cnpjsplit.length == 14 && !isNaN(usuario.cnpj)){
          $("#spanCnpjErro").remove()
        }
        if(usuario.cnpj != ""){
          if(cnpjsplit.length != 14 && document.getElementById('spanCnpjErro') != null ){
            document.getElementById('spanCnpjErro').hidden = false
          }else if(cnpjsplit.length != 14 && aux[2] == "CNPJ"){
            document.getElementById('divCnpjErro').innerHTML = "<span id= 'spanCnpjErro' hidden> " + error + "</span>"
          }
          $("#spanCnpj").remove()
        }
        if(aux[1] == "CNPJ"){ 
          document.getElementById('divCnpj').innerHTML = "<span id= 'spanCnpj'>" + error + "</span>"
        }
        if(aux[2] == "CNPJ" && usuario.cnpj == ""){
          document.getElementById('divCnpjErro').innerHTML = "<span id= 'spanCnpjErro' hidden> " + error + "</span>"
        }

        insereHTML("spanMercado", error, "login", aux[1], 'divMercado', usuario.login)

        insereHTML("spanSenha", error, "senha", aux[1], 'divSenha', usuario.senha)

        if(aux[0] == "Mercado"){
          document.getElementById('divMercado').innerHTML = "<span id= 'spanMercado'> " + error + "</span>"
        }
      })
    }
  });
});