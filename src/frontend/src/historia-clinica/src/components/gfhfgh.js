// ... (código anterior)

{isModalOpen && (
  <div className="modal">
    <button className="close-button" onClick={closeViewModal}>
      Cerrar
    </button>
    <h3>Ficha Clínica</h3>
    {/* Renderiza los detalles de la ficha clínica */}
    {selectedRecord && (
      <div>
        <h4>Exámenes Físicos</h4>
        <table>
          <thead>
            <tr>
              <th>F. Cardíaca</th>
              <th>Saturación de Oxígeno</th>
              {/* ... (otras columnas omitidas para brevedad) */}
            </tr>
          </thead>
          <tbody>
            {/* Mapeo de los exámenes físicos */}
            {selectedRecord.physicalExams.map((exam) => (
              <tr key={exam.id}>
                <td>{exam.heartRate}</td>
                <td>{exam.oxygenSaturation}</td>
                {/* ... (otras columnas omitidas para brevedad) */}
              </tr>
            ))}
          </tbody>
        </table>

        <h4>Medicamentos</h4>
        <table>
          <thead>
            <tr>
              <th>Nombre</th>
              <th>Concentración</th>
              {/* ... (otras columnas omitidas para brevedad) */}
            </tr>
          </thead>
          <tbody>
            {/* Mapeo de los medicamentos */}
            {selectedRecord.medications.map((medication) => (
              <tr key={medication.id}>
                <td>{medication.medicationName}</td>
                <td>{medication.concentration}</td>
                {/* ... (otras columnas omitidas para brevedad) */}
              </tr>
            ))}
          </tbody>
        </table>

        <h4>Diagnósticos</h4>
        <table>
          <thead>
            <tr>
              <th>Código</th>
              <th>Descripción</th>
              <th>Estado</th>
            </tr>
          </thead>
          <tbody>
            {/* Mapeo de los diagnósticos */}
            {selectedRecord.diagnoses.map((diagnosis) => (
              <tr key={diagnosis.id}>
                <td>{diagnosis.code}</td>
                <td>{diagnosis.description}</td>
                <td>{traducirEstado(diagnosis.status)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    )}

    {totalPages > 1 && (
      <div className="pagination">
        <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0}>
          Anterior
        </button>
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index}
            onClick={() => handlePageChange(index)}
            className={index === currentPage ? 'active' : ''}
          >
            {index + 1}
          </button>
        ))}
        <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages - 1}>
          Siguiente
        </button>
      </div>
    )}
  </div>
)}

// ... (código posterior)
