import './deletePatientForm.css';
import React,{ useState } from 'react';


const DeletePatientForm = ({ patient, onCancel, onConfirm }) => {
  const [deleteMessage, setDeleteMessage] = useState(null);
  const handleConfirmDelete = () => {
      // Realiza la solicitud HTTP DELETE para eliminar el paciente
      fetch(`http://localhost:8081/patient/delete/${patient.id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error('Error al eliminar al paciente');
          }
          setDeleteMessage('Se eliminó correctamente');
           // Muestra el mensaje durante 2 segundos antes de llamar a onConfirm
            window.location.reload();
          })
        .catch((error) => {
          console.error('Error al eliminar al paciente:', error);
        });
    };


  return (
      <div className="delete-medic-form-container">
        <h2>Confirmar Eliminación</h2>
        <p>¿Estás seguro de que deseas eliminar al paciente {patient.name} {patient.lastName}?</p>
        {deleteMessage && <div className="success-message">{deleteMessage}</div>}
        <div className="delete-medic-form-buttons">
          <button className="confirm-delete-button" onClick={handleConfirmDelete}>Eliminar</button>
          <button className="cancel-delete-button" onClick={onCancel}>Cancelar</button>
        </div>
      </div>
  );
};

export default DeletePatientForm;
