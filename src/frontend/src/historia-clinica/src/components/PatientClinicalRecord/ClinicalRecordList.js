import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './clinicalRecord.css';
import Message from '../Message.js';
import ViewIcon from '../../assets/icons/view-icon.svg';
import DownloadIcon from '../../assets/icons/download-icon.svg';
import SigIcon from '../../assets/icons/flecha-adel-icon.svg';
import AntIcon from '../../assets/icons/flecha-ant-icon.svg';
import MedicationsIcon from '../../assets/icons/medication-icon.svg';
import VitalSignsIcon from '../../assets/icons/vital_signs-icon.svg';
import DiagnosisIcon from '../../assets/icons/diagnosis-icon.svg';
import CertificateIcon from '../../assets/icons/certificado-medico-icon.svg';

const formatBirthdate = (birthdate) => {
  // Lógica de formateo de fecha aquí (asegúrate de implementarla según tus necesidades)
  return new Date(birthdate).toLocaleDateString();
};

const ClinicalRecordList = () => {
  const [clinicalRecords, setClinicalRecords] = useState([]);
  const [selectedRecord, setSelectedRecord] = useState(null); // Nuevo estado para almacenar la ficha clínica seleccionada
  const [isModalOpen, setIsModalOpen] = useState(false); // Estado para controlar si el modal está abierto
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);
  const [patientData, setPatientData] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [medicData, setMedicData] = useState(null);



  const { patientId } = useParams();

  useEffect(() => {
   const token = localStorage.getItem('token');
    // Llamada a la API para obtener las fichas clínicas del paciente
   fetch(`http://localhost:8081/clinical/patient/${patientId}?page=${currentPage}`, {
     headers: {
       Authorization: `Bearer ${token}`, // Incluye el token en la cabecera de autorización
     },
   })

      .then((response) => response.json())
      .then((data) => {
        setClinicalRecords(data.content);
        setTotalPages(data.totalPages);
      })
      .catch((error) => {
        console.error('Error al obtener las fichas clínicas:', error);
      });

    // Llamada a la API para obtener los datos del paciente

    fetch(`http://localhost:8081/patient/${patientId}`,  {
         headers: {
         Authorization: `Bearer ${token}`, // Incluye el token en la cabecera de autorización
         },
    })
      .then((response) => response.json())
      .then((data) => {
        setPatientData(data);
      })
      .catch((error) => {
        console.error('Error al obtener los datos del paciente:', error);
      });

  }, [patientId, currentPage]);


    const handleNextPage = () => {
      if (currentPage < totalPages - 1) {
          setCurrentPage((prevPage) => prevPage + 1);
      }
    };

    const handlePrevPage = () => {
      if (currentPage > 0) {
          setCurrentPage((prevPage) => prevPage - 1);
      }
    };

    // Código en el componente anterior que muestra la lista de fichas clínicas
    const handleCreateClinicalRecordClick = (patientId) => {
      // Redirige al usuario a la página de creación de fichas clínicas
      window.location.href = `/clinical/add?patientId=${patientId}`;
    };



  const viewClinicalRecord = (recordId) => {
    // Llamada a la API para obtener la ficha clínica por ID
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8081/clinical/${recordId}`, {
        headers: {
          Authorization: `Bearer ${token}`, // Incluye el token en el encabezado de autorización
        },
      })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        setSelectedRecord(data); // Almacena la ficha clínica seleccionada
        setIsModalOpen(true); // Abre el modal
      })
      .catch((error) => {
        console.error('Error al obtener la ficha clínica por ID:', error);
      });
  };

   const loadMedicData = (recordId) => {
     const token = localStorage.getItem('token');
     fetch(`http://localhost:8081/clinical/${recordId}/medic`, {
       headers: {
         Authorization: `Bearer ${token}`,
       },
     })
       .then((response) => response.json())
       .then((data) => {
         console.log(data);
         setMedicData(data); // Almacena los datos del médico asociado al registro clínico
       })
       .catch((error) => {
         console.error('Error al obtener los datos del médico:', error);
       });
   };

   // Cuando se abre el modal, cargamos los datos del médico asociado al registro clínico
   useEffect(() => {
     if (isModalOpen && selectedRecord) {
       loadMedicData(selectedRecord.id);
     }
   }, [isModalOpen, selectedRecord]);





