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
			toastMessage('Código de barras não cadastrado. Cadastre o produto antes de adicioná-lo ao Estoque.', '#toast-text', 'liveToast')
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
// modal de pesquisar produto
function adicionarEstoque() {
	const codigoBarras = $('#codigoBarras').val();
	const quantidade = $('#quantidade').val()
	const precoUnitario = $('#precoUnitario').val()
	if (codigoBarras === '') {
		toastMessage('Pesquise o produto antes de adicionar!', '#toast-text', 'liveToast')

		return 0;
	}

	var data = {
		codigoBarras: codigoBarras,
		quantidade: quantidade,
		precoUnitario: precoUnitario,
		nomeMercado: usuario.login
	}
	if($("#quantidade").val() == "" || $("#precoUnitario").val() == ""){
		toastMessage('Insira a quantidade e o preço unitário!', '#toast-text', 'liveToast')
		return 0;
	}
	if($("#quantidade").val() < 0 || $("#precoUnitario").val() < 0){
		toastMessage('Insira a quantidade e o preço unitário com valores positivos!', '#toast-text', 'liveToast')
		return 0;
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
			$("#exampleModal").modal('hide')
			preencherTabela()
		},
		error: function(jqXHR) {

			var list = JSON.parse(jqXHR.responseText)
			
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
			toastMessage('Produto não cadastrado no seu Estoque. Cadastre o produto para editá-lo.', '#toast-text-editar', 'liveToastEditar')
		}
	});
}

// modal de editar produto
function editarProduto() {
	const codigoBarras = $('#codigoBarrasEditar').val();
	const quantidade = $('#quantidadeEditar').val()
	const precoUnitario = $('#precoUnitarioEditar').val()
	if (codigoBarras === '') {
		toastMessage('Pesquise o produto antes de editar!', '#toast-text-editar', 'liveToastEditar')
		return 0;
	}
	var data = {
		codigoBarras: codigoBarras,
		quantidade: quantidade,
		precoUnitario: precoUnitario,
		nomeMercado: usuario.login
	}
	if($("#quantidadeEditar").val() == "" && $("#precoUnitarioEditar").val() == ""){
		toastMessage('Insira quantidade ou preço!', '#toast-text-editar', 'liveToastEditar')
	}
	if($("#quantidadeEditar").val() < 0 || $("#precoUnitarioEditar").val() < 0){
		toastMessage('Insira a quantidade e o preço unitário com valores positivos!', '#toast-text-editar', 'liveToastEditar')
		return 0;
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
			$("#modalEditar").modal('hide');
			preencherTabela()
		},
		error: function(jqXHR) {
			var list = JSON.parse(jqXHR.responseText)
			
			toastMessage('Impossível fazer cadastrado desse produto no Estoque.')

		}
	});
}
// modal de add produto
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
			$("#modalAdicionar").modal('hide');
		},
		error: function(jqXHR) {
			var list = JSON.parse(jqXHR.responseText).errors
			
			toastMessage('Impossível salvar produto.', '#toast-text-add', 'liveToastAdd')
		}
	});
}
function fecharEditar(){
	$('#quantidadeEditar').val("")
	$('#precoUnitarioEditar').val("")
	$('#descricaoEditar').val("")
	$('#codigoBarrasEditar').val("")
	$("#pesquisarProdutoEditar").val("")
}
function fecharEstoque(){
	$("#pesquisarProduto").val("")
	$("#descricao").val("")
	$("#codigoBarras").val("")
	$("#quantidade").val("")
	$("#precoUnitario").val("")
}
function fecharAdd(){
	$("#descricaoAdd").val("")
	$("#codigoBarrasAdd").val("")
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

var meuModal1 = document.getElementById("exampleModal");
meuModal1.addEventListener("hidden.bs.modal", function (event) {
	fecharEstoque()
});

var meuModal2 = document.getElementById("modalEditar");
meuModal2.addEventListener("hidden.bs.modal", function (event) {
	fecharEditar()
});

var meuModal3 = document.getElementById("modalAdicionar");
meuModal3.addEventListener("hidden.bs.modal", function (event) {
	fecharAdd()
});