import React, { useState } from 'react';
import Modal from 'react-modal';
import './modal.css';

function UploadSignatureModal({ isOpen, onClose, medicData }) {
  const [imageFile, setImageFile] = useState(null);

  const handleImageChange = (event) => {
    setImageFile(event.target.files[0]);
  };

  const handleImageUpload = () => {
    const formData = new FormData();
    formData.append('file', imageFile);

    fetch(`http://localhost:8081/medic-images/${medicData.id}`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      body: formData,
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Error al cargar la imagen del médico');
      }
      console.log('Imagen del médico cargada exitosamente');
      onClose(); // Cierra el modal después de cargar la imagen con éxito
    })
    .catch(error => {
      console.error('Error al cargar la imagen del médico:', error);
    });
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onClose}
      contentLabel="Cargar Firma"
      className="custom-modal"
      overlayClassName="custom-overlay"
    >
      <div className="modal-content">
        <span className="close-button" onClick={onClose}>&times;</span>
        <h2 className="tittle">Cargar Firma</h2>
        <input type="file" accept="image/*" onChange={handleImageChange} />
        <button className="upload-button" onClick={handleImageUpload}>Cargar</button>
      </div>
    </Modal>
  );
}

export default UploadSignatureModal;
