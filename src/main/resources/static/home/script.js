
const usuario = {
  login: localStorage.getItem('login'),
  senha: localStorage.getItem('senha')
};
var token = localStorage.getItem("token");

function pegartoken(){
  $.ajax({
    url: '/api/vendas?login='+usuario.login,
    type: 'GET',
    contentType: 'application/json',
    headers:{
      'Authorization': 'Bearer '+ token 
    },
    success: function(data) {
      $('#tabela > tbody > tr').remove()
      data.forEach(function(c) {
        $('#tabela > tbody').append('<tr> <td>'+c.descricao+'</td> <td>'+c.codbarras+'</td> <td>'+c.precoUnitario+'</td> <td>'+c.quantidade+'</td> <td>'+c.date+'</td> </tr>')
      })
    },
    error: function(jqXHR) {
      var list = JSON.parse(jqXHR.responseText)
			if(list.status == 403) {
				window.location.href = "../index.html";
			}
    }
  });
}
pegartoken();

function sair(){
  $.ajax({
    url: '/logout',
    type: 'GET',
    contentType: 'application/json',
    success: function(data) {
      localStorage.removeItem("token")
    }
  });
}