const downloadClinicalRecordMedications = (recordId) => {
  // Llamada al nuevo endpoint para obtener el PDF
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8081/pdf/clinical-record/medications/${recordId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        // Verificar el estado de la respuesta
        if (response.ok) {
          return response.blob();
        } else if (response.status === 404) {
          // Mostrar un mensaje en lugar de descargar el PDF
          throw new Error('No existen medicamentos asociados a esta ficha');
        } else {
          throw new Error('Error al obtener el PDF');
        }
      })
      .then((blob) => {
        // Crear un objeto URL para el blob
        const url = window.URL.createObjectURL(new Blob([blob]));

        // Crear un enlace invisible y hacer clic en él para iniciar la descarga
        const a = document.createElement('a');
        a.href = url;
        a.download = 'medicamentos.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);

        // Revocar el objeto URL para liberar recursos
        window.URL.revokeObjectURL(url);
        // Limpiar el mensaje de error después de una descarga exitosa
        setErrorMessage(null);
      })
      .catch((error) => {
        // Mostrar el mensaje de error
        console.error(error.message);

        // Mostrar el mensaje de error en la interfaz de usuario (reemplaza este código según tu implementación)
        setErrorMessage(error.message);
      });
  };

const downloadClinicalRecordExam = (recordId) => {
  // Llamada al nuevo endpoint para obtener el PDF
    const token = localStorage.getItem('token');
    fetch(`http://localhost:8081/pdf/clinical-record/vital-signs/${recordId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        // Verificar el estado de la respuesta
        if (response.ok) {
          return response.blob();
        } else if (response.status === 404) {
          // Mostrar un mensaje en lugar de descargar el PDF
          throw new Error('No existen Signos Vitales asociados a esta ficha');
        } else {
          throw new Error('Error al obtener el PDF');
        }
      })
      .then((blob) => {
        // Crear un objeto URL para el blob
        const url = window.URL.createObjectURL(new Blob([blob]));

        // Crear un enlace invisible y hacer clic en él para iniciar la descarga
        const a = document.createElement('a');
        a.href = url;
        a.download = 'signos_vitales.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);

        // Revocar el objeto URL para liberar recursos
        window.URL.revokeObjectURL(url);
        // Limpiar el mensaje de error después de una descarga exitosa
        setErrorMessage(null);
      })
      .catch((error) => {
        // Mostrar el mensaje de error
        console.error(error.message);

        // Mostrar el mensaje de error en la interfaz de usuario (reemplaza este código según tu implementación)
        setErrorMessage(error.message);
      });
  };

const downloadClinicalRecordDiagnosis = (recordId) => {
  // Llamada al nuevo endpoint para obtener el PDF
  const token = localStorage.getItem('token');
  fetch(`http://localhost:8081/pdf/clinical-record/diagnosis/${recordId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then((response) => {
      // Verificar el estado de la respuesta
      if (response.ok) {
        return response.blob();
      } else if (response.status === 404) {
        // Mostrar un mensaje en lugar de descargar el PDF
        throw new Error('No existen diagnósticos asociados a esta ficha');
      } else {
        throw new Error('Error al obtener el PDF');
      }
    })
    .then((blob) => {
      // Crear un objeto URL para el blob
      const url = window.URL.createObjectURL(new Blob([blob]));

      // Crear un enlace invisible y hacer clic en él para iniciar la descarga
      const a = document.createElement('a');
      a.href = url;
      a.download = 'diagosticos.pdf';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

      // Revocar el objeto URL para liberar recursos
      window.URL.revokeObjectURL(url);
      // Limpiar el mensaje de error después de una descarga exitosa
      setErrorMessage(null);
    })
    .catch((error) => {
      // Mostrar el mensaje de error
      console.error(error.message);

      // Mostrar el mensaje de error en la interfaz de usuario (reemplaza este código según tu implementación)
      setErrorMessage(error.message);
    });
};

// Función para cerrar el mensaje de error
  const closeErrorMessage = () => {
    setErrorMessage(null);
  };

