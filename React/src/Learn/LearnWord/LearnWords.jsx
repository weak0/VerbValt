import React, { useEffect } from 'react'
import { fetchData } from '../../user'
import { testuser } from '../../user'
import './LearnWord.css'


const LearnWords = (props) => {
  const { courseId } = props
  const [word, setWord] = React.useState()
  const [checkResult, setCheckResult] = React.useState()

  const getRandomWord = async () => {
    setCheckResult('')
    if (courseId == 0) {
      return getRandomUserWord()
    }
    const response = await fetchData(`courses/${courseId}/words/random`, "GET")
    setWord(response);
  }
  const getRandomUserWord = async () => {
    const response = await fetchData(`users/${testuser.id}/words/random`, "GET")
    setWord(response);
  }

  // const checkWord = async (wordId, foreignWord) => {
  //   const response = await fetchData(`courses/${courseId}/users/${testuser.id}/words/${wordId}`, "POST", { foreignWord })
  //   setCheckResult(response)
  // }

  const encodeWord = (word) => {
    return word.split('').map(letter => letter === ' ' ? letter : '_ ').join('')
  }

  useEffect(() => {
    getRandomWord()
  }, [])

  return (
    <div>
      {word && <>
        <p className='word-foregin'>{encodeWord(word.foreignWord)}</p>
        <p className='word-translation'>{word.translation}</p>
        <input type="text" className='learn-word-input' value={checkResult} onChange={(e) => setCheckResult(e.target.value) }/>
        <div className='learn-word-buttons'>
        <button  onClick={() => getRandomWord()}>Next</button>
        <button onClick={() => console.log("xd")}>Check</button>
        </div>

      </>}
    </div>
  )
}

export default LearnWords