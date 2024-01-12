import React, { Component } from 'react';
import './medicList.css';
import Modal from 'react-modal';
import EditMedicForm from './EditMedicForm';
import DeleteMedicForm from './DeleteMedicForm';
import TrashIcon from '../../assets/icons/trash-icon.svg'; // Ruta al archivo SVG del tacho de basura
import PencilIcon from '../../assets/icons/pencil-icon.svg';
import AntIcon from '../../assets/icons/flecha-ant-icon.svg';
import SigIcon from '../../assets/icons/flecha-adel-icon.svg';
Modal.setAppElement('#root');

class MedicList extends Component {
  constructor() {
    super();
    this.state = {
      medics: [],
      selectedMedic: null,
      selectedMedicDelete : null,
      isEditModalOpen: false,
      isDeleteModalOpen: false,
      currentPage: 0,
      totalPages: 1,
      searchName: '', // Nuevo estado para el primer nombre de búsqueda
      searchLastName: '', // Nuevo estado para el apellido de búsqueda
      searchRegistrationNumber: '',
    };
  }


  componentDidMount() {

    const { currentPage } = this.state;

    fetch(`http://localhost:8081/medic/all?page=${currentPage}`)
      .then((response) => response.json())
      .then((data) => {
                this.setState({
                  medics: data.content,
                  totalPages: data.totalPages,
                   });
                })
      .catch((error) => {
        console.error('Error al obtener la lista de médicos:', error);
      });
  };

    handleSearchNameChange = (event) => {
          this.setState({ searchName: event.target.value });
        };

    handleSearchLastNameChange = (event) => {
          this.setState({ searchLastName: event.target.value });
        };

    handleSearchRegistrationNumberChange = (event) => {
          this.setState({ searchRegistrationNumber: event.target.value });
        };

    handleSearch = () => {
          this.updateMedicList();
    };

   handleClearFilters = () => {
         // Limpiar los estados de búsqueda
         this.setState(
           {
             searchName: '',
             searchLastName: '',
             searchRegistrationNumber: '',
           },
           () => {
             // Después de limpiar los filtros, cargar la lista completa de pacientes
             const { currentPage } = this.state;

             fetch(`http://localhost:8081/medic/all?page=${currentPage}`)
               .then((response) => response.json())
               .then((data) => {
                 this.setState({
                   medics: data.content,
                   totalPages: data.totalPages,
                 });
               })
               .catch((error) => {
                 console.error('Error al obtener la lista de pacientes:', error);
               });
           }
         );
       };

  updateMedicList = () => {
     const { searchName, searchLastName, searchRegistrationNumber, currentPage } = this.state;

     // Construir la URL con el punto final común
     let url = `http://localhost:8081/medic/all?page=${currentPage}`;

     // Inicializar la función de procesamiento de datos
     let processData = (data) => {
       this.setState({
         medics: data.content,
         totalPages: data.totalPages,
       });
     };

     // Si hay parámetros de búsqueda, ajusta la URL y la lógica de procesamiento
     if (searchName || searchLastName || searchRegistrationNumber) {
       const queryParams = [];
       if (searchName) queryParams.push(`name=${searchName}`);
       if (searchLastName) queryParams.push(`lastName=${searchLastName}`);
       if (searchRegistrationNumber) queryParams.push(`registrationNumber=${searchRegistrationNumber}`);
       url = `http://localhost:8081/medic/search?${queryParams.join('&')}&page=${currentPage}`;

       // Modifica la función de procesamiento para adaptarse a la respuesta de búsqueda
       processData = (data) => {
         this.setState({
           medics: data,
           totalPages: 1, // No hay paginación en la búsqueda, así que establece totalPages en 1
         });
       };
     }

     // Realizar la solicitud GET
     fetch(url)
       .then((response) => response.json())
       .then(processData) // Usa la función de procesamiento correspondiente
       .catch((error) => {
         console.error('Error al obtener la lista de medicamentos:', error);
       });
   };

         openEditModal = (medic) => {
            this.setState({ selectedMedic: medic, isEditModalOpen: true });
         };

         closeEditModal = () => {
            this.setState({ selectedMedic: null, isEditModalOpen: false });
         };

          // Funciones para abrir y cerrar el modal de eliminación
         openDeleteModal = (medic) => {
            this.setState({ selectedMedicDelete: medic, isDeleteModalOpen: true });
         };

         closeDeleteModal = () => {
            this.setState({ selectedMedicDelete: null, isDeleteModalOpen: false });
         };

       handlePrevPage = () => {
            const { currentPage } = this.state;
            if (currentPage > 0) {
              this.setState({ currentPage: currentPage - 1 }, this.updateMedicList);
            }
          };

        handleNextPage = () => {
            const { currentPage, totalPages } = this.state;
            if (currentPage < totalPages - 1) {
              this.setState({ currentPage: currentPage + 1 }, this.updateMedicList);
            }
        };

  handleCreateMedicClick = () => {
    // Utiliza window.location.href para redirigir a la página de creación de médicos
    window.location.href = '/medic/create';
  };

   handleEditMedicClick = (medic) => {
       this.setState({ selectedMedic: medic });
       this.updateMedicList();
   };

    handleDeleteMedicClick = (medic) => {
      this.setState({ selectedMedicDelete: medic });
      this.updateMedicList();

    };


  render() {
      const { medics, selectedMedic, selectedMedicDelete, isEditModalOpen, isDeleteModalOpen, currentPage, totalPages, searchRegistrationNumber, searchLastName, searchName  } = this.state;
      return (

        <div className="medic-list-container">
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
            placeholder="Número de Registro"
            value={searchRegistrationNumber}
            onChange={this.handleSearchRegistrationNumberChange}
          />
          <button onClick={this.updateMedicList}>Buscar</button>
          <button onClick={this.handleClearFilters}>Limpiar</button>
        </div>
          <h2>Lista de Médicos</h2>
          <table>
            <thead>
              <tr>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Número de Registro</th>
                <th>Especialidad</th>
                <th>Correo Electrónico</th>
                <th>Acciones</th> {/* Nueva columna para los botones */}
              </tr>
            </thead>
            <tbody>
              {medics.map((medic) => (
                <tr key={medic.id}>
                  <td>{medic.name}</td>
                  <td>{medic.lastName}</td>
                  <td>{medic.registrationNumber}</td>
                  <td>{medic.speciality}</td>
                  <td>{medic.email}</td>
                  <td>
                      <button onClick={() => this.openEditModal(medic)} title="Editar Paciente">
                          <img src={PencilIcon} alt="Editar" width="20" height="20" />
                      </button>
                     <button onClick={() => this.openDeleteModal(medic)} title="Borrar Paciente">
                        <img src={TrashIcon} alt="Eliminar" width="20" height="20" />
                     </button>
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
          {/* Utiliza handleCreateMedicClick al hacer clic en el botón */}
          <button className="create-medic-button" onClick={this.handleCreateMedicClick}>Crear Médico</button>
 <div>
         <Modal
           isOpen={isEditModalOpen}
           onRequestClose={this.closeEditModal}
           contentLabel="Editar Médico"
           className="custom-modal"  // Agrega una clase personalizada para el estilo del modal
           overlayClassName="custom-overlay"
         >
           {selectedMedic && (
           <EditMedicForm medic={selectedMedic} onClose={this.closeEditModal} />
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
            {selectedMedicDelete && (
             <DeleteMedicForm
               medic={selectedMedicDelete}
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

  export default MedicList;