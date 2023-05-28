function toastMessage(message, idText, idToast) {
	const toastLiveExample = document.getElementById(idToast)
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
	$(idText).text(message)
	toastBootstrap.show()
}

const usuario = {
	login: localStorage.getItem('login'),
	senha: localStorage.getItem('senha')
};

var token = localStorage.getItem("token");


function buscarProduto() {
	const codigoBarras = $('#pesquisarProduto').val();
	if (codigoBarras === '') {
		toastMessage('Código de barras não pode ser vazio.', '#toast-text', 'liveToast')
		return 0;
	}
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
			// var list = JSON.parse(jqXHR.responseText)
			// if(list.status == 403) {
			// 	window.location.href = "../index.html";
			// }
			const toastLiveExample = document.getElementById('liveToast')
			const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
			$('#toast-text').text('Código de barras não cadastrado. Cadastre o produto antes de adicioná-lo ao Estoque.')
			toastBootstrap.show()
		}
	});
}

function preencherTabela() {
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
			var list = JSON.parse(jqXHR.responseText)
			if(list.status == 403) {
				window.location.href = "../index.html";
			}
		}
	});
}

preencherTabela();

function adicionarEstoque() {
	const codigoBarras = $('#codigoBarras').val();
	const quantidade = $('#quantidade').val()
	const precoUnitario = $('#precoUnitario').val()
	if (codigoBarras === '' || quantidade == '' || precoUnitario == '') {
		toastMessage('Preencha os campos corretamente.', '#toast-text', 'liveToast')

		return 0;
	}
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
			console.log(data)
			$('#precoUnitario').val("")
			$('#quantidade').val("")
			preencherTabela()
		},
		error: function(jqXHR) {

			var list = JSON.parse(jqXHR.responseText)
			console.log(list)
			toastMessage('Impossível fazer cadastro desse produto no Estoque.', '#toast-text', 'liveToast')

		}
	});	
}

function buscarProdutoEditar() {
	const codigoBarras = $('#pesquisarProdutoEditar').val();
	if (codigoBarras === '') {
		toastMessage('Código de barras não pode ser vazio.', '#toast-text-editar', 'liveToastEditar')
		return 0;
	}

	$.ajax({
		url: '/api/estoque?login=' + usuario.login,
		type: 'GET',
		contentType: 'application/json',
		headers: {
			'Authorization': 'Bearer ' + token
		},
		success: function(data) {
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
}


function editarProduto() {
	const codigoBarras = $('#codigoBarrasEditar').val();
	const quantidade = $('#quantidadeEditar').val()
	const precoUnitario = $('#precoUnitarioEditar').val()
	console.log(codigoBarras)
	if (codigoBarras === '' || (quantidade == '' && precoUnitario == '')) {
		toastMessage('Preencha os campos corretamente.', '#toast-text', 'liveToast')
		return 0;
	}

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
}

function adicionarProduto() {
	
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
}
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