
import './App.css'
import HomePage from './Home/HomePage';
import { testuser } from './user'


function App() {

  localStorage.setItem('user', testuser);
  const user = localStorage.getItem('user');

  return (
    <>
{     !user ? <div className="landingpage">
        <div className='landingpage-description'>
            <h1>VerbVault</h1>
            <h2>Learn English Verbs</h2>
            <p>VerbVault is a simple app to help you learn English verbs.</p>
            <p>The app is under development so please be patient... </p>
            <button>Get Started</button>
        </div>
      <div className='landingpage-actions'>
        <button>Sign In</button>
        <button>Sign Up</button>
      </div>
      </div> : <HomePage />}
    </>
  )
}

export default App
