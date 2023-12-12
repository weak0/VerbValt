import React, { useEffect } from 'react'
import { fetchData } from '../user'
import { testuser } from '../user'


const LearnWords = (props) => {
  const { courseId } = props
  const [word, setWord] = React.useState("")

  const getRandomWord = async () => {
    if (courseId === 0){
      return getRandomUserWord()
    }
    const response = await fetchData(`courses/${courseId}/words/random`, "GET")
    setWord(response);
  } 
  const getRandomUserWord = async () => {
    const response = await fetchData(`courses/${courseId}/users/${testuser.id}/words/random`, "GET")
    setWord(response);
  }

  const encodeWord = (word) => {
    return word.split('').map(letter =>  letter === ' ' ? letter : '_ ').join('')
  }

  useEffect(() => {
    getRandomWord()
  }, [])

  return (
    <div>
        {word && 
        encodeWord(word.foreignWord)
        }
    </div>
  )
}

export default LearnWords