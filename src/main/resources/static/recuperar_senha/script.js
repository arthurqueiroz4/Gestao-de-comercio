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
    type: 'PATCH',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      console.log('Cadastro realizado com sucesso!');
    //   document.getElementById("").innerHTML = data;
        },
		error: function(jqXHR) {
                var list = JSON.parse(jqXHR.responseText).errors
                var erro
                list.forEach(function(error) {
                    console.log(error)
                })
           }
  });
});