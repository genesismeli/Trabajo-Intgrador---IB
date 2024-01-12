import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

const DiagnosisTab = () => {
  const { patientId } = useParams();
  const [diagnoses, setDiagnoses] = useState([]);

  useEffect(() => {
    // Cargar diagnósticos para el paciente con ID patientId
    fetch(`http://localhost:8081/diagnosis/patient/${patientId}`)
      .then(response => response.json())
      .then(data => {
        setDiagnoses(data);
      })
      .catch(error => {
        console.error('Error al cargar diagnósticos:', error);
      });
  }, [patientId]); // Dependencia patientId para volver a cargar diagnósticos cuando cambia el ID del paciente

  return (
    <div className="diagnosis-tab">
      <h3>Diagnósticos</h3>
      <ul>
        {diagnoses.map(diagnosis => (
          <li key={diagnosis.id}>
            <strong>Código:</strong> {diagnosis.code}<br />
            <strong>Descripción:</strong> {diagnosis.description}<br />
            <strong>Estado:</strong> {diagnosis.status}<br />
            <strong>Fecha:</strong> {diagnosis.date}<br />
            <strong>Notas:</strong> {diagnosis.notes}<br />
            {/* Puedes agregar más detalles del diagnóstico según tu modelo de datos */}
          </li>
        ))}
      </ul>
      {/* Agregar aquí el formulario para agregar diagnósticos si es necesario */}
    </div>
  );
};

export default DiagnosisTab;
