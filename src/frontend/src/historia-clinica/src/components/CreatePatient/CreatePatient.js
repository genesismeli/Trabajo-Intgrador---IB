import React, { Component } from 'react';
import './createPatient.css'; // Asegúrate de tener el archivo CSS adecuado

class CreatePatient extends Component {
  constructor() {
    super();
    this.state = {
      name: '',
      lastName: '',
      dni: '',
      birthdate: '',
      gender: '',
      address: '',
      phone: '',
      email: '',
      validationErrors: {},
    };
  }

  handleInputChange = (event) => {
      const { name, value } = event.target;
      this.setState((prevState) => {
        // Clonar el objeto de errores de validación actual
        const newValidationErrors = { ...prevState.validationErrors };
        // Eliminar el mensaje de error para el campo actual
        delete newValidationErrors[name];
        // Actualizar el estado con el nuevo valor y mensajes de error
        return { [name]: value, validationErrors: newValidationErrors };
      });
    };

  handleCreatePatient = (event) => {
    event.preventDefault();
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }
    if (!this.validateForm()) {
          return;
    }

    const { name, lastName, dni, birthdate, gender, address, phone, email } = this.state;
    const patientData = { name, lastName, dni, birthdate, gender, adress: address, phone, email };

    fetch('http://localhost:8081/patient/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(patientData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al crear al paciente');
        }
        return response.json();
      })
      .then((data) => {
        console.log(data); // Maneja la respuesta exitosa del servidor aquí
        // Redirige al usuario a la página de lista de pacientes después de crear al paciente
        window.location.href = '/patient/list';
      })
      .catch((error) => {
        console.error('Error al crear al paciente:', error);
      });
  };

  validateForm = () => {
        // Realiza todas las validaciones necesarias y devuelve true si el formulario es válido
        const { birthdate, email, phone, dni } = this.state;
        const currentDate = new Date();
        const newValidationErrors = {};

        // Validación de fecha de nacimiento
        const birthdateDate = new Date(birthdate);
        if (birthdateDate > currentDate) {
          newValidationErrors.birthdate = 'La fecha de nacimiento no puede ser posterior a la fecha actual.';

        }

        // Validación de formato de email
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
          newValidationErrors.email='El formato del correo electrónico no es válido.';

        }

        // Validación de teléfono
        const phoneRegex = /^\d{10,}$/;
        if (!phoneRegex.test(phone)) {
          newValidationErrors.phone='El teléfono debe tener al menos 10 caracteres numéricos.';

        }

        // Validación de DNI (por ejemplo, mínimo 8 caracteres numéricos)
        const dniRegex = /^\d{8,}$/;
        if (!dniRegex.test(dni)) {
          newValidationErrors.dni='El DNI debe tener al menos 8 caracteres numéricos.';

        }

        this.setState({ validationErrors: newValidationErrors });

            // Devuelve true si no hay mensajes de error
        return Object.keys(newValidationErrors).length === 0;
        // Si todas las validaciones pasan, el formulario es válido

   };



  render() {
  const { validationErrors } = this.state;
    return (
      <div className="create-patient-container">
        <h2>Crear Nuevo Paciente</h2>
        <form onSubmit={this.handleCreatePatient}>
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
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.name && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.name}</p>}
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
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.lastName && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.lastName}</p>}
          </div>
          <div class="form-row">
            <label htmlFor="dni">DNI:</label>
            <input
              type="text"
              id="dni"
              name="dni"
              value={this.state.dni}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
                        {validationErrors.dni && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.dni}</p>}
          </div>
          <div class="form-row">
            <label htmlFor="birthdate">Fecha de Nacimiento:</label>
            <input
              type="date"
              id="birthdate"
              name="birthdate"
              value={this.state.birthdate}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.birthdate && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.birthdate}</p>}
          </div>
          <div class="form-row">
            <label htmlFor="gender">Sexo:</label>
            <select className="gender"
              id="gender"
              name="gender"
              value={this.state.gender}
              onChange={this.handleInputChange}
              required
            >
              <option value="">Selecciona una opción</option>
              <option value="MASCULINO">Masculino</option>
              <option value="FEMENINO">Femenino</option>
              <option value="NO ESPECIFICAR">NO Especificar</option>

            </select>
          </div>
          <div class="form-row">
            <label htmlFor="address">Dirección:</label>
            <input
              type="text"
              id="address"
              name="address"
              value={this.state.address}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.address && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.address}</p>}
          </div>
          <div class="form-row">
            <label htmlFor="phone">Teléfono:</label>
            <input
              type="text"
              id="phone"
              name="phone"
              value={this.state.phone}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.phone && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.phone}</p>}
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
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.email && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.email}</p>}
          </div>
          <button className="create-patient-button">Crear Paciente</button>
        </form>
      </div>
    );
  }
}

export default CreatePatient;
