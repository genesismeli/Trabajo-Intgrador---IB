import React, { Component } from 'react';
import './createMedic.css';

class CreateMedic extends Component {
  constructor() {
    super();
    this.state = {
      name: '',
      lastName: '',
      registrationNumber: '',
      speciality: '',
      email: '',
      userName: '',
      password: '',
      user: 'USER'
    };
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  handleCreateMedic = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }

    // Aquí puedes enviar una solicitud HTTP al backend para crear un nuevo médico
    const { name, lastName, registrationNumber, speciality, email, userName, password } = this.state;
    const medicData = { name, lastName, registrationNumber, speciality, email, userName, password };

    // Envía la solicitud HTTP al backend para crear un nuevo médico
    // Puedes usar fetch, axios u otra biblioteca de HTTP aquí
    fetch('http://localhost:8081/medic/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(medicData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al crear al médico');
        }
        return response.json();
      })
      .then((data) => {
        console.log(data); // Maneja la respuesta exitosa del servidor aquí
        // Redirige al usuario a la página de lista de médicos después de crear al médico
        window.location.href = '/medic/list';
      })
      .catch((error) => {
        console.error('Error al crear al médico:', error);
      });
  };

  render() {
    return (
      <div className="create-medic-container">
        <h2>Crear Nuevo Médico</h2>
        <form onSubmit={this.handleCreateMedic}>
          <div class="form-row">
            <label htmlFor="name">Nombre:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={this.state.name}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div class="form-row">
            <label htmlFor="lastName">Apellido:</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={this.state.lastName}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div class="form-row">
            <label htmlFor="registrationNumber">Número de Registro:</label>
            <input
              type="text"
              id="registrationNumber"
              name="registrationNumber"
              value={this.state.registrationNumber}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div class="form-row">
            <label htmlFor="speciality">Especialidad:</label>
            <input
              type="text"
              id="speciality"
              name="speciality"
              value={this.state.speciality}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div class="form-row">
            <label htmlFor="email">Correo Electrónico:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={this.state.email}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div class="form-row">
            <label htmlFor="userName">Usuario:</label>
            <input
               type="text"
               id="userName"
               name="userName"
               value={this.state.userName}
               onChange={this.handleInputChange}
               required
            />
          </div>
          <div class="form-row">
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
          <button className="create-medic-button">Crear Médico</button>
        </form>
      </div>
    );
  }
}

export default CreateMedic;
