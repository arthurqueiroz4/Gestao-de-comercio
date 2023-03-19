const formu = document.getElementById("form1");
let id_user;

formu.addEventListener('submit', event => {
    event.preventDefault();
    const formData = new FormData(formu);
    const data = {
        usuario: formData.get('usuario'),
        senha: formData.get('senha')
    };
    console.log(data);
    const idPromise = fetch("validarlogin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(resp => {
		console.log(resp.status)
		if (resp.status==200){
			console.log("Login validado");
		} else if (resp.status==406){
			throw new Error("Login invalido");
		}
		return resp.json()
	})
    .then(data => {
		localStorage.setItem("id", JSON.stringify(data.idLogin))
	})
    .catch(error => console.error(error));
});
