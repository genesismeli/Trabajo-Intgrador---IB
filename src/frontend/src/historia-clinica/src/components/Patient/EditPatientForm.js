import React, { Component } from 'react';
import './editPatientForm.css';

class EditPatientForm extends Component {
  constructor(props) {
    super(props);
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

  componentDidMount() {
    const { id } = this.props.patient;
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }

 // Realiza una solicitud para obtener los datos del paciente
    fetch(`http://localhost:8081/patient/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        const formattedDate = new Date(data.birthdate).toISOString().split('T')[0];
        // Actualiza el estado con los datos del paciente obtenidos del servidor
        this.setState({
          name: data.name,
          lastName: data.lastName,
          dni: data.dni,
          birthdate: formattedDate,
          gender: data.gender,
          address: data.adress,
          phone: data.phone,
          email: data.email,
        });
      })
      .catch((error) => {
        console.error('Error al obtener los datos del paciente:', error);
      });
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


  handleUpdatePatient = (event) => {
    event.preventDefault();
    const { id } = this.props.patient;
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }
    if (!this.validateForm()) {
          return;
    }

    // Aquí puedes enviar una solicitud HTTP al backend para editar un paciente
    const { name, lastName, dni, birthdate, gender, address, phone, email } = this.state;
    const patientData = { name, lastName, dni, birthdate, gender, adress: address, phone, email };

    fetch(`http://localhost:8081/patient/update/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(patientData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al editar al paciente');
        }
        return response.json();
      })
      .then((data) => {
        console.log(data); // Maneja la respuesta exitosa del servidor aquí
        // Redirige al usuario a la página de lista de pacientes después de editar al paciente
        window.location.reload();
        //window.location.href = '/patient/list';

      })
      .catch((error) => {
        console.error('Error al editar al paciente:', error);
      });

    this.props.onClose();
    window.location.reload();
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
    const { name, lastName, dni, birthdate, gender, address, phone, email, validationErrors } = this.state;

    return (
      <div className="edit-patient-form-container">
        <h2 id="sesion">Editar Paciente</h2>
        <form onSubmit={this.handleUpdatePatient}>
          <div className="form-field">
            <label htmlFor="name">Nombre:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={name}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.name && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.name}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="lastName">Apellido:</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={lastName}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.lastname && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.lastname}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="dni">DNI:</label>
            <input
              type="text"
              id="dni"
              name="dni"
              value={dni}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.dni && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.dni}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="birthdate">Fecha de Nacimiento:</label>
            <input
              type="date"
              id="birthdate"
              name="birthdate"
              value={birthdate}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.birthdate && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.birthdate}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="gender">Sexo:</label>
            <select
              id="gender"
              name="gender"
              value={gender}
              onChange={this.handleInputChange}
              required
            >
              <option value="">Selecciona una opción</option>
              <option value="MASCULINO">Masculino</option>
              <option value="FEMENINO">Femenino</option>
              <option value="NO ESPECIFICAR">No Especificar</option>
            </select>
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.gender && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.gender}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="address">Dirección:</label>
            <input
              type="text"
              id="address"
              name="address"
              value={address}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.address && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.address}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="phone">Teléfono:</label>
            <input
              type="text"
              id="phone"
              name="phone"
              value={phone}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.phone && <p  style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.phone}</p>}
          </div>
          <div className="form-field">
            <label htmlFor="email">Correo Electrónico:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={this.handleInputChange}
              required
            />
            {/* Muestra el mensaje de error debajo del campo si existe */}
            {validationErrors.email && <p style={{ color: '#1DBEB4' }} className="error-message">{validationErrors.email}</p>}
          </div>

          <button className="edit-patient-button">Guardar Cambios</button>
          <button className="close-patient-button" onClick={this.props.onClose}>Cerrar</button>
        </form>
      </div>
    );
  }
}
export default EditPatientForm;
