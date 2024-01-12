document.addEventListener("DOMContentLoaded", function () {
  const formularioRegistro = document.getElementById("registration-form");

  formularioRegistro.addEventListener("submit", function (event) {
    event.preventDefault(); // Evita el envío del formulario por defecto

    // Obtén los valores de los campos del formulario
    const name = document.getElementById("name").value;
    const userName = document.getElementById("userName").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const roles = []; // Agrega los roles que desees aquí

    // Crea un objeto con los datos del formulario
    const registroData = {
      name: name,
      userName: userName,
      email: email,
      password: password,
      roles: roles, // Puedes modificar esto según tu lógica
    };

    // Realiza una solicitud POST al servidor
    fetch("/auth/new", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(registroData),
    })
      .then((response) => response.json())
      .then((data) => {
        // Maneja la respuesta del servidor aquí
        if (data.message === "User created successfully!!") {
          alert("Registro exitoso. Inicia sesión.");
          // Redirige al usuario a la página de inicio de sesión u otra página relevante
          window.location.href = "/login.html";
        } else {
          alert("Error en el registro. Por favor, inténtalo de nuevo.");
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud:", error);
      });
  });
});
