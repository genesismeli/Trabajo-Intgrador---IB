import React, { useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import { BrowserRouter as Redirect } from 'react-router-dom';
import { REGEX_EMAIL, REGEX_ALPHANUMERIC } from "./../../constants";
import Swal from "sweetalert2";
import { registerUser } from "../../actions/auth-action";

import './register.css';

const Register = () => {
  const [register, setRegister] = useState({});
  const [errors, setErrors] = useState([]);
  const history = useNavigate();
  const handleRegister = (event) => {
  const saveRegisterDataAsync = async (dataRegister) => {
      const responseRegister = await registerUser(dataRegister);
      if (responseRegister?.status === 201 || responseRegister?.code === 201) {
        Swal.fire({
          title: "Acceso",
          text: "Usted se ha registrado con exito.",
          icon: "success",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#1DBEB4",
        }).then((result) => {
          if (result.isConfirmed) {
            history("/login");
          }
        })
        } else {
        Swal.fire({
          title: "Acceso",
          text: "Lamentablemente no ha podido registrarse. Por favor intente más tarde",
          icon: "error",
          confirmButtonText: "Aceptar",
          confirmButtonColor: "#1DBEB4",
        });
      }
    };

    const formDataValidator = (e) => {
      e.preventDefault();
      let data = e.target;
      let dataRegister = {
        name: data.name.value,
        lastname: data.lastName.value,
        username: data.email.value,
        password: data.password.value,
        rePassword: data.confirmPassword.value,
      };
      console.log(dataRegister);
      const validateErrors = Object.keys(errors);

      let numberErrors = 0;
      validateErrors.forEach((element) => {
        if (errors[element]?.hasError) {
          numberErrors++;
          return false;
        }
      });

      if (numberErrors === 0) {
        setRegister(dataRegister);
        saveRegisterDataAsync(dataRegister);
      } else {
        Swal.fire({
          title: "Acceso",
          text: "Porfavor revise los datos de ingresados en el formulario de registro",
          icon: "error",
          button: "Ok",
          confirmButtonColor: "#1565C0",
        });
      }
    };
  const registerForm = [
     {
       name: "name",
       formName: "Nombre",
       required: false,
       min: 5,
       max: 50,
       regexEmail: false,
       regexAlphaNumeric: false,
       matchWith: "",
     },
     {
       name: "lastName",
       formName: "Apellido",
       required: false,
       min: 5,
       max: 50,
       regexEmail: false,
       regexAlphaNumeric: false,
       matchWith: "",
     },
     {
       name: "email",
       formName: "Correo electrónico",
       required: false,
       min: 5,
       max: 50,
       regexAlphaNumeric: false,
       regexEmail: true,
       matchWith: "",
     },
     {
       name: "password",
       formName: "Contraseña",
       required: false,
       min: 5,
       max: 20,
       regexEmail: false,
       regexAlphaNumeric: true,
       matchWith: "confirmPassword",
     },
     {
       name: "confirmPassword",
       formName: "Confirmar contraseña",
       required: false,
       min: 5,
       max: 20,
       regexEmail: false,
       regexAlphaNumeric: true,
       matchWith: "password",
     },
   ];

   const showErrors = () => {
     console.log(errors);
   };

   const validateForm = (name, value) => {
     const fieldConfiguration = registerForm.find(
       (config) => config.name === name
     );
     setErrors((inv) => ({
       ...inv,
       [name]: {
         data: value,
         hasError: false,
       },
     }));

     if (fieldConfiguration.required && !value) {
       setErrors((inv) => ({
         ...inv,
         [name]: {
           hasError: true,
           data: value,
           message: `${fieldConfiguration.formName} es requerido`,
         },
       }));
     }

     if (fieldConfiguration.regexEmail) {
       var regExpEmail = REGEX_EMAIL.test(value);
       if (!regExpEmail) {
         setErrors((inv) => ({
           ...inv,
           [name]: {
             hasError: true,
             data: value,
             message: `${fieldConfiguration.formName} no tiene un formato de email valido`,
           },
         }));
       }
     }

     if (fieldConfiguration.regexAlphaNumeric) {
       var regExpAlphanumeric = REGEX_ALPHANUMERIC.test(value);
       if (!regExpAlphanumeric) {
         setErrors((inv) => ({
           ...inv,
           [name]: {
             hasError: true,
             data: value,
             message: `${fieldConfiguration.formName} debe contener caracteres alfanumericos`,
           },
         }));
       }
     }

     if (
       fieldConfiguration.min > value.length + 1 ||
       fieldConfiguration.max <= value.length + 1
     ) {
       setErrors((inv) => ({
         ...inv,
         [name]: {
           hasError: true,
           data: value,
           message: `${fieldConfiguration.formName} debe estar entre ${fieldConfiguration.min} y ${fieldConfiguration.max} caracteres`,
         },
       }));
     } else {
       setErrors((inv) => ({
         ...inv,
         [name]: {
           hasError: false,
           data: value,
           message: "",
         },
       }));
     }

     if (fieldConfiguration.matchWith && fieldConfiguration.name === name) {
       const dataPassword = errors[fieldConfiguration.matchWith]?.data;
       if (dataPassword && dataPassword !== value) {
         setErrors((inv) => ({
           ...inv,
           [name]: {
             hasError: true,
             data: value,
             message: `Las contraseñas deben ser iguales`,
           },
         }));
       } else {
         setErrors((inv) => ({
           ...inv,
           [fieldConfiguration.matchWith]: {
             hasError: false,
             message: "",
           },
         }));
       }
     }

     console.log(value);
     setTimeout(() => {
       showErrors();
     }, 5);
   };

   const handleKeyUp = (e) => {
     const { name, value } = e.target;
     validateForm(name, value);
   };

   const showErrorIcon = (errorText) => {
     return (
       <>
         <ErrorIcon color="error" fontSize="inherit" />
         <span style={{ color: "red" }}>{errorText}</span>
       </>
     );
   };

   return (
     <>

       <Body />
       <div className="container-register">
         <h1>Crear Cuenta</h1>
         <form onSubmit={formDataValidator} className="register-form">
           <div className="form-register-info">
             <div className="form-register-name">
               <label htmlFor="name">Nombre</label>
               <input
                 type="text"
                 placeholder="nombre"
                 name="name"
                 id="name"
                 onKeyUp={handleKeyUp}
               />
               <div className="errors-message">
                 {errors &&
                   errors["name"]?.hasError &&
                   showErrorIcon(errors["name"].message)}
               </div>
             </div>
             <div className="form-register-lastName">
               <label>Apellido</label>
               <input
                 type="text"
                 placeholder="apellido"
                 name="lastName"
                 onKeyUp={handleKeyUp}
               />
               <div className="errors-message">
                 {errors &&
                   errors["lastName"]?.hasError &&
                   showErrorIcon(errors["lastName"].message)}
               </div>
             </div>
           </div>

           <div className="form-register-fields">
             <label>Correo electrónico</label>
             <input
               type="email"
               placeholder="correo electronico"
               name="email"
               onKeyUp={handleKeyUp}
             />
             <div>
               {errors &&
                 errors["email"]?.hasError &&
                 showErrorIcon(errors["email"].message)}
             </div>

             <label>Contraseña</label>
             <input
               type="password"
               placeholder="contraseña"
               name="password"
               onKeyUp={handleKeyUp}
             />
             <div>
               {errors &&
                 errors["password"]?.hasError &&
                 showErrorIcon(errors["password"].message)}
             </div>

             <label>Confirmar contraseña</label>
             <input
               type="password"
               placeholder="contraseña"
               name="confirmPassword"
               onKeyUp={handleKeyUp}
             />
             <div>
               {errors &&
                 errors["confirmPassword"]?.hasError &&
                 showErrorIcon(errors["confirmPassword"].message)}
             </div>
           </div>
             <button type="submit" value="Ingresar" className="button-register">
               Registrar
             </button>
            </form>
         <div className="text-register">
           <span>
             ¿Ya tienes una cuenta? <Link to="/login">Iniciar sesión</Link>
           </span>
         </div>


     </>;
   );
 };

 export default Register;

