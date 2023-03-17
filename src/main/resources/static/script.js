const formu = document.getElementById("form1");
const usuario = document.getElementById("usuario");
const senha = document.getElementById("password");

formu.addEventListener('submit', event =>{
	//const data_password = new FormData();
	//onst password_final = Object.fromEntries(password);
    event.preventDefault();
    const data = JSON.stringify({
			usuario: usuario.value,
			senha: senha.value
		});
	$.ajax({
		method: "GET",
		url: "validarlogin",
		data: data,
		success: function(response) {
			alert("LOGIN FEITO COM SUCESSO.")
		}
	}).fail(function(xhr, status, errorThrow) {
		alert("LOGIN NÃO ENCONTRADO\n" + xhr.responseText);
	})
	})
	/*
    fetch("validarlogin", {
        method:"GET",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
			usuario: usuario.value,
			senha: senha.value
		})
    }).then(resp => resp).then(data => console.log(data))
})
/*

function validarUsuario(){
	console.log("entrou");
	sleep(1000);
	const usuario = $("#form1").value();
	const senha = $("#password").value();

	$.ajax({
		method: "GET",
		url: "validarlogin",
		data: JSON.stringify({
			usuario: usuario,
			senha: senha
		}),
		contentType: "application/json; charset=utf-8",
		success: function(response) {
			alert("LOGIN FEITO COM SUCESSO.")
		}
	}).fail(function(xhr, status, errorThrow) {
		alert("LOGIN NÃO ENCONTRADO" + xhr.responseText);
	})
}
*/
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}