import React from 'react'
import './Courses.css'

const CourseDetails = ({courseDetails}) => {
    const { courseSentences, courseWords} = courseDetails
    console.log(courseSentences, courseWords)
  return (
   <>
    <h2 className='section-tittle '>Courses Details</h2>
    <div className='details-words-main'>
    <div className='details-header details-table'>
        <h3>Words</h3>
        <h3>Translation</h3>
    </div>
    {courseWords.map(word => {
        return (
            <div key={word.id} className='details-row  details-table'>
                <p>{word.foreignWord}</p>
                <p>{word.translation}</p>
            </div>
        )
    }
    
    )}
    </div>
    <div className='details-words-main'>
    <div className='details-header details-table'>
        <h3>Sentence</h3>
        <h3>Translation</h3>
    </div>
    {courseSentences.map(word => {
        return (
            <div key={word.id} className='details-row  details-table'>
                <p>{word.foreignSentence}</p>
                <p>{word.translation}</p>
            </div>
        )
    }
    
    )}
    </div>
   </>
  )
}

export default CourseDetails