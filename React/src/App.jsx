import "./App.css";
import HomePage from "./Home/HomePage";
import FormRegister from "./Form/FormRegister";
import FormLogin from "./Form/FormLogin";
import { useEffect, useState } from "react";

function App() {
  const [user, setUser] = useState();



  const [isRegisterActive, setRegisterActive] = useState(true);
  function isRegisterActiveHandler() {
    setRegisterActive(prevState => !prevState);
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
              <button onClick={isRegisterActiveHandler}>{isRegisterActive ? 'Sign In' : 'Sing Un'}</button>
            </div>
            {isRegisterActive ? <FormLogin loginHandler={(token) => setUser(token)} /> : <FormRegister />}
          </div>
        </div>
      ) : (
        <HomePage loginHandler={() => setUser()} />
      )}
    </>
  );
}

export default App;
