import React from 'react'
import './HomePage.css'
import { PiVault } from "react-icons/pi";
import { CiSettings } from "react-icons/ci";
import { IoIosLogOut } from "react-icons/io";
import { IoMdBook } from "react-icons/io";
import Courses from './Courses/Courses';

const HomePage = () => {
  return (
    <div className="homepage">
      <div className='homepage-sidebar'>

        <div className='homepage-sidebar-top'>
          <img src='https://via.placeholder.com/150' alt='avatar' />
          <IoMdBook size={24} />
          <PiVault size={24} />
        </div>
        <div className='homepage-sidebar-bottom'>
          <CiSettings size={24} onClick={() => console.log("tomek debil")} />
          <IoIosLogOut size={24} />
        </div>

      </div>
      <h1>VerbVault</h1>
      <div className='main'>
        <Courses />
      </div>
    </div>
  )
}

export default HomePage