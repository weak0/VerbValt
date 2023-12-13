import React from 'react'
import './Learn.css'
import { learnTypeEnum } from '../utils'
import LearnWords from './LearnWord/LearnWords'
import LearnSentences from './LearnSentences'

const Learn = (props) => {
    const { learnTypeProp, courseId } = props
    const [learnType, setLearnType] = React.useState(learnTypeProp)
    return (
        <div>
            <h2>Learn</h2>
            {learnType == learnTypeEnum.Default && <div className='dialog-box'>
                <button className='dialog-box-button' onClick={() => setLearnType(learnTypeEnum.Words)}>Words</button>
                <button className='dialog-box-button' onClick={() => setLearnType(learnTypeEnum.Sentences)}>Sentences</button>
            </div>}
            {learnType == learnTypeEnum.Words &&
                <div className='dialog-box'>
                    <LearnWords courseId={courseId} />
                </div>}
            {learnType == learnTypeEnum.Sentences &&
                <div className='dialog-box'>
                    <LearnSentences courseId={courseId} />
                </div>}

        </div>
    )
}

export default Learn