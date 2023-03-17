
const formu = document.getElementById("form1");
formu.addEventListener('submit', event =>{
    event.preventDefault();
    const formData = new FormData(formu);
    const data = Object.fromEntries(formData);
    fetch("validarlogin", {
        method:"POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(resp => resp.json()).then(data => console.log(data))
})