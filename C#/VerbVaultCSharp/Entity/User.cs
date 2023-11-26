namespace VerbVaultCSharp.Entity;

public class User
{
    public string Email { get; set; }
    public string Password { get; set; }
    public string UserName { get; set; }
    public List<Word> Words { get; set; }
}