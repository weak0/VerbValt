import "./Form.css";

const FormLogin = ({loginHandler}) => {

  const login= async(e) => {
    e.preventDefault();
    const form = document.getElementById("logIn");
    const formData = new FormData(form);
    const obj = Object.fromEntries(formData);
    
    const json = JSON.stringify(obj);
    const userTokenResponse = await fetch(
      "http://127.0.0.1:8000/users/authenticate",
      {
        method: "POST",
        body: json,
        headers: {
          "Content-Type": "application/json",
        },
      })

    if (userTokenResponse.ok) {
      const responseData = await userTokenResponse.json();
      const token = responseData.token;
    
      localStorage.setItem("token", token);
      loginHandler(token);
    }else {
      alert("Wrong email or password");
    }  
  }
  return (
    <>
      <div className="form">
        <h1>Sign In</h1>
        <form id = "logIn">
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
          <button className="form-button" onClick={login}>Sign In</button>
        </form>
      </div>
    </>
  );
};

export default FormLogin;
