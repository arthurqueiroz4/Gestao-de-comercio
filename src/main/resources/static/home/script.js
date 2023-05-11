var token;

function pegartoken(){
    const usuario = {
        login: localStorage.getItem('login'),
        senha: localStorage.getItem('senha')
    };

        console.log(usuario)
        console.log('/api/vendas?login='+usuario.login)

      $.ajax({
        url: '/api/usuarios/auth',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(usuario),
        success: function(data) {
          token = data.token;
          console.log(token)
           $.ajax({
                    url: '/api/vendas?login='+usuario.login,
                    type: 'GET',
                    contentType: 'application/json',
                    headers:{
                      'Authorization': 'Bearer '+token
                    },
                    success: function(data) {

                            $('#tabela > tbody > tr').remove()
                            data.forEach(function(c) {
                                       $('#tabela > tbody').append('<tr> <td>'+c.descricao+'</td> <td>'+c.codbarras+'</td> <td>'+c.precoUnitario+'</td> <td>'+c.quantidade+'</td> <td>'+c.date+'</td> </tr>')
                            })
                        },
                    error: function(jqXHR) {
//                                    var list = JSON.parse(jqXHR.responseText).errors
//                                    var erro
//                                    list.forEach(function(error) {
//                                        console.log(error)
//                                    })
                               }
                  });
            },
        error: function(jqXHR) {
                        var list = JSON.parse(jqXHR.responseText).errors
                        var erro
                        list.forEach(function(error) {
                            console.log(error)
                        })
                   }
      });
      }
//
////
//function pegarVendas(){
//        $.ajax({
//          url: '/api/vendas',
//          type: 'GET',
//          contentType: 'application/json',
//          headers:{
//            'Authorization': 'Bearer '+token
//          },
//          success: function(data) {
//            console.log(data)
//              },
//          error: function(jqXHR) {
//                          var list = JSON.parse(jqXHR.responseText).errors
//                          var erro
//                          list.forEach(function(error) {
//                              console.log(error)
//                          })
//                     }
//        });
//        }

pegartoken();
console.log(token)
//pegarVendas();