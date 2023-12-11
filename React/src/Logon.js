import React, { Component } from 'react'
class Logon extends Component
{
    constructor(props){
        super(props);
        this.state = {
            nickname: '',
            password: '',
            newNickname: '',
            newPassword: '',
            newEmail: '',
            visible: true,
            logged: false
        };
    }
    handleInputChange = (event) =>
    {
        const {id, value} = event.target;
        this.setState({[id]: value});
    }

    submitLogin = () =>
    {
        const {newNickname, newPassword, newEmail } = this.state;
        console.log('New Nickname: ', newNickname);
        console.log('New Password: ', newPassword);
        console.log('New Email: ', newEmail);

        const {nickname, password} = this.state;
        console.log('Nickname: ',nickname);
        console.log('Password: ',password);
    }

    render()
    {
        return (
    
        <div id='LogonContainer'>
            <div id='Register'>
                <div id = 'naglowek_register'><h1>No account? Sign in now!</h1></div> 
                <label htmlFor='newNickname'id='newNicknameLabel'>Nickname:</label>
                    <input
                        id='newNickname'
                        type='text'
                        value={this.state.newNickname}
                        onChange={this.handleInputChange}
                    />
                    <br/>

                <label htmlFor='newPassword' id='newPasswordLabel'>Password:</label>
                    <input
                        id='newPassword'
                        type='password'
                        value={this.state.newPassword}
                        onChange={this.handleInputChange}
                    />
                    <br />

                <label htmlFor='newEmail' id='newEmailLabel'>Email:</label>
                    <input
                        id='newEmail'
                        type='text'
                        value={this.state.newEmail}
                        onChange={this.handleInputChange}
                    />
                    <br/>
                    <button onClick={this.submitLogin}>Sign in</button>
            </div>
            <div id='loginContainer'>
                   <div id = 'naglowek'><h1>Login</h1></div> 
                    <div id = 'loginContent'>
                        <label htmlFor='Nickname'>Nickname:  </label> 
                        <input 
                            id='nickname'
                            type='text'
                            value={this.state.nickname}
                            onChange={this.handleInputChange}                        
                        />
                        <br/><br/><br/>
                        <label htmlFor='Password'>Password:  </label>
                        <input 
                            type='password'
                            id = 'password'
                            value={this.state.password}
                            onChange={this.handleInputChange} 
                        />
                        <br/><br/><br/>
                        <button onClick={this.submitLogin}>Submit</button>
                    </div>
            </div>
        </div>
        )
    }
}
export default Logon;