import React, { Component } from 'react';
import EditPatientForm from './EditPatientForm';
import DeletePatientForm from './DeletePatientForm';
import TrashIcon from '../../assets/icons/trash-icon.svg';
import PencilIcon from '../../assets/icons/pencil-icon.svg';
import FormIcon from '../../assets/icons/form-icon.svg';
import AntIcon from '../../assets/icons/flecha-ant-icon.svg';
import SigIcon from '../../assets/icons/flecha-adel-icon.svg';
import Modal from 'react-modal';
import './patientList.css';

Modal.setAppElement('#root');

class PatientList extends Component {
  constructor() {
    super();
    this.state = {
      patients: [], // Almacena la lista de pacientes
      selectedPatient: null,
      selectedPatientDelete : null,
      isEditModalOpen: false,
      isDeleteModalOpen: false,
      currentPage: 0,
      totalPages: 1,
      userRoles: [],
      searchName: '', // Nuevo estado para el primer nombre de búsqueda
      searchLastName: '', // Nuevo estado para el apellido de búsqueda
      searchDni: '', // Nuevo estado para el DNI de búsqueda
    };
  }


  componentDidMount() {

      const { currentPage } = this.state;

            // Obtener roles del usuario
            fetch('http://localhost:8081/user/authorities', {
              method: 'GET',
              headers: {
                'Authorization': `Bearer ${localStorage.getItem('token')}`,
              },
            })
              .then((response) => response.json())
              .then((data) => {
                this.setState({
                  userRoles: data.model,

                });
              })
              .catch((error) => {
                console.error('Error al obtener los roles del usuario:', error);
              });

              fetch(`http://localhost:8081/patient/all?page=${currentPage}`)
                .then((response) => response.json())
                .then((data) => {
                  this.setState({
                  patients: data.content,
                  totalPages: data.totalPages,
                   });
                })
                .catch((error) => {
                  console.error('Error al obtener la lista de pacientes:', error);
                });
            };

    handleSearchNameChange = (event) => {
        this.setState({ searchName: event.target.value });
      };

      handleSearchLastNameChange = (event) => {
        this.setState({ searchLastName: event.target.value });
      };

      handleSearchDniChange = (event) => {
        this.setState({ searchDni: event.target.value });
      };

    handleSearch = () => {
        this.updatePatientList();
    };

    handleClearFilters = () => {
      // Limpiar los estados de búsqueda
      this.setState(
        {
          searchName: '',
          searchLastName: '',
          searchDni: '',
        },
        () => {
          // Después de limpiar los filtros, cargar la lista completa de pacientes
          const { currentPage } = this.state;

          fetch(`http://localhost:8081/patient/all?page=${currentPage}`)
            .then((response) => response.json())
            .then((data) => {
              this.setState({
                patients: data.content,
                totalPages: data.totalPages,
              });
            })
            .catch((error) => {
              console.error('Error al obtener la lista de pacientes:', error);
            });
        }
      );
    };


 updatePatientList = () => {
   const { searchName, searchLastName, searchDni, currentPage } = this.state;

   // Construir la URL con el punto final común
   let url = `http://localhost:8081/patient/all?page=${currentPage}`;

   // Inicializar la función de procesamiento de datos
   let processData = (data) => {
     this.setState({
       patients: data.content,
       totalPages: data.totalPages,
     });
   };

   // Si hay parámetros de búsqueda, ajusta la URL y la lógica de procesamiento
   if (searchName || searchLastName || searchDni) {
     const queryParams = [];
     if (searchName) queryParams.push(`name=${searchName}`);
     if (searchLastName) queryParams.push(`lastName=${searchLastName}`);
     if (searchDni) queryParams.push(`dni=${searchDni}`);
     url = `http://localhost:8081/patient/search?${queryParams.join('&')}&page=${currentPage}`;

     // Modifica la función de procesamiento para adaptarse a la respuesta de búsqueda
     processData = (data) => {
       this.setState({
         patients: data,
         totalPages: 1, // No hay paginación en la búsqueda, así que establece totalPages en 1
       });
     };
   }

   // Realizar la solicitud GET
   fetch(url)
     .then((response) => response.json())
     .then(processData) // Usa la función de procesamiento correspondiente
     .catch((error) => {
       console.error('Error al obtener la lista de pacientes:', error);
     });
 };


    handleCreatePatientClick = () => {
        // Utiliza window.location.href para redirigir a la página de creación de pacientes
        window.location.href = '/patient/create';
      };

    handleClinicalPatientClick = (patientId) => {
            // Utiliza window.location.href para redirigir a la página de creación de fichas
            window.location.href =`/clinical/patient/${patientId}`;
          };

     openEditModal = (patient) => {
        this.setState({ selectedPatient: patient, isEditModalOpen: true });
     };

