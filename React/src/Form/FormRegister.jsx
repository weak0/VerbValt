import React from "react";
import "./Form.css";

const FormRegister = ({isRegisterActiveHandler}) => {
  return (
    <>
      <div className="landingpage-actions">
        <button onClick={isRegisterActiveHandler}>Sign In</button>
      </div>
      <div className="form">
        <h1>Register</h1>
        <form>
          <input
            type="text"
            name="name"
            placeholder="Name"
            className="form-input"
          />
          <input
            type="text"
            name="email"
            placeholder="Email"
            className="form-input"
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            className="form-input"
          />
          <input
            type="text"
            name="password"
            placeholder="Repeat password"
            className="form-input"
          />
          <button className="form-button ">Register</button>
        </form>
      </div>
    </>
  );
};

export default FormRegister;


// rafce
