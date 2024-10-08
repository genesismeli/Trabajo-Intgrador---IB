import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header/Header';
import Footer from './components/Footer/Footer';
import Login from './components/Login/Login';
import ChangedPassword from './components/Password/ChangedPassword';
import MedicList from './components/Medic/MedicList';
import PatientList from './components/Patient/PatientList';
import CreateMedic from './components/Medic/CreateMedic';
import CreatePatient from './components/CreatePatient/CreatePatient';
import ClinicalRecordList from './components/PatientClinicalRecord/ClinicalRecordList';
import CreateClinicalRecord from './components/PatientClinicalRecord/CreateClinicalRecord';
import PatientClinicalRecord from './components/VistPatient/PatientClinicalRecordList';


function App() {
return (
    <div className="app-container">
      <Router>
        <Header />
        <div className="content">
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/change-password" element={<ChangedPassword/>} />
            <Route path="/medic/list" element={<MedicList />} />
            <Route path="/patient/list" element={<PatientList />} />
            <Route path="/medic/create" element={<CreateMedic />} />
            <Route path="/patient/create" element={<CreatePatient />} />
            <Route path="/clinical/patient/:patientId" element={<ClinicalRecordList />} />
            <Route path="/clinical/add" element={<CreateClinicalRecord />} />
            <Route path="/patient/list/clinicalRecord" element={<PatientClinicalRecord />} />


          </Routes>
        </div>
        <Footer />

      </Router>
    </div>
  );
}
export default App;


