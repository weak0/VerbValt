import React from 'react'
import { fetchData } from '../../user'
import './Words.css'
import { IoMdArrowBack } from "react-icons/io";
import { data } from '../../user'
import { learnTypeEnum } from '../../utils';
import WordTable from './EditWords';


const pageEnum = {
  default: 1,
  addWords: 2,
  editWords: 3
}

const Words = (props) => {
  const { setupLearn } = props
  const [page, setPage] = React.useState(pageEnum.default);
  const [foreignWord, setForeignWord] = React.useState("");
  const [translation, setTranslation] = React.useState("");
  const [userWords, setUserWords] = React.useState([]) 

  const addWord = async () => {
    await fetchData(`users/${data.user.id}/words`, "POST", { foreign: foreignWord, translate: translation })
    setForeignWord("")
    setTranslation("")
    getUserWords()
  }
  const getUserWords = async () => {
    const response = await fetchData(`users/${data.user.id}/words`, "GET")
    setUserWords(response)
  }
  React.useEffect(() => {
    getUserWords()
  }, [])

  return (
    <>
      <h2 className='section-tittle'>Words</h2>
      {page === pageEnum.default && 
      <div className='dialog-box'>
        <button className='dialog-box-button' disabled={userWords.length === 0} onClick={() => setupLearn(0, learnTypeEnum.Words)}>Learn words</button>
        <button className='dialog-box-button' onClick={() => setPage(pageEnum.addWords)}>Add words</button>
        <button className='dialog-box-button' disabled={userWords.length === 0} onClick={() => setPage(pageEnum.editWords)}>Edit words</button>
      </div >}
      {page === pageEnum.addWords && 
      <div className='dialog-box add-word'>
        <IoMdArrowBack  size={24} className='back-icon' onClick={() => setPage(pageEnum.default)}/>
        <label htmlFor='add-word-foregin'> Foregin </label>
        <input type='text' placeholder='Word' id="add-word-foregin" value={foreignWord} onChange={(e) => setForeignWord(e.target.value)} />
        <label htmlFor='add-word-translation'> Translaation </label>
        <input type='text' placeholder='Translation' id="add-word-translation" value={translation} onChange={(e) => setTranslation(e.target.value)} />
        <button className='dialog-box-button' onClick={addWord}>Add</button>
      </div>}
      {page === pageEnum.editWords && <WordTable words={userWords} handleReload={() => getUserWords} />}
    </>
  )
}

export default Words