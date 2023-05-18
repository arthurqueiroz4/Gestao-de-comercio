const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})


function pesquisarProduto(){
  const usuario = {
    login: localStorage.getItem('login'),
    senha: localStorage.getItem('senha')
  };

  const codigoBarras = $('#pesquisarProduto').val();

    console.log(usuario.login)
    console.log(codigoBarras)

  $.ajax({
    url: '/api/usuarios/auth',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      var token = data.token;
      const url = '/api/produtos/barras/'+codigoBarras;
      console.log(url)
      $.ajax({
        url: url,
        type: 'GET',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+token
        },
        success: function(data) {
            /*Desbloquear os campos de input 'Quantidade' e 'Preco'*/
            /*e guardo o produto*/
            console.log(data.descricao)

//            $.ajax({
//                    url: '/api/estoque',
//                    type: 'POST',
//                    contentType: 'application/json',
//                    headers:{
//                      'Authorization': 'Bearer '+token
//                    },
//                    success: function(data) {
//                        /*Mensagem de salvo com sucesso*/
//                    },
//                    error: function(jqXHR) {
//                      // var list = JSON.parse(jqXHR.responseText).errors
//                      // var erro
//                      // list.forEach(function(error) {
//                      //     console.log(error)
//                      // })
//                    }
//                  });

        },
        error: function(jqXHR) {
          // var list = JSON.parse(jqXHR.responseText).errors
          // var erro
          // list.forEach(function(error) {
          //     console.log(error)
          // })
        }
      });
    },
    error: function(jqXHR) {
      var list = JSON.parse(jqXHR.responseText).errors
      list.forEach(function(error) {
          console.log(error)
      })
    }
  });
}




function preencherTabela(){
  const usuario = {
    login: localStorage.getItem('login'),
    senha: localStorage.getItem('senha')
  };
    console.log('/api/estoque?login='+usuario.login)
  $.ajax({
    url: '/api/usuarios/auth',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      var token = data.token;
      $.ajax({
        url: '/api/estoque?login='+usuario.login,
        type: 'GET',
        contentType: 'application/json',
        headers:{
          'Authorization': 'Bearer '+token
        },
        success: function(data) {

            $('#tabela > tbody > tr').remove()
                      data.forEach(function(c) {
                        $('#tabela > tbody').append('<tr> <td>'+c.nomeProduto+'</td> <td>'+c.codigoBarras+'</td> <td>'+c.precoUnitario+'</td> <td>'+c.quantidade+'</td></tr>')
                      })

        },
        error: function(jqXHR) {
        }
      });
    },
    error: function(jqXHR) {
      var list = JSON.parse(jqXHR.responseText).errors
      list.forEach(function(error) {
          console.log(error)
      })
    }
  });
}

preencherTabela();