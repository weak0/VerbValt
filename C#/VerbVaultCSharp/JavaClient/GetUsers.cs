using Newtonsoft.Json;

namespace VerbVaultCSharp.JavaClient;

public interface IGetUsers
{
    Task<List<JUser>> GetUsersAsync();
}

public class GetUsers : IGetUsers
{
    private readonly IConnection _connection;
    
    public GetUsers(IConnection connection)
    {
        _connection = connection;
    }
    
    public async Task<List<JUser>> GetUsersAsync()
    {
          var result  = await _connection.GetAsync("/users");
          var mappedResult  = JsonConvert.DeserializeObject<List<JUser>>(result);
          return mappedResult;
    }
    
    
}