import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserAvatar from './Avatar';
import './styles.css';
function Header() {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [userAuthorities, setUserAuthorities] = useState([]);

  useEffect(() => {
    // Obtener el token almacenado en localStorage
    const token = localStorage.getItem('token');



    if (token) {

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

      // Realizar la solicitud para obtener el nombre de usuario
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

    }
  }, []);


     const handleLogout = () => {
        // Eliminar el token del almacenamiento local al cerrar sesión
        localStorage.removeItem('token');
        // Redirigir a la página de inicio de sesión
        setUsername(''); // Establecer el estado a un valor diferente para forzar la actualización
        navigate('');
      };
  //const handleGoBack = () => {
  //navigate(-1); // Navegar hacia atrás en la historia
  //};

    const handleGoToPatients = () => {
        // Redirige a la página de "Pacientes"
        navigate('/patient/list');
      };

  const handleGoToMedics = () => {
    // Redirige a la página de "Medicos" solo si el usuario tiene el rol ADMIN
    if (userAuthorities.includes('ROLE_ADMIN')) {
      navigate('/medic/list');
    } else {
      // Puedes mostrar un mensaje o realizar alguna acción si el usuario no tiene permisos
      console.error('Usuario no autorizado para acceder a la página de Medicos.');
    }
  };

 return (

 <div className="header">
    {username && (
   <div className="left-section">
     <button className='go-to-patients' onClick={handleGoToPatients}>Ir a Pacientes</button>
     {userAuthorities.includes('ROLE_ADMIN') && (
     <button className='go-to-medics' onClick={handleGoToMedics}>Ir a Médicos</button>
     )}
   </div>
   )}
   <div className="center-section">
     <h1 className='title'>HISTORIA CLÍNICA</h1>
   </div>
   {username && (
   <div className="right-section">
     <button className='cerrar-session' onClick={handleLogout}>Cerrar Sesión</button>
     <span className="welcome-message">¡Bienvenido {username}!  </span>
     <UserAvatar username={username} />
   </div>
   )}
 </div>

 );
 }

export default Header;
