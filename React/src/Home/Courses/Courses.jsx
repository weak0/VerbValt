import React, { useEffect, useState } from 'react'

const Courses = () => {


    const [courses, setCourses] = useState([])

   const getCourses = async () => {
        const response = await fetch('http://localhost:8000/courses')
        const data = await response.json()
        if (response.status !== 200) {
            alert(data.message)
        }
        setCourses(data)

    }
        
   useEffect(() => {
        getCourses()
    }, []) 

  return (
    <div>
        <h2>Courses</h2>
        {courses.map(course => {
            return (
                <div key={course.id}>
                    <h3>{course.courseLevel}</h3>
                </div>
            )
        })}
    </div>
  )
}

export default Courses