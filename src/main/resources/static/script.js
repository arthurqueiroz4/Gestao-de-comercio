
const form = $('#form1');
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
    login: $('#mercado').val(),
    senha: $('#senha').val()
  };

  localStorage.setItem('login',  $('#mercado').val())
  localStorage.setItem('senha',  $('#senha').val())

  
  $.ajax({
    url: '/api/usuarios/auth',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      $("#spanSenha").remove()
      $("#spanMercado").remove()
      $("#spanSubmit").remove()
      
      window.location.href = "home/index.html"
      localStorage.setItem('token', data.token)
    },
    error: function(jqXHR) {
      var list = JSON.parse(jqXHR.responseText).errors
      list.forEach(function(error) {
        aux = error.split(' ')
        insereHTML('spanMercado', error, 'login', aux[1], 'divMercado', usuario.login)
        
        insereHTML('spanSenha', error, 'senha', aux[1], 'divSenha', usuario.senha)
        
        if(aux[1] == "não"){
          document.getElementById('divMercado').innerHTML = "<span id= 'spanMercado'> " + error + "</span>"
        }
        if(aux[1] == "incorreta."){
          document.getElementById('divSenha').innerHTML = "<span id= 'spanSenha'> " + error + "</span>"
        }
      })

      
    }
  });
});
