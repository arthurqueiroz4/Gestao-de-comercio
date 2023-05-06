const form = $('#form2');
const button = $('#button');

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
      console.log('Cadastro realizado com sucesso!');
    //   document.getElementById("").innerHTML = data;
        },
		error: function(xhr, textStatus, errorThrown) {
			// Erro na requisição
			console.error('Erro ao realizar login: ' );
			
			console.log(xhr.responseText);
		}
  });
});