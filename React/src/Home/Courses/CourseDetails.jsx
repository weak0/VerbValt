import React from 'react'

const CourseDetails = ({courseDetails}) => {
    const { courseSentences, courseWords} = courseDetails
    console.log(courseSentences, courseWords)
  return (
   <>
    <h2 className='section-tittle'>Courses Details</h2>
    <div className='details-header'>
        <p>Words</p>
        <p>Translation</p>
    </div>
    {courseWords.map(word => {
        return (
            <div key={word.id} className='details-row'>
                <p>{word.word}</p>
                <p>{word.translation}</p>
            </div>
        )
    }
    )}
   </>
  )
}

export default CourseDetails