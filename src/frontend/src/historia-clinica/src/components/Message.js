import React from 'react';
import './message.css';

const Message = ({ text, onClose }) => {
  return (
    <div className="message-container">
      <div className="message">
        <p>{text}</p>
        <button className="button-close" onClick={onClose}>Cerrar</button>
      </div>
    </div>
  );
};

export default Message;
