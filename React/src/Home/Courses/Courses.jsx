import React, { useEffect, useState } from 'react'
import './Courses.css'
import CourseDetails from './CourseDetails'
import { isEmpty } from '../../utils'
import { testuser } from '../../user'

const userId = testuser.id

const Courses = () => {


    const [courses, setCourses] = useState([])
    const [courseDetails, setCourseDetails] = useState({})

    const getCourses = async () => {
        const response = await fetch('http://localhost:8000/courses')
        const data = await response.json()
        if (response.status !== 200) {
            alert(data.message)
        }
        setCourses(data)

    }

    const getCourseDetails = async (id) => {
        const response = await fetch(`http://localhost:8000/courses/${id}`)
        const data = await response.json()
        if (response.status !== 200) {
            alert(data.message)
        }
        setCourseDetails(data)

    }

    const startCourse = async (courseId) => {
        const response = await fetch(`http://localhost:8000/courses/${courseId}/users/${userId}`,
        {method: 'POST'})
        const data = await response.json()
        if (response.status !== 200) {
            alert(data.message)
        }
        getCourses()
    }

    useEffect(() => {
        getCourses()
    }, [])

    return (
        <>
            <h2 className='section-tittle'>Courses</h2>
            <div className='courses' >
                {courses.map(course => {
                    return (
                        <div key={course.id} className='course-container'>
                                <h3 className='courses-description'>{course.courseLevel}</h3>
                            <div className='courses-image'>
                                <img src="https://via.placeholder.com/300x200/333" alt="course" />
                            </div>
                            <div className='courses-actions'>
                                {course.users.some( user => user.id  === userId) 
                                ? <button> Learn</button> 
                                : <button onClick = {() => startCourse(course.id)}>Start</button>}
                                <button onClick={() => getCourseDetails(course.id)}>Details</button>
                            </div>
                        </div>
                    )
                })}
            </div>
            {!isEmpty(courseDetails) && <CourseDetails courseDetails={courseDetails} />}
            </>
    )
}

export default Courses