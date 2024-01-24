import React from 'react'
import ReactDOM from 'react-dom'
import './Notification.css'

const Notification = (props) => {
  const { message } = props
  const isSucssed = message.includes('Brawo');

  return ReactDOM.createPortal(
    <div className='notifcation-container'>

      <div className={isSucssed ? "checkpopup-sucessed" : "checkpopup-failed"}>
        <h4>{message}</h4>
      </div>
    </div>,
    document.getElementById('portal')
  );
}

export default Notification