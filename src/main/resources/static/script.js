const form = $('#form1');
const button = $('#button');

button.click(function(event) {
  event.preventDefault();

  const usuario = {
    login: $('#mercado').val(),
    senha: $('#senha').val()
  };

  $.ajax({
    url: '/api/usuarios/auth',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      console.log('Login realizado com sucesso!');
      console.log(data)
    //   document.getElementById("").innerHTML = data;
        },
    error: function() {
      console.error('Erro ao realizar login.');
    }
  });
});
