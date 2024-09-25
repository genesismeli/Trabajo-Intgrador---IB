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

  handleFileChange = (event) => {
      const file = event.target.files[0];
      this.setState({ signatureImage: file });
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
    const medicData = { name, lastName, registrationNumber, speciality, email, userName, password};

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
            <select className="speciality"
              id="speciality"
              name="speciality"
              value={this.state.speciality}
              onChange={this.handleInputChange}
              required
            >
              <option value="">Selecciona una opción</option>
              <option value="ALERGIA_E_INMUOLOGIA_PEDIATRICA">ALERGIA E INMUOLOGIA PEDIATRICA</option>
              <option value="ALERGIA_E_INMUNOLOGIA">ALERGIA E INMUNOLOGIA</option>
              <option value="ANATOMIA_PATOLOGICA">ANATOMIA PATOLOGICA</option>
              <option value="ANESTESIOLOGIA">ANESTESIOLOGIA</option>
              <option value="ANGIOLOGA_GENERAL_Y_HEMODINAMIA">ANGIOLOGA GENERAL Y HEMODINAMIA</option>
              <option value="CARDIOLOGIA">CARDIOLOGIA</option>
              <option value="CARDIOLOGIA_INFANTIL">CARDIOLOGIA INFANTIL</option>
              <option value="CIRUGIA_CARDIOVASCULAR INFANTIL">CIRUGIA CARDIOVASCULAR INFANTIL</option>
              <option value="CIRUGIA_CARDIOVASCULAR">CIRUGIA CARDIOVASCULAR</option>
              <option value="CIRUGIA_DE CABEZA_Y_CUELLO">CIRUGIA DE CABEZA Y CUELLO</option>
              <option value="CIRUGIA_DE_TORAX">CIRUGIA DE TORAX</option>
              <option value="CIRUGIA_GENERAL">CIRUGIA GENERAL</option>
              <option value="CIRUGIA_INFANTIL">CIRUGIA INFANTIL</option>
              <option value="CIRUGIA_PLASTICA">CIRUGIA PLASTICA</option>
              <option value="CIRUGIA_VASCULAR_PERIFERICA">CIRUGIA VASCULAR PERIFERICA</option>
              <option value="CLINICA_MEDICA">CLINICA MEDICA</option>
              <option value="COLOPROCTOLOGÍA">COLOPROCTOLOGÍA</option>
              <option value="DERMATOLOGIA_PEDIATRICA">DERMATOLOGIA PEDIATRICA</option>
              <option value="DERMATOLOGIA">DERMATOLOGIA</option>
              <option value="DIAGNOSTICO_POR_IMAGENES">DIAGNOSTICO POR IMAGENES</option>
              <option value="ELECTRO_FSIOLOGIA_CARDIACA">ELECTRO FSIOLOGIA CARDIACA</option>
              <option value="EMERGENTOLOGIA">EMERGENTOLOGIA</option>
              <option value="ENDOCRINOLOGIA">ENDOCRINOLOGIA</option>
              <option value="ENDOCRINOLOGIA_INFANTIL">ENDOCRINOLOGIA INFANTIL</option>
              <option value="FARMACOLOGIA_CLINICA">FARMACOLOGIA CLINICA</option>
              <option value="FISIATRA">FISIATRA</option>
              <option value="GASTROENTEROLOGIA">GASTROENTEROLOGIA</option>
              <option value="GASTROENTEROLOGIA_INFANTIL">GASTROENTEROLOGIA INFANTIL</option>
              <option value="GENETICA_MEDICA">GENETICA MEDICA</option>
              <option value="HEMATO_ONCOLOGIA_PEDIATRICA">HEMATO-ONCOLOGIA PEDIATRICA</option>
              <option value="HEMOTERAPIA_E_INMUNOHEMATOLOGIA">HEMOTERAPIA E INMUNOHEMATOLOGIA</option>
              <option value="HEMATOLOGIA_PEDIATRICA">HEMATOLOGIA PEDIATRICA</option>
              <option value="HEPATOLOGIA">HEPATOLOGIA</option>
              <option value="INFECTOLOGIA">INFECTOLOGIA</option>
              <option value="INFECTOLOGIA_INFANTIL">INFECTOLOGIA INFANTIL</option>
              <option value="MEDICINA_DEL_DEPORTE">MEDICINA DEL DEPORTE</option>
              <option value="MEDICINA_DEL_TRABAJO">MEDICINA DEL TRABAJO</option>
              <option value="MEDICINA_GRAL_Y/O_MEDICINA_DE_FAMILIA">MEDICINA GRAL Y/O MEDICINA DE FAMILIA</option>
              <option value="MEDICINA_LEGAL">MEDICINA LEGAL</option>
              <option value="MEDICINA_NUCLEAR">MEDICINA NUCLEAR</option>
              <option value="MEDICINA_PALIATIVA">MEDICINA PALIATIVA</option>
              <option value="NEFROLOGIA">NEFROLOGIA</option>
              <option value="NEFROLOGIA_INFANTIL">NEFROLOGIA INFANTIL</option>
              <option value="NEONATOLOGIA">NEONATOLOGIA</option>
              <option value="NEUMONOLOGIA">NEUMONOLOGIA</option>
              <option value="NEUMONOLOGIA_INFANTIL">NEUMONOLOGIA INFANTIL</option>
              <option value="NEUROCIRUGIA">NEUROCIRUGIA</option>
              <option value="NEUROLOGIA">NEUROLOGIA</option>
              <option value="NEUROLOGIA_INFANTIL">NEUROLOGIA INFANTIL</option>
              <option value="NUTRICION">NUTRICION</option>
              <option value="OBSTETRICIA">OBSTETRICIA</option>
              <option value="OFTALMOLOGIA">OFTALMOLOGIA</option>
              <option value="ONCOLOGIA">ONCOLOGIA</option>
              <option value="ORTOPEDIA_Y_TRAMATOLOGIA_INFANTIL">ORTOPEDIA Y TRAMATOLOGIA INFANTIL</option>
              <option value="ORTOPEDIA_Y_TRAUMATOLOGIA">ORTOPEDIA Y TRAUMATOLOGIA</option>
              <option value="OTORRINOLARINGOLOGIA">OTORRINOLARINGOLOGIA</option>
              <option value="PEDIATRIA">PEDIATRIA</option>
              <option value="PSIQUIATRIA_INFANTO_JUVENIL">PSIQUIATRIA INFANTO JUVENIL</option>
              <option value="PSIQUIATRIA">PSIQUIATRIA</option>
              <option value="RADIOTERAPIA">RADIOTERAPIA</option>
              <option value="REUMATOLOGIA">REUMATOLOGIA</option>
              <option value="REUMATOLOGIA_INFANTL">REUMATOLOGIA INFANTL</option>
              <option value="TERAPIA_INTENSIVA">TERAPIA INTENSIVA</option>
              <option value="TERAPIA_INTENSIVA_INFANTIL">TERAPIA INTENSIVA INFANTIL</option>
              <option value="TOCOGINECOLOGIA">TOCOGINECOLOGIA</option>
              <option value="TOXICOLOGIA">TOXICOLOGIA</option>
              <option value="UROLOGIA">UROLOGIA</option>
            </select>
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