     closeEditModal = () => {
        this.setState({ selectedPatient: null, isEditModalOpen: false });
     };

      // Funciones para abrir y cerrar el modal de eliminación
     openDeleteModal = (patient) => {
        this.setState({ selectedPatientDelete: patient, isDeleteModalOpen: true });
     };

     closeDeleteModal = () => {
        this.setState({ selectedPatientDelete: null, isDeleteModalOpen: false });
     };

    handleEditPatientClick = (patient) => {
           this.setState({ selectedPatient: patient });
           this.updatePatientList();
    };
    handleDeletePatientClick = (patient) => {
          this.setState({ selectedPatientDelete: patient });
          this.updatePatientList();

    };
    handlePrevPage = () => {
        const { currentPage } = this.state;
        if (currentPage > 0) {
          this.setState({ currentPage: currentPage - 1 }, this.updatePatientList);
        }
      };

    handleNextPage = () => {
        const { currentPage, totalPages } = this.state;
        if (currentPage < totalPages - 1) {
          this.setState({ currentPage: currentPage + 1 }, this.updatePatientList);
        }
    };

  render() {
    const { patients, selectedPatient, selectedPatientDelete, currentPage, totalPages, isEditModalOpen, isDeleteModalOpen, searchDni, searchLastName, searchName } = this.state;
    return (
    <div>
      <div className="patient-list-container">
        <div className="search-bar">
          <input
            type="text"
            placeholder="Nombre"
            value={searchName}
            onChange={this.handleSearchNameChange}
          />
          <input
            type="text"
            placeholder="Apellido"
            value={searchLastName}
            onChange={this.handleSearchLastNameChange}
          />
          <input
            type="text"
            placeholder="DNI"
            value={searchDni}
            onChange={this.handleSearchDniChange}
          />
          <button onClick={this.updatePatientList}>Buscar</button>
          <button onClick={this.handleClearFilters}>Limpiar</button>
        </div>

        <h2>Lista de Pacientes</h2>
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>DNI</th>
              <th>Fecha de Nacimiento</th>
              <th>Sexo</th>
              <th>Dirección</th>
              <th>Teléfono</th>
              <th>Correo Electrónico</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {patients && patients.map((patient) => (
              <tr key={patient.id}>
                <td>{patient.name}</td>
                <td>{patient.lastName}</td>
                <td>{patient.dni}</td>
                <td>{new Date(patient.birthdate).toLocaleDateString()}</td>
                <td>{patient.gender}</td>
                <td>{patient.adress}</td>
                <td>{patient.phone}</td>
                <td>{patient.email}</td>
                <td>
                 <button onClick={() => this.openEditModal(patient)} title="Editar Paciente">
                 <img src={PencilIcon} alt="Editar" width="20" height="20" />
                 </button>
                 <button onClick={() => this.openDeleteModal(patient)} title="Borrar Paciente">
                 <img src={TrashIcon} alt="Eliminar" width="20" height="20" />
                 </button>
                  {this.state.userRoles.includes('ROLE_USER') && (
                    <button onClick={() => this.handleClinicalPatientClick(patient.id)} title="Fichas del Paciente">
                      <img src={FormIcon} alt="Ficha Paciente" width="20" height="20" />
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div className="pagination-buttons">
            <button onClick={this.handlePrevPage} disabled={currentPage === 0}>
            <img src={AntIcon} alt="Anterior" width="20" height="20" />
            </button>
            <span>Página {currentPage + 1} de {totalPages}</span>
            <button onClick={this.handleNextPage} disabled={currentPage === totalPages - 1}>
            <img src={SigIcon} alt="Siguiente" width="20" height="20" />
            </button>
        </div>
        <button className="create-patient-button" onClick={this.handleCreatePatientClick}>Crear Paciente</button>
      </div>

      <div>
        <Modal
          isOpen={isEditModalOpen}
          onRequestClose={this.closeEditModal}
          contentLabel="Editar Paciente"
          className="custom-modal"  // Agrega una clase personalizada para el estilo del modal
          overlayClassName="custom-overlay"
        >
          {selectedPatient && (
          <EditPatientForm patient={selectedPatient} onClose={this.closeEditModal} />
          )}
        </Modal>

        {/* Modal de eliminación */}
        <Modal
            isOpen={isDeleteModalOpen}
            onRequestClose={this.closeDeleteModal}
            contentLabel="Eliminar Paciente"
            className="custom-modal"  // Agrega una clase personalizada para el estilo del modal
            overlayClassName="custom-overlay"
        >
           {selectedPatientDelete && (
            <DeletePatientForm
              patient={selectedPatientDelete}
              onCancel={this.closeDeleteModal}
              onConfirm={this.handleConfirmDelete}
           />
           )}
        </Modal>
        </div>
      </div>

    );
  }
}

export default PatientList;
