import React, { useEffect } from 'react'
import { fetchData } from '../../user'
import { testuser } from '../../user'
import './LearnWord.css'
import CheckPopup from '../../Popups/CheckPopup'


const LearnWords = (props) => {
  const { courseId } = props
  const [word, setWord] = React.useState()
  const [inputValue, setInputValue] = React.useState()


  const getRandomWord = async () => {
    setInputValue('')
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

  const checkWord = async (wordId, foreignWord) => {
    const response = await fetchData(`courses/${courseId}/words/foreign`, "POST", { userId: testuser.id, wordId: wordId, word: foreignWord })
    console.log(response)
  }

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
        <input type="text" className='learn-word-input' value={inputValue} onChange={(e) => setInputValue(e.target.value) }/>
        <div className='learn-word-buttons'>
        <button  onClick={() => getRandomWord()}>Next</button>
        <button onClick={() => checkWord(word.wordId, inputValue)}>Check</button>
        </div>
      </>}
    </div>
  )
}

export default LearnWords