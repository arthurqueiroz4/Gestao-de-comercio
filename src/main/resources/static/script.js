const form = document.querySelector('#form1');
const button = document.querySelector('#button');

button.addEventListener('click', (event) => {
  event.preventDefault();

  const usuario = {
    login: document.querySelector('#mercado').value,
    senha: document.querySelector('#senha').value
  };

  fetch('/api/usuarios/auth', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(usuario)
  })
  .then(response => {
    if (response.ok) {
      // Sucesso na requisição
      console.log('Login realizado com sucesso!');
    } else {
      // Erro na requisição
      console.error('Erro ao realizar login.');
    }
  })
  .catch(error => {
    console.error('Erro ao realizar login: ' + error);
  });
});