const downloadClinicalRecord = (recordId) => {
  // Llamada al nuevo endpoint para obtener el PDF
  const token = localStorage.getItem('token');
  fetch(`http://localhost:8081/pdf/clinical-record/${recordId}/pdf`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then((response) => {
      if (response.ok) {
        return response.blob();
      } else if (response.status === 404) {
        throw new Error('No se encontró la ficha clínica solicitada');
      } else {
        throw new Error('Error al obtener el PDF de la ficha clínica');
      }
    })
    .then((blob) => {
      // Crear un objeto URL para el blob
      const url = window.URL.createObjectURL(new Blob([blob]));

      // Crear un enlace invisible y hacer clic en él para iniciar la descarga
      const a = document.createElement('a');
      a.href = url;
      a.download = 'ficha_clinica.pdf';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

      // Revocar el objeto URL para liberar recursos
      window.URL.revokeObjectURL(url);
      // Limpiar el mensaje de error después de una descarga exitosa
      setErrorMessage(null);
    })
    .catch((error) => {
      // Mostrar el mensaje de error
      console.error(error.message);

      // Mostrar el mensaje de error en la interfaz de usuario (reemplaza este código según tu implementación)
      setErrorMessage(error.message);
    });
};


  const closeViewModal = () => {
    setIsModalOpen(false); // Cierra el modal
  };

  // Definición de la función formatSpecialityName
  const formatSpecialityName = (name) => {
    // Reemplaza los guiones bajos con espacios
    return name.replace(/_/g, ' ');
  };

const downloadClinicalRecordMedicalCertificate = (recordId) => {
  // Llamada al nuevo endpoint para obtener el certificado médico en PDF
  const token = localStorage.getItem('token');
  fetch(`http://localhost:8081/pdf/medical-certificate/${recordId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  })
    .then((response) => {
      // Verificar el estado de la respuesta
      if (response.ok) {
        return response.blob();
      } else if (response.status === 404) {
        // Mostrar un mensaje en lugar de descargar el PDF
        throw new Error('No existe certificado médico asociado a esta ficha');
      } else {
        throw new Error('Error al obtener el certificado médico');
      }
    })
    .then((blob) => {
      // Crear un objeto URL para el blob
      const url = window.URL.createObjectURL(new Blob([blob]));

      // Crear un enlace invisible y hacer clic en él para iniciar la descarga
      const a = document.createElement('a');
      a.href = url;
      a.download = 'certificado_medico.pdf';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);

       // Revocar el objeto URL para liberar recursos
       window.URL.revokeObjectURL(url);
       // Limpiar el mensaje de error después de una descarga exitosa
       setErrorMessage(null);
     })
     .catch((error) => {
       // Mostrar el mensaje de error
       console.error(error.message);

       // Mostrar el mensaje de error en la interfaz de usuario (reemplaza este código según tu implementación)
       setErrorMessage(error.message);
     });
};

  return (

    <div className= "clinical-record-list-container">
    <div className="left-container">
    {patientData && (
       <div>
       <h3 className="form-section-title"> Datos del paciente </h3>
       <p> <strong>Paciente:</strong> {patientData.name} {patientData.lastName} </p>
       <p> <strong>Fecha de nacimiento:</strong> {formatBirthdate(patientData.birthdate)} </p>
       <p> <strong>Teléfono:</strong> {patientData.phone} </p>
       <p> <strong>Email:</strong> {patientData.email} </p>
       <p> <strong>DNI:</strong> {patientData.dni}</p>
    </div>
    )}
    <div>
      <button className="create-record-button" onClick={() => handleCreateClinicalRecordClick(patientId)}>Crear Ficha </button>
    </div>
    </div>

      <div className="clinical-record-container">
      <h2>Lista de Fichas Clínicas</h2>
      {errorMessage && (
         <Message text={errorMessage} onClose={closeErrorMessage} />
      )}
       {/* Verifica si hay fichas clínicas disponibles */}
       {clinicalRecords.length > 0 ? (
            <>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Fecha de Creación</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {clinicalRecords.map((record) => (
            <tr key={record.id}>
              <td>Ficha Clínica</td>
              <td>{`${new Date(record.date).toLocaleDateString()} ${new Date(record.date).toLocaleTimeString()}`}</td>
              <td>
                <button onClick={() => viewClinicalRecord(record.id)} title="Ver ficha">
                  <img src={ViewIcon} alt="Ver Ficha" width="20" height="20" />
                </button>
                <button onClick={() => downloadClinicalRecord(record.id)} title="Descargar Ficha Completa">
                  <img src={DownloadIcon} alt="Descargar Ficha Completa" width="20" height="20" />
                </button>
                <button onClick={() => downloadClinicalRecordMedications(record.id)} title="Descargar Ficha Medicamentos">
                  <img src={MedicationsIcon} alt="Descargar Ficha Medicamentos" width="20" height="20" />
                </button>
                <button onClick={() => downloadClinicalRecordExam(record.id)} title="Descargar Ficha Signos Vitales">
                   <img src={VitalSignsIcon} alt="Descargar Ficha Signos Vitales" width="20" height="20" />
                </button>
                  <button onClick={() => downloadClinicalRecordDiagnosis(record.id)} title="Descargar Ficha Dagnósticoss">
                    <img src={DiagnosisIcon} alt="Descargar Ficha Dagnósticos" width="20" height="20" />
                  </button>
                <button onClick={() => downloadClinicalRecordMedicalCertificate(record.id)} title="Descargar Certificado Médico">
                  <img src={CertificateIcon} alt="Descargar Certificado Médico" width="20" height="20" />
                </button>

              </td>
            </tr>
          ))}

        </tbody>
      </table>
        <div className="pagination-buttons">
        <button onClick={handlePrevPage} disabled={currentPage === 0}>
          <img src={AntIcon} alt="Anterior" width="20" height="20" />
        </button>
        <span>Página {currentPage + 1} de {totalPages}</span>
        <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
        <img src={SigIcon} alt="Siguiente" width="20" height="20" />
        </button>
        </div>
      </>
        ) : (
         <p className="error">No hay fichas clínicas disponibles.</p>
        )}
      </div>


      {/* Modal para mostrar la ficha clínica */}
      {isModalOpen && (
        <div className="modal active">
          <button className="close-button" onClick={closeViewModal}>Cerrar</button>
          <h2 className="title">Ficha Clínica</h2>
          {/* Renderiza los detalles de la ficha clínica */}
          {selectedRecord && (
            <div>
            {medicData && (
                 <div>
                   <h4 className="title">Datos del Profesional</h4>
                   <p> <strong>Profesional:</strong> {medicData.name} {medicData.lastName} </p>
                   <p> <strong>Especialidad:</strong> {formatSpecialityName(medicData.speciality)} </p>
                   <p> <strong>Email:</strong> {medicData.email} </p>
                   <p> <strong>Número de Registro:</strong> {medicData.registrationNumber} </p>

                </div>
                )}
            <h4 className="title">Motivo de atención</h4>
            <table>
              <thead>
                <tr>
                  <th>Motivo de Atención</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{selectedRecord.reason}</td>
                </tr>
              </tbody>
            </table>
            <h4 className="title">Notas</h4>
            <table>
              <thead>
                <tr>
                  <th>Notas</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{selectedRecord.notes}</td>
                </tr>
              </tbody>
            </table>
            <h4 className="title">Anamnesis</h4>
            <table>
              <thead>
                <tr>
                  <th>Anamnesis</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{selectedRecord.anamnesis}</td>
                </tr>
              </tbody>
            </table>
            <h4 className="title">Antecedentes Personales</h4>
            {selectedRecord.personalHistorys && selectedRecord.personalHistorys.length > 0 && (
            <table>
              <thead>
                <tr>
                  <th>Tipo de Antecedente</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                {selectedRecord.personalHistorys && (
                  <>
                    <tr>
                      <td>Psicopatías</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.personalHistorys[0].psychopathy}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>Diabetes</td>
                      <td>
                        <input
                          type="checkbox"
                          defaultChecked={selectedRecord.personalHistorys[0].diabetes}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>Enf. Coronarias</td>
                      <td>
                        <input
                          type="checkbox"
                          defaultChecked={selectedRecord.personalHistorys[0].coronaryArteryDisease}
                          disabled
                        />
                      </td>
                    </tr>
                      <tr>
                        <td>Asma</td>
                        <td>
                          <input
                            type="checkbox"
                            defaultChecked={selectedRecord.personalHistorys[0].bronchialAsthma}
                            disabled
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>Enf. Pulmonares</td>
                        <td>
                          <input
                            type="checkbox"
                            defaultChecked={selectedRecord.personalHistorys[0].bronchopulmonaryDisease}
                            disabled
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>Tuberculosis</td>
                        <td>
                          <input
                            type="checkbox"
                            defaultChecked={selectedRecord.personalHistorys[0].tuberculosis}
                            disabled
                          />
                        </td>
                      </tr>
                      <tr>
                        <td>Gota</td>
                        <td>
                          <input
                            type="checkbox"
                            defaultChecked={selectedRecord.personalHistorys[0].gout}
                            disabled
                          />
                        </td>
                      </tr>
                    <tr>
                      <td>Infección de Transmisión Sexual</td>
                      <td>
                        <input
                          type="checkbox"
                          defaultChecked={selectedRecord.personalHistorys[0].sexuallyTransmittedInfection}
                          disabled
                        />
                      </td>
                    </tr>
                     <tr>
                       <td>Endocrinopatías </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].endocrineDisorders}
                           disabled
                         />
                       </td>
                     </tr>
                    <tr>
                       <td>Nefropatías </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].nephropathies}
                           disabled
                         />
                       </td>
                     </tr>
                    <tr>
                       <td>Urolopatías </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].uropathies}
                           disabled
                         />
                       </td>
                     </tr>
                   <tr>
                       <td>Hematopatologías </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].hematopathies}
                           disabled
                         />
                       </td>
                     </tr>

                   <tr>
                       <td>Úlceras </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].ulcer}
                           disabled
                         />
                       </td>
                     </tr>
                   <tr>
                       <td>Hepatitis </td>
                       <td>
                         <input
                           type="checkbox"
                           defaultChecked={selectedRecord.personalHistorys[0].hepatitis}
                           disabled
                         />
                       </td>
                     </tr>
                    <tr>
                        <td>Fiebre </td>
                        <td>
                          <input
                            type="checkbox"
                            defaultChecked={selectedRecord.personalHistorys[0].fever}
                            disabled
                          />
                        </td>
                      </tr>
                    <tr>
                      <td>Alergias</td>
                      <td>{selectedRecord.personalHistorys[0].allergies}</td>
                    </tr>
                    <tr>
                      <td>Antecedentes Médicos Adicionales</td>
                      <td>{selectedRecord.personalHistorys[0].otherMedicalHistory}</td>
                    </tr>
                    <tr>
                      <td>Cáncer</td>
                      <td>{selectedRecord.personalHistorys[0].cancer}</td>
                    </tr>
                  </>
                )}
              </tbody>
            </table>
            )}
            <h4 className="title">Antecedentes Familiares</h4>
            {selectedRecord.familyHistorys && selectedRecord.familyHistorys.length > 0 && (
            <table>
              <thead>
                <tr>
                  <th>Tipo de Antecedente</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                {selectedRecord.familyHistorys && (
                  <>
                    <tr>
                      <td>Enf. Coronarias</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.familyHistorys[0].coronaryArteryDisease}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>Infarto de Miocardio</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.familyHistorys[0].myocardialInfarction}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>Hipetensión</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.familyHistorys[0].hypertension}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>Asma</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.familyHistorys[0].asthma}
                          disabled
                        />
                      </td>
                    </tr>
                    <tr>
                      <td>EPOC</td>
                      <td>
                        <input
                          type="checkbox"
                          checked={selectedRecord.familyHistorys[0].copd}
                          disabled
                        />
                      </td>
                    </tr>
                     <tr>
                       <td>Tuberculosis</td>
                       <td>
                         <input
                           type="checkbox"
                           checked={selectedRecord.familyHistorys[0].tuberculosis}
                           disabled
                         />
                       </td>
                     </tr>
                     <tr>
                       <td>Diabetes</td>
                       <td>
                         <input
                           type="checkbox"
                           checked={selectedRecord.familyHistorys[0].diabetes}
                           disabled
                         />
                       </td>
                     </tr>
                     <tr>
                       <td>Obesidad</td>
                       <td>
                         <input
                           type="checkbox"
                           checked={selectedRecord.familyHistorys[0].obesity}
                           disabled
                         />
                       </td>
                     </tr>
                      <tr>
                        <td>Síndrome Metabólico</td>
                        <td>
                          <input
                            type="checkbox"
                            checked={selectedRecord.familyHistorys[0].metabolicSyndrome}
                            disabled
                          />
                        </td>
                      </tr>
                           <tr>
                             <td>Anemia</td>
                             <td>
                               <input
                                 type="checkbox"
                                 checked={selectedRecord.familyHistorys[0].anemia}
                                 disabled
                               />
                             </td>
                           </tr>
                         <tr>
                           <td>Hemofilia</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].hemophilia}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>ACV</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].stroke}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Enf. de Alzheimer</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].alzheimer}
                               disabled
                             />
                           </td>
                         </tr>
                             <tr>
                               <td>Esclerosis Múltiple</td>
                               <td>
                                 <input
                                   type="checkbox"
                                   checked={selectedRecord.familyHistorys[0].multipleSclerosis}
                                   disabled
                                 />
                               </td>
                             </tr>
                               <tr>
                                 <td>Hematocromatosis</td>
                                 <td>
                                   <input
                                     type="checkbox"
                                     checked={selectedRecord.familyHistorys[0].hemochromatosis}
                                     disabled
                                   />
                                 </td>
                               </tr>
                         <tr>
                           <td>Fibrosis Quística</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].cysticFibrosis}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Depresión</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].depression}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Esquizofrenia</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].schizophrenia}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Artritis Reumatoide</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].rheumatoidArthritis}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Lupus</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].lupus}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Enf. Celíaca</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].celiacDisease}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Enf. Renal Crónica</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].chronicKidneyDisease}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Nefrología Diabética</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].diabeticNephropathy}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Cáncer de mama</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].breastCancer}
                               disabled
                             />
                           </td>
                         </tr>
                         <tr>
                           <td>Cáncer de colon</td>
                           <td>
                             <input
                               type="checkbox"
                               checked={selectedRecord.familyHistorys[0].colonCancer}
                               disabled
                             />
                           </td>
                         </tr>
                            <tr>
                              <td>Cáncer de pulmón</td>
                              <td>
                                <input
                                  type="checkbox"
                                  checked={selectedRecord.familyHistorys[0].lungCancer}
                                  disabled
                                />
                              </td>
                            </tr>
                            <tr>
                              <td>Enf. Intestinal Inflamatoria</td>
                              <td>
                                <input
                                  type="checkbox"
                                  checked={selectedRecord.familyHistorys[0].inflammatoryBowelDisease}
                                  disabled
                                />
                              </td>
                            </tr>
                            <tr>
                              <td>Hipotiroidismo</td>
                              <td>
                                <input
                                  type="checkbox"
                                  checked={selectedRecord.familyHistorys[0].hypothyroidism}
                                  disabled
                                />
                              </td>
                            </tr>
                    <tr>
                      <td>Antecedentes Médicos Adicionales</td>
                      <td>{selectedRecord.familyHistorys[0].otherMedicalHistory}</td>
                    </tr>
                  </>
                )}
              </tbody>
            </table>
            )}
              <h4 className="title">Signos Vitales</h4>
              <table>
                <thead>
                  <tr>
                    <th>F. Cardíaca</th>
                    <th>Saturación de Oxígeno</th>
                    <th>F. Respiratoria</th>
                    <th>P. Sistólica</th>
                    <th>P. Diastólica</th>
                    <th>LPM</th>
                    <th>Glucosa</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.physicalExams.map((exam) => (
                    <tr key={exam.id}>
                      <td>{exam.heartRate}</td>
                      <td>{exam.oxygenSaturation}</td>
                      <td>{exam.respiratoryRate}</td>
                      <td>{exam.systolicPressure}</td>
                      <td>{exam.diastolicPressure}</td>
                      <td>{exam.beatsPerMinute}</td>
                      <td>{exam.glucose}</td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <h4 className="title">Medicamentos</h4>
              <table>
                <thead>
                  <tr>
                    <th>Nombre Comercial</th>
                    <th>Nombre Genérico</th>
                    <th>Concentración</th>
                     <th>Presentación</th>
                     <th>Notas o indicaciones</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.medications.map((medication) => (
                    <tr key={medication.id}>
                      <td>{medication.vademecum?.nombre_comercial}</td>
                      <td>{medication.vademecum?.nombre_generico}</td>
                      <td>{medication.vademecum?.concentracion}</td>
                      <td>{medication.vademecum?.presentacion}</td>
                      <td>{medication.notes}</td>

                    </tr>
                  ))}
                </tbody>
              </table>

              <h4 className="title">Diagnósticos</h4>
              <table>
                <thead>
                  <tr>
                    <th>Código CIE10</th>
                    <th>Descripción</th>
                    <th>Notas</th>
                  </tr>
                </thead>
                <tbody>
                  {selectedRecord.diagnoses.map((diagnosis) => (
                    <tr key={diagnosis.id}>
                      <td>{diagnosis.codeCie10?.code}</td>
                      <td>{diagnosis.codeCie10?.description}</td>
                     <td>{diagnosis.notes}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            <h4 className="title">Exámenes Complementarios</h4>
            <table>
              <thead>
                <tr>
                  <th>Exámenes Complementarios</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{selectedRecord.complementaryExams}</td>
                </tr>
              </tbody>
            </table>
                <div>
                    <h4 className="title">Certificado Médico</h4>
                    <p>{selectedRecord.medicalCertificate}</p>
                </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default ClinicalRecordList;


