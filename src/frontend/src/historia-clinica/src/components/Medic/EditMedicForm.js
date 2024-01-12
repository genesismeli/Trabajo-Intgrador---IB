import React, { Component } from 'react';
import './editMedicForm.css';
class EditMedicForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: '',
      lastName: '',
      registrationNumber: '',
      speciality: '',
      email: '',
    };
  }

  componentDidMount() {
    const { id } = this.props.medic;
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }

    // Realiza una solicitud para obtener los datos del médico
    fetch(`http://localhost:8081/medic/${id}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        // Actualiza el estado con los datos del médico obtenidos del servidor
        this.setState({
          name: data.name,
          lastName: data.lastName,
          registrationNumber: data.registrationNumber,
          speciality: data.speciality,
          email: data.email,
        });
      })
      .catch((error) => {
        console.error('Error al obtener los datos del médico:', error);
      });
  }

  handleInputChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  handleUpdateMedic = (event) => {
    event.preventDefault();
    const { id } = this.props.medic;
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Error: No se encontró el token de autenticación.');
      return;
    }

    // Aquí puedes enviar una solicitud HTTP al backend para editar un médico
    const { name, lastName, registrationNumber, speciality, email } = this.state;
    const medicData = { name, lastName, registrationNumber, speciality, email };

    fetch(`http://localhost:8081/medic/update/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(medicData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Error al editar al médico');
        }
        return response.json();
      })
      .then((data) => {
        console.log(data); // Maneja la respuesta exitosa del servidor aquí
        // Redirige al usuario a la página de lista de médicos después de editar al médico
        window.location.href = '/medic/list';
        this.updateMedicList();
      })
      .catch((error) => {
        console.error('Error al editar al médico:', error);
      });

    this.props.onClose();
  };

  render() {
    const { name, lastName, registrationNumber, speciality, email } = this.state;

    return (
      <div className="edit-medic-form-container">
        <h2 id="sesion">Editar Médico</h2>
        <form onSubmit={this.handleUpdateMedic}>
          <div>
            <label htmlFor="name">Nombre:</label>
            <input
              type="text"
              id="name"
              name="name"
              value={name}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div>
            <label htmlFor="lastName">Apellido:</label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              value={lastName}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div>
            <label htmlFor="registrationNumber">Número de Registro:</label>
            <input
              type="text"
              id="registrationNumber"
              name="registrationNumber"
              value={registrationNumber}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div>
            <label htmlFor="speciality">Especialidad:</label>
            <input
              type="text"
              id="speciality"
              name="speciality"
              value={speciality}
              onChange={this.handleInputChange}
              required
            />
          </div>
          <div>
            <label htmlFor="email">Correo Electrónico:</label>
            <input
              type="email"
              id="email"
              name="email"
              value={email}
              onChange={this.handleInputChange}
              required
            />
          </div>

          <button className="edit-medic-button">Guardar Cambios</button>
          <button className="close-medic-button" onClick={this.props.onClose}>Cerrar</button>

        </form>
      </div>
    );
  }
}

export default EditMedicForm;
