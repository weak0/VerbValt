import "./App.css";
import HomePage from "./Home/HomePage";
import  FormRegister from "./Form/FormRegister";
import  FormLogin from "./Form/FormLogin";
import { useState } from "react";
import { testuser } from "./user";

function App() {
  //localStorage.setItem('user', testuser);
  const user = localStorage.getItem("user");

  const [isRegisterActive, setRegisterActive] = useState(true);
  function isRegisterActiveHandler() {
    setRegisterActive(prevState=>!prevState);
  }
  return (
    <>
      {!user ? (
        <div className="container">
          <div className="landingpage">
            <div className="landingpage-description">
              <h1>VerbVault</h1>
              <h2>Learn English Verbs</h2>
              <p>VerbVault is a simple app to help you learn English verbs.</p>
              <p>The app is under development so please be patient... </p>
              <button>Get Started</button>
            </div>
            <div className="landingpage-actions">
        <button onClick={isRegisterActiveHandler}>{isRegisterActive ? 'Sign In':'Sing Un'}</button>
      </div>
            {isRegisterActive ? <FormLogin /> : <FormRegister/>}
          </div>
        </div>
      ) : (
        <HomePage />
      )}
    </>
  );
}

export default App;
