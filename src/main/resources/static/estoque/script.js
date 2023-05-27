
function toastMessage(message, idText, idToast) {
	const toastLiveExample = document.getElementById(idToast)
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
	$(idText).text(message)
	toastBootstrap.show()
}

function buscarProduto() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};

	const codigoBarras = $('#pesquisarProduto').val();
	if (codigoBarras === '') {
		toastMessage('Código de barras não pode ser vazio.', '#toast-text', 'liveToast')
		return 0;
	}
	$.ajax({
		url: '/api/usuarios/auth',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(usuario),
		success: function(data) {
			var token = data.token;
			const url = '/api/produtos/barras/' + codigoBarras;
			$.ajax({
				url: url,
				type: 'GET',
				contentType: 'application/json',
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {
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

function preencherTabela() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};
	console.log('/api/estoque?login=' + usuario.login)
	$.ajax({
		url: '/api/usuarios/auth',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(usuario),
		success: function(data) {
			var token = data.token;
			$.ajax({
				url: '/api/estoque?login=' + usuario.login,
				type: 'GET',
				contentType: 'application/json',
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {

					$('#tabela > tbody > tr').remove()
					data.forEach(function(c) {
						$('#tabela > tbody').append('<tr> <td>' + c.nomeProduto + '</td> <td>' + c.codigoBarras + '</td> <td>' + c.precoUnitario + '</td> <td>' + c.quantidade + '</td></tr>')
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

function adicionarEstoque() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};

	const codigoBarras = $('#pesquisarProduto').val();
	const quantidade = $('#quantidade').val()
	const precoUnitario = $('#precoUnitario').val()
	console.log(codigoBarras)
	if (codigoBarras === '' || quantidade == '' || precoUnitario == '') {
		toastMessage('Preencha os campos corretamente.', '#toast-text', 'liveToast')

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
				codigoBarras: codigoBarras,
				quantidade: quantidade,
				precoUnitario: precoUnitario,
				nomeMercado: usuario.login
			}
			$.ajax({
				url: '/api/estoque',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(data),
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {
					$('#precoUnitario').val("")
					$('#quantidade').val("")
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

function buscarProdutoEditar() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};

	const codigoBarras = $('#pesquisarProdutoEditar').val();
	if (codigoBarras === '') {
		toastMessage('Código de barras não pode ser vazio.', '#toast-text-editar', 'liveToastEditar')
		return 0;
	}

	$.ajax({
		url: '/api/usuarios/auth',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(usuario),
		success: function(data) {
			var token = data.token;
			$.ajax({
				url: '/api/estoque?login=' + usuario.login,
				type: 'GET',
				contentType: 'application/json',
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {
					let exists;
					data.forEach(function(retorno) {
						if (retorno.codigoBarras == codigoBarras) {
							$('#descricaoEditar').val(retorno.nomeProduto)
							$('#codigoBarrasEditar').val(retorno.codigoBarras)
						} 
					})
					if ($('#descricaoEditar').val() == '' ){
						toastMessage('Produto não cadastrado no seu Estoque. Cadastre o produto para editá-lo.', '#toast-text-editar', 'liveToastEditar')
					}
				},
				error: function(jqXHR) {
					var list = JSON.parse(jqXHR.responseText)

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


function editarProduto() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};

	const codigoBarras = $('#pesquisarProdutoEditar').val();
	const quantidade = $('#quantidadeEditar').val()
	const precoUnitario = $('#precoUnitarioEditar').val()
	console.log(codigoBarras)
	if (codigoBarras === '' || (quantidade == '' && precoUnitario == '')) {
		toastMessage('Preencha os campos corretamente.', '#toast-text', 'liveToast')
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
				codigoBarras: codigoBarras,
				quantidade: quantidade,
				precoUnitario: precoUnitario,
				nomeMercado: usuario.login
			}
			$.ajax({
				url: '/api/estoque',
				type: 'PUT',
				contentType: 'application/json',
				data: JSON.stringify(data),
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {
					 $('#quantidadeEditar').val("")
					 $('#precoUnitarioEditar').val("")
					 $('#descricaoEditar').val("")
					 $('#codigoBarrasEditar').val("")
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

function adicionarProduto() {
	const usuario = {
		login: localStorage.getItem('login'),
		senha: localStorage.getItem('senha')
	};
	const codigoBarras = $('#codigoBarrasAdd').val();
	const descricao = $('#descricaoAdd').val()
	if (codigoBarras === '' || descricao == '') {
		toastMessage('Preencha os campos corretamente.', '#toast-text-add', 'liveToastAdd')

		return 0;
	}

	const cadastroProduto = {
		cod_barras: codigoBarras,
		descricao: descricao
	}
	$.ajax({
		url: '/api/usuarios/auth',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(usuario),
		success: function(data) {
			var token = data.token;
			console.log(JSON.stringify(data))
			$.ajax({
				url: '/api/produtos',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(cadastroProduto),
				headers: {
					'Authorization': 'Bearer ' + token
				},
				success: function(data) {

					$('#descricaoAdd').val("")
					$('#codigoBarrasAdd').val("")

				},
				error: function(jqXHR) {
					var list = JSON.parse(jqXHR.responseText).errors
					list.forEach(function(error) {
						console.log(error)
					})
					toastMessage('Impossível salvar produto.', '#toast-text-add', 'liveToastAdd')
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