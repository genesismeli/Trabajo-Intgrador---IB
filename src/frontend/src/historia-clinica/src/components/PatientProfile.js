import React, { Component } from 'react';
import { Link } from 'react-router-dom';


class PatientProfile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      patientData: null,
    };
  }

  componentDidMount() {
    const { patientId } = this.props.match.params;

    // Hacer una solicitud al backend para obtener los datos del paciente
    fetch(`http://localhost:8081/patient/${patientId}`)
      .then((response) => response.json())
      .then((data) => {
        this.setState({ patientData: data });
      })
      .catch((error) => {
        console.error('Error al obtener los datos del paciente:', error);
      });
  }

  render() {
   // const { patientData } = this.state;
    return (
      <div className="patient-profile">
        <h2>Perfil del Paciente</h2>
        <div className="patient-details">
          <h3>Información del Paciente</h3>
          {/* Mostrar los detalles del paciente */}
          {/* patientData.name, patientData.lastName, etc. */}
        </div>
        <div className="tabs">
          <Link to={`/diagnosis/patient/{patientId}`}>Diagnósticos</Link>
        </div>

      </div>
    );
  }
}

export default PatientProfile;
