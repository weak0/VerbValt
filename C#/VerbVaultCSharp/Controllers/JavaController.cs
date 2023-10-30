using Microsoft.AspNetCore.Mvc;
using VerbVaultCSharp.JavaClient;

namespace VerbVaultCSharp.Controllers;

public class JavaController : ControllerBase

{
    private readonly IGetUsers _getUsers;

    public JavaController(IGetUsers getUsers)
    {
        _getUsers = getUsers;
    }

    [HttpGet("/users")]
    public async Task<ActionResult<User>> Get()
    {
        var result = await _getUsers.GetUsersAsync();
        return Ok(result);

    }
}