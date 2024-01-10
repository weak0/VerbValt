import React, { useEffect } from 'react'

const CheckPopup = (props) => {
   const [isPopupActive, setIsPopupActive] = React.useState(false)

   useEffect(() => {
      setTimeout(() => {
         setIsPopupActive(false)
      }, 2000);
    }, [isPopupActive])

    const { message } = props
    const isSucssed = message.includes = "Brawo"
  return (
    <div className={isSucssed ? "checkpopup-sucessed" : "checkpopup-failed"} >
        <h4>{message}</h4>
    </div>
  )
}

export default CheckPopup