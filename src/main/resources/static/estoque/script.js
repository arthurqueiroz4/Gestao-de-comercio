const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})

function toastMessage(message){
    const toastLiveExample = document.getElementById('liveToast')
                    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
                    $('#toast-text').text(message)
                        toastBootstrap.show()
}

function buscarProduto(){
  const usuario = {
    login: localStorage.getItem('login'),
    senha: localStorage.getItem('senha')
  };

  const codigoBarras = $('#pesquisarProduto').val();
    if (codigoBarras === ''){
         const toastLiveExample = document.getElementById('liveToast')
                    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
                    $('#toast-text').text('Código de barras não pode ser vazio')
                        toastBootstrap.show()
                        return 0;
    }

  $.ajax({
    url: '/api/usuarios/auth',
    type: 'POST',
    contentType: 'application/json',
    data: JSON.stringify(usuario),
    success: function(data) {
      var token = data.token;
      const url = '/api/produtos/barras/'+codigoBarras;
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
            $('#descricao').val(data.decricao)
            $('#codigoBarras').val(data.codig_barras)
        },
        error: function(jqXHR) {
        var list = JSON.parse(jqXHR.responseText)
        const toastLiveExample = document.getElementById('liveToast')
            const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
            $('#toast-text').text('Código de barras não cadastrado. Cadastre o produto antes de adicioná-lo ao Estoque.')
                toastBootstrap.show()

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

function adicionarEstoque(){
    const usuario = {
        login: localStorage.getItem('login'),
        senha: localStorage.getItem('senha')
      };

      const codigoBarras = $('#pesquisarProduto').val();
      const quantidade = $('#quantidade').val()
      const precoUnitario = $('#precoUnitario').val()
      console.log(codigoBarras)
        if (codigoBarras === '' || quantidade == '' || precoUnitario == ''){
             toastMessage('Preencha os campos corretamente.')

                            return 0;
        }

      $.ajax({
        url: '/api/usuarios/auth',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(usuario),
        success: function(data) {
          var token = data.token;
          var data = {
            codigoBarras:codigoBarras,
            quantidade:quantidade,
            precoUnitario:precoUnitario,
            nomeMercado:usuario.login
          }
          $.ajax({
            url: '/api/estoque',
            type: 'POST',
            contentType: 'application/json',
            data:JSON.stringify(data),
            headers:{
              'Authorization': 'Bearer '+token
            },
            success: function(data) {
                preencherTabela()
            },
            error: function(jqXHR) {
            var list = JSON.parse(jqXHR.responseText)
            console.log(list)
            toastMessage('Impossível fazer cadastrado desse produto no Estoque.')

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