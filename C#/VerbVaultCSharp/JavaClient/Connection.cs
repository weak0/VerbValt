namespace VerbVaultCSharp.JavaClient;

public interface IConnection
{
    Task<string> GetAsync(string path);
}

public class Connection : IConnection
{
    private HttpClient Client { get; set; }
    public readonly string Url  ;

    public Connection( string url )
    {
        Client = new HttpClient();
        Url = url;
    }
    
    
    public async Task<string> GetAsync(string path)
    {
        var response = await Client.GetAsync(Url + path);
        if (response.IsSuccessStatusCode)
        {
            return await response.Content.ReadAsStringAsync();
        }
        else
        {
            throw new HttpRequestException("Błąd zapytania: " + response.StatusCode);
        }
    }
}