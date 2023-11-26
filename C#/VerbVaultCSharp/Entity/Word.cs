using System.Runtime.InteropServices.ComTypes;

namespace VerbVaultCSharp.Entity;

public class Word
{
    public int id {get; set; }
    public string foreign_word { get; set; }
    public string translation { get; set; }
    public int user_id { get; set; }

    public Word(int id, int user_id, string foreign_word, string translation)
    {
        this.id = id;
        this.foreign_word = foreign_word;
        this.translation = translation;
        this.user_id = user_id;
    }
}