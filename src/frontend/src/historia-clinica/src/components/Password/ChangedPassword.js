import React, { Component } from 'react';
import './styles.css';
import Message from '../Message.js';

class ChangePassword extends Component {
  constructor() {
    super();
    this.state = {
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      errorMessage: ''
    };
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value, errorMessage: '' });
  };



handleChangePassword = (event) => {
    event.preventDefault();
    const { oldPassword, newPassword, confirmPassword } = this.state;

    // Validación de que las contraseñas coinciden
    if (newPassword !== confirmPassword) {
        this.setState({ errorMessage: 'Las contraseñas no coinciden' });
        return;
    }

    // Obtener el token JWT del almacenamiento local
    const token = localStorage.getItem('token');

    // Aquí puedes enviar una solicitud HTTP al backend para cambiar la contraseña
    const passwordData = { oldPassword, newPassword };

    fetch('http://localhost:8081/auth/change-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Agrega el token JWT al encabezado de la solicitud
        },
        body: JSON.stringify(passwordData),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data); // Maneja la respuesta del servidor aquí

            // Verifica si el cambio de contraseña fue exitoso
            if (data.message === 'Password changed successfully') {
                localStorage.removeItem('token');
                // Redirige al usuario a la página de inicio de sesión
                window.location.href = '/';
            } else {
                // Manejar caso de error
                console.error('Error al cambiar la contraseña:', data.message);
                this.setState({ errorMessage: 'Error al cambiar la contraseña' });
            }
        })
        .catch((error) => {
            console.error('Error al cambiar la contraseña:', error);
            this.setState({ errorMessage: 'Error al cambiar la contraseña' });
        });
};

  render() {
    return (
      <div className="change-password-container">
        <h2 className="tittle">Cambiar Contraseña</h2>
        {this.state.errorMessage && (
          <Message text={this.state.errorMessage} onClose={() => this.setState({ errorMessage: '' })} />
        )}
        <form onSubmit={this.handleChangePassword}>
          <div className="form-group">
            <label htmlFor="oldPassword">Contraseña Antigua:</label>
            <input
              type="password"
              id="oldPassword"
              name="oldPassword"
              value={this.state.oldPassword}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="newPassword">Nueva Contraseña:</label>
            <input
              type="password"
              id="newPassword"
              name="newPassword"
              value={this.state.newPassword}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="confirmPassword">Confirmar Nueva Contraseña:</label>
            <input
              type="password"
              id="confirmPassword"
              name="confirmPassword"
              value={this.state.confirmPassword}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <button className="change-password-button">Cambiar Contraseña</button>
        </form>
      </div>
    );
  }
}

export default ChangePassword;
