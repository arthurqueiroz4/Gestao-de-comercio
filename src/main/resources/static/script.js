const form = $('#form1');
const button = $('#button');

button.click(function(event) {
  event.preventDefault();

  const usuario = {
    login: $('#mercado').val(),
    senha: $('#senha').val()
  };

    console.log(usuario)

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
    error: function(jqXHR) {
                    var list = JSON.parse(jqXHR.responseText).errors
                    var erro
                    list.forEach(function(error) {
                        console.log(error)
                    })
               }
  });
});
function home(){
  window.location.href = "home/index.html";
}