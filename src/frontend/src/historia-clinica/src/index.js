import React from 'react';
import { createRoot } from 'react-dom/client'; // Importa createRoot desde react-dom/client
import App from './App';
import './index.css';

const root = createRoot(document.getElementById('root')); // Crea un contenedor de raíz en el elemento con ID 'root'

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

