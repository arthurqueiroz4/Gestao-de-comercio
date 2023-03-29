const formulario = document.getElementById("form2");
formulario.addEventListener('submit', event => {
	event.preventDefault();
	console.log(document.getElementById("cnpj").value)
	const data = {
		cnpj: document.getElementById("cnpj").value,
		usuario: document.getElementById("usuario").value,
		senha: document.getElementById("senha").value
	}
	const promise = fetch("cadastrar", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(data)
	})
		.then(resp => {
			console.log(resp.status)
			if (resp.status == 200) {
				alert("Cadastro validado.");
			} else if (resp.status == 406) {
				alert("Cadastro inválido.\nUsuário já cadastrado.");
				throw new Error("Cadastro inválido");
			} else if (resp.status == 409){
				alert("Cadastro inválido.\nCNPJ inválido => Deve conter exatamente 14 números.");
				throw new Error("Cadastro inválido");
			}
			return resp.json()
		})
		.catch(e => console.error(e));
});