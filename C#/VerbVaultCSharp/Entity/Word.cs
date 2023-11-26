namespace VerbVaultCSharp.Entity;

public class Word
{
    public int id {get; set; }
    public string foreign_word { get; set; }
    public string translation { get; set; }
    public int user_id { get; set; }
}