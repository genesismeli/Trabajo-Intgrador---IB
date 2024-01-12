import axios from "Axios";

export const loginUser = (loginData) =>
  new Promise((resolve, reject) => {
    const headers = {
      "Content-Type": "application/json",
    };

    axios
      .post("http://localhost:8081/auth/login", loginData, headers)
      .then((response) => {
        resolve(response);
      })
      .catch((error) => {
        resolve(error.response);
        reject(error.response);
      });
  });

