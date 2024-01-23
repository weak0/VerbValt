import React, { useState } from 'react';
import './EditWords.css';
import { fetchData } from '../../user';
import { data } from '../../user';

const WordTable = ({ words, handleReload }) => {
  const [userWords, setUserWords] = useState(words);
  const [editMode, setEditMode] = useState(false);
  const [editIndex, setEditIndex] = useState(null);
  const [editWord, setEditWord] = useState({});

  const handleDelete = async (id) => {
    await fetchData(`users/${data.user.id}/words/${id}`, "DELETE")
    const response = await fetchData(`users/${data.user.id}/words`, "GET")
    setUserWords(response)
  };

  const handleEdit = (index) => {
    setEditMode(true);
    setEditIndex(index);
    setEditWord(words[index]);
  };

  const handleSave = async (index) => {
    await fetchData(`users/${data.user.id}/words`, "PUT", { foreignWord: editWord.foreignWord, translation: editWord.translation, wordId: editWord.wordId})
    setEditMode(false);
    setEditIndex(null);
    const response = await fetchData(`users/${data.user.id}/words`, "GET")
    setUserWords(response)
  };

  const handleCancel = () => {
    setEditMode(false);
    setEditIndex(null);
  };

  return (
    <table className="word-table">
      <thead>
        <tr>
          <th>Słowo</th>
          <th>Tłumaczenie</th>
          <th>Akcje</th>
        </tr>
      </thead>
      <tbody>
        {userWords.map((word, index) => (
          <tr key={word.wordId}>
            <td>{editMode && editIndex === index ? <input value={editWord.foreignWord} onChange={(e) => setEditWord({...editWord, foreignWord: e.target.value})} /> : word.foreignWord}</td>
            <td>{editMode && editIndex === index ? <input value={editWord.translation} onChange={(e) => setEditWord({...editWord, translation: e.target.value})} /> : word.translation}</td>
            <td>
              {editMode && editIndex === index ? (
                <>
                  <button className="button" onClick={() => handleSave(word.wordId)}>Zapisz</button>
                  <button className="button button-delete" onClick={handleCancel}>Anuluj</button>
                </>
              ) : (
                <>
                  <button className="button" onClick={() => handleEdit(index)}>Edytuj</button>
                  <button className="button button-delete" onClick={() => handleDelete(word.wordId)}>Usuń</button>
                </>
              )}
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default WordTable;