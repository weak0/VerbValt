import "./Form.css";

const FormRegister = () => {
  const register = async (e) => {
    e.preventDefault();
    const form = document.getElementById("register");
    const role = document.getElementById("role");
    const formData = new FormData(form);
    const obj = Object.fromEntries(formData);

    if (obj.password !== obj.passwordRepeat) {
      alert("Passwords do not match");
      return;
    }
    const json = JSON.stringify(obj);
    console.log(json,role.value);

    // const userTokenResponse = await fetch(
    //   "http://127.0.0.1:8000/users/register",
    //   {
    //     method: "POST",
    //     body: json,
    //     headers: {
    //       "Content-Type": "application/json",
    //     },
    //   }
    // );
  //   const token = JSON.parse(userTokenResponse).token;
  //   console.log(token);
  };
  return (
    <div className="form">
      <h1>Register</h1>
      <form id="register">
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
          type="password"
          name="passwordRepeat"
          placeholder="Repeat password"
          className="form-input"
        />
        <label className="form-label">
        Wybierz rolę:
          <select id="role" className="form-select">
            <option value="student">Student</option>
            <option value="tutor">Tutor</option>
          </select>
        </label>
        <button className="form-button" onClick={register}>
          Register
        </button>
      </form>
    </div>
  );
};

export default FormRegister;
