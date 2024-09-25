import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserAvatar from './Avatar';
import CloseIcon from '../../assets/icons/close-sesion-icon.svg';
//import UploadSignatureModal from './UploadSignatureModal';
import './styles.css';


function Header() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [userAuthorities, setUserAuthorities] = useState([]);
  const [medicData, setMedicData] = useState(null); // Estado para almacenar los datos del médico
  const [showMenu, setShowMenu] = useState(false); // Estado para controlar si se muestra el menú
  //const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);


 useEffect(() => {
   const token = localStorage.getItem('token');
   const storedUsername = localStorage.getItem('userName');

   if (token) {
     // Obtener autoridades del usuario
     fetch('http://localhost:8081/user/authorities', {
       method: 'GET',
       headers: {
         'Authorization': `Bearer ${token}`
       }
     })
     .then(response => response.json())
     .then(data => {
       setUserAuthorities(data.model);
     })
     .catch(error => {
       console.error('Error al obtener las autoridades del usuario:', error);
     });

     // Obtener nombre de usuario
     fetch('http://localhost:8081/user/username', {
       method: 'GET',
       headers: {
         'Authorization': `Bearer ${token}`
       }
     })
     .then(response => response.text())
     .then(data => {
       setUsername(data);
     })
     .catch(error => {
       console.error('Error al obtener el nombre de usuario:', error);
     });

     // Obtener datos del médico
     fetch(`http://localhost:8081/medic/username/${storedUsername}`, {
       headers: {
         Authorization: `Bearer ${token}`,
       },
     })
     .then(response => response.json())
     .then(data => {
       const medicId = data;

       // Llamada a la API para obtener los datos del paciente
       fetch(`http://localhost:8081/medic/${medicId}`, {
         headers: {
           Authorization: `Bearer ${token}`,
         },
       })
       .then((response) => response.json())
       .then((data) => {
         setMedicData(data);
       })
       .catch((error) => {
         console.error('Error al obtener los datos del medico:', error);
       });
     })
     .catch((error) => {
       console.error('Error al obtener el ID del medico:', error);
     });
   }
 }, []);

      //const handleOpenUploadModal = () => {
        //setIsUploadModalOpen(true);
      //};

      //const handleCloseUploadModal = () => {
        //setIsUploadModalOpen(false);
      //};

   const handleGoToPatients = () => {
      navigate('/patient/list');
    };

    const handleGoToMedics = () => {
      if (userAuthorities.includes('ROLE_ADMIN')) {
        navigate('/medic/list');
      } else {
        console.error('Usuario no autorizado para acceder a la página de Médicos.');
      }
    };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setUsername('');
    navigate('');
  };

  const toggleMenu = () => {
    setShowMenu(!showMenu);
    console.log('Toggle menu clicked:', showMenu);
  };

  // Definición de la función formatSpecialityName
  const formatSpecialityName = (name) => {
    // Reemplaza los guiones bajos con espacios
    return name.replace(/_/g, ' ');
  };

  return (
    <div className="header">
      {username && userAuthorities && (
        <div className="left-section">
          <button className='cerrar-session' onClick={handleLogout} title="Cerrar Sesión">
            <img src={CloseIcon} alt="Salir" width="20" height="20" />
          </button>
          {userAuthorities && !userAuthorities.includes('ROLE_PATIENT') && (
            <button className='go-to-patients' onClick={handleGoToPatients}>Ir a Pacientes</button>
          )}
          {userAuthorities && userAuthorities.includes('ROLE_ADMIN') && (
            <button className='go-to-medics' onClick={handleGoToMedics}>Ir a Médicos</button>
          )}
          {/* {userAuthorities && userAuthorities.includes('ROLE_USER') && (
            <div className="upload-image-section">
              <button className="upload-image-button" onClick={handleOpenUploadModal}>Cargar Firma</button>
            </div>
          )} */}
        </div>
      )}
      <div className="center-section">
        <h1 className='title'>HISTORIA CLÍNICA</h1>
      </div>
      {username && userAuthorities && (
        <div className="right-section">
          <UserAvatar username={username} onClick={toggleMenu} />
          <span className="welcome-message">¡Bienvenido {username}!</span>
          {medicData && (
            <span className="speciality-message">Especialidad: {formatSpecialityName(medicData.speciality)}</span>
          )}
        </div>
      )}
    </div>
  );
}
export default Header;
