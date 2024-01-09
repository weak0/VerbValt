const FormLogin = ({isRegisterActiveHandler}) => {
    return (
        <>
            <div className="landingpage-actions">
                <button onClick={isRegisterActiveHandler}>Sign Up</button>
            </div>
            <div className="form">
                <h1>Sign In</h1>
                <form>
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
                    <button className="form-button ">Sign In</button>
                </form>
            </div>
        </>
    )
}
export default FormLogin;