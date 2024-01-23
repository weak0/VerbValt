import React, { useEffect } from 'react'
import { fetchData, data } from '../../user'
import './LearnWord.css'
import Notification from '../../Popups/Notification'

const LearnWords = (props) => {
  const { courseId, type } = props
  const [word, setWord] = React.useState()
  const [inputValue, setInputValue] = React.useState()
  const [message, setMessage] = React.useState('')

  const getRandomWord = async () => {
    setInputValue('')
    if (courseId == 0) {
      return getRandomUserWord()
    }
    const response = await fetchData(`courses/${courseId}/${type}/random`, "GET")
    setWord(response);
  }
  const getRandomUserWord = async () => {
    const response = await fetchData(`users/${data.user.id}/${type}/random`, "GET")
    setWord(response);
  }

  const checkWord = async (wordId, foreignWord) => {
    if (courseId == 0) {
      return checkUserWord(wordId, foreignWord)
    }
    const payload = type == 'words' ? { userId: data.user.id, wordId: wordId, word: foreignWord } : { userId: data.user.id, sentenceId: wordId, sentence: foreignWord }
    const response = await fetchData(`courses/${courseId}/${type}/foreign`, "POST", payload)
    setMessage(response.status)
    getRandomWord()
    setTimeout(() => {
      setMessage('')
    }, 2000);
  }

  const checkUserWord = async (wordId, foreignWord) => {
    const response = await fetchData(`users/${data.user.id}/${type}/foreign`, "POST", { userId: data.user.id, wordId: wordId, word: foreignWord })
    setMessage(response.status)
    getRandomWord()
    setTimeout(() => {
      setMessage('')
    }, 2000);
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
       {message && <Notification message={message} />}
        <p className='word-foregin'>{encodeWord(type == 'words' ? word.foreignWord : word.foreignSentence)}</p>
        <p className='word-translation'>{ word.translation}</p>
        <input type="text" className='learn-word-input' value={inputValue} onChange={(e) => setInputValue(e.target.value)} />
        <div className='learn-word-buttons'>
          <button onClick={() => getRandomWord()}>Next</button>
          <button onClick={() => checkWord(type === 'words'?word.wordId : word.sentenceId, inputValue)}>Check</button>
        </div>
      </>}
    </div>
  )
}

export default LearnWords