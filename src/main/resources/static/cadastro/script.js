const form = $('#form2');
const button = $('#button');
// a função retira os 2 ultimos digitos do cnpj fornecido e com o restante, faz os calculos abaixo e compara com os 2 ultimos 
// para validar o cnpj
function validarCNPJ(cnpj) {
  if(cnpj == '') return false;
   
  if (cnpj.length != 14){
    return false;
  }
  
  // Elimina CNPJs invalidos conhecidos
  if (/^(\d)\1+$/.test(cnpj)) {
    return false;
  }

  // Calcula o primeiro dígito verificador
  let soma = 0;
  let peso = 2;
  
  for (let i = 11; i >= 0; i--) {
    soma += parseInt(cnpj.charAt(i)) * peso;
    peso = (peso === 9) ? 2 : peso + 1;
  }
  
  let digitoVerificador = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
  
  if (parseInt(cnpj.charAt(12)) !== digitoVerificador) {
    return false;
  }

  // Calcula o segundo dígito verificador
  soma = 0;
  peso = 2;
  
  for (let i = 12; i >= 0; i--) {
    soma += parseInt(cnpj.charAt(i)) * peso;
    peso = (peso === 9) ? 2 : peso + 1;
  }
  
  digitoVerificador = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
  
  if (parseInt(cnpj.charAt(13)) !== digitoVerificador) {
    return false;
  }
  
  return true; // CNPJ válido
  
}
// função de inserir e apagar do html os elementos
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

        if(usuario.cnpj != ""){
          if(cnpjsplit.length == 14){
            // remove a mensagem de texto "Digite um CNPJ válido."
            if(!isNaN(usuario.cnpj) && validarCNPJ(usuario.cnpj)){
              $("#spanCnpjErro").remove()
            }
            // insere no html o erro caso seja do tipo "Digite um CNPJ válido." e o cnpj inserido não seja válido
            if(!validarCNPJ(usuario.cnpj) && aux[2] == "CNPJ"){
              document.getElementById('divCnpjErro').innerHTML = "<span id= 'spanCnpjErro'> " + error + "</span>"
            }
          }
          // insere no html o erro caso seja do tipo "Digite um CNPJ válido."
          if(cnpjsplit.length != 14){
            if(aux[2] == "CNPJ"){
              document.getElementById('divCnpjErro').innerHTML = "<span id= 'spanCnpjErro'> " + error + "</span>"
            }
          }
          // remove a mensagem de texto "Campo CNPJ inválido."
          $("#spanCnpj").remove()
        }
        // insere no html o erro caso seja do tipo "Campo CNPJ inválido."
        if(aux[1] == "CNPJ"){ 
          document.getElementById('divCnpj').innerHTML = "<span id= 'spanCnpj'>" + error + "</span>"
        }
        // apaga e insere no html o erro caso seja do tipo "Campo login inválido."
        insereHTML("spanMercado", error, "login", aux[1], 'divMercado', usuario.login)

        // apaga e insere no html o erro caso seja do tipo "Campo senha inválido."
        insereHTML("spanSenha", error, "senha", aux[1], 'divSenha', usuario.senha)

        // insere no html o erro caso seja do tipo "Mercado já cadastrado."
        if(aux[0] == "Mercado"){
          document.getElementById('divMercado').innerHTML = "<span id= 'spanMercado'> " + error + "</span>"
        }
      })
    }
  });
});