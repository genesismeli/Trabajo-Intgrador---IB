import React, { Component } from 'react';
import './styles.css';
import Message from '../Message.js';
class Login extends Component {
  constructor() {
    super();
    this.state = {
      userName: '',
      password: '',
      errorMessage: '',


    };
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value, errorMessage: '' });
  };

  handleLogin = (event) => {
    event.preventDefault();
    // Aquí puedes enviar una solicitud HTTP al backend para el inicio de sesión
    const { userName, password } = this.state;
    const userData = { userName, password };

    // Envía la solicitud HTTP al backend para el inicio de sesión
    // Puedes usar fetch, axios u otra biblioteca de HTTP aquí
    fetch('http://localhost:8081/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userData),
    })
      .then((response) => response.json())
            .then((data) => {
              if (data.token) {
                localStorage.setItem('token', data.token);
                // Verifica si la respuesta contiene un token y el rol del usuario
                if (data.authorities.some(role => role.authority === 'ROLE_USER')) {
                  // Redirige a la lista de pacientes para el usuario
                  window.location.href = '/patient/list';
                } else if (data.authorities.some(role => role.authority === 'ROLE_ADMIN')) {
                  // Redirige a la lista de médicos para el médico
                  window.location.href = '/medic/list';
                } else {
                  console.error('Rol desconocido:', data.authorities);
                }
              } else {
                console.error('Error: No se recibió un token después del inicio de sesión.');
              }
            })
            .catch((error) => {
              console.error('Error al iniciar sesión:', error);
              this.setState({ errorMessage: 'Usuario o contraseña incorrectos' });
            });
      };

  render() {
     if (this.state.redirectToMedicList) {
          // Redirige a la lista de médicos
          window.location.href = '/medic/list';
        }

     return (
        <div className="login-container">
          <h2 id="sesion">Iniciar Sesión</h2>
        {this.state.errorMessage && (
          <Message text={this.state.errorMessage} onClose={() => this.setState({ errorMessage: '' })} />
        )}
          <form onSubmit={this.handleLogin}>
            <div className="form-group">
              <label htmlFor="username">Nombre de usuario:</label>
              <input
                type="text"
                id="userName"
                name="userName"
                value={this.state.userName}
                onChange={this.handleInputChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="password">Contraseña:</label>
              <input
                type="password"
                id="password"
                name="password"
                value={this.state.password}
                onChange={this.handleInputChange}
                required
              />
            </div>
            <button className="session-button">Iniciar Sesión</button>
          </form>
        </div>
      );
    }
  }

export default Login;
