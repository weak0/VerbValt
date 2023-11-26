using Microsoft.AspNetCore.Mvc;
using VerbVaultCSharp.Entity;

namespace VerbVaultCSharp.Controllers;


[Route("/words")]
public class WordController : ControllerBase
{
    private readonly VerbVaultDb _db;

    public WordController(VerbVaultDb db)
    {
        _db = db;
    }
    
    //zmapowałem ci words  na word z bazy danych bedziesz tez potrzebował usera, ale mysle ze sobie poradzisz
    //1. dodaj encje na podstawie skrina ktory wysłał tomek
    //2. dodaj do verbvault db - DbSet<User> Users { get; set; }
    
    // pobieranie słowka - GET words/{wordId}
    // lista
    // pobieranie wszystkich słowek dla użytkownika - GET words/{userId}
    //sprawdz co robi appi controller
    // zapisanie słowka - POST add
    // usuwanie słowka - DELETE words/{wordId}
    // modyfikacja słowka - PATCH words/{wordId}

    [HttpGet]
    public ActionResult GetWords()
    {
        var words = _db.words.ToList();
        return Ok(words);
    }
   
    // przykładowy endpoint
    // - czasownik http 
    // [HttpGet("UpdateToDb")]
    // public async Task<ActionResult> UpdateToDb()
    // {
    //     var posts = await _phClient.GetPostsAsync();
    //     
    //
    //     var postsToDb = posts.GroupBy(post => post.UserId).Select(x => new Users()
    //     {
    //         Id = x.Key,
    //         Posts = x.ToList()
    //     });
    //     var updatedPosts = 0;
    //     foreach (var userPosts in postsToDb)
    //     {
    //         var existingUser = await _db.Users.FindAsync(userPosts.Id);
    //         if (existingUser != null) 
    //             continue;
    //         
    //         updatedPosts += userPosts.Posts.Count;
    //         _db.Users.Add(userPosts);
    //     }
    //     await _db.SaveChangesAsync();
    //     return Ok($"Updated {updatedPosts} posts");
    // }
    //
    // [HttpGet("GetFromDb")]
    // public ActionResult<List<PostsDto>> GetPosts()
    // {
    //     var posts = _db.Posts.ToList();
    //     var result = posts.Select(post => new PostsDto(post.Body)
    //     {
    //         Id = post.Id,
    //         Title = post.Title,
    //     });
    //     return Ok(result);
    // }
    // [HttpDelete("DeleteFromDb")]
    // public async Task<ActionResult> DeleteFromDb()
    // {
    //     var users = await _db.Users.ToListAsync();
    //     _db.Users.RemoveRange(users);
    //     await _db.SaveChangesAsync();
    //     return Ok();
    // }
    
}