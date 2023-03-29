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
				alert("Cadastro validado");
			} else if (resp.status == 406) {
				alert("Cadastro invalido\nUsuário já cadastrado");
				throw new Error("Cadastro invalido");
			}
			return resp.json()
		})
		.catch(e => console.error(e));
});