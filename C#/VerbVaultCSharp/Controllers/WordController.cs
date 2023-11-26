using System.Runtime.InteropServices.JavaScript;
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
    //1. dodaj encje na podstawie skrina ktory wysłał tomek  --ok
    //2. dodaj do verbvault db - DbSet<User> Users { get; set; } -- done(?)
    
    // pobieranie słowka - GET words/{wordId} -- done(?)
    // (lista) pobieranie wszystkich słowek dla użytkownika - GET words/{userId} -- done(?)
    // usuwanie słowka - DELETE words/{wordId} -- done(?)
    // zapisanie słowka - POST add -- done(?)
    // modyfikacja słowka - PATCH words/{wordId} -- done(?)
    
    
    //sprawdz co robi appi controller
    


    [HttpGet("{wordId}")]
    public ActionResult GetSingleWord([FromRoute] int wordId)
    {
        var word = _db.words.SingleOrDefault(wordid => wordid.id == wordId);
        if (word != null)
        {
            return Ok(word);
        }
        else
        {
            return NotFound("Word with this ID hasn't been found.");
        }
    }

    [HttpGet("userlistofwords/{userId}")]
    public ActionResult<List<Word>>  GetListWords([FromRoute] int userId)
    {
        var wordlist = _db.words.Where(user => user.user_id == userId).ToList();
        return Ok(wordlist);
    }

    [HttpPost("AddWord")]
    public ActionResult AddWord([FromRoute] int wordId, int user_id, String foreign_word, String translation)
    {
        Word word = new Word(wordId, user_id, foreign_word, translation);
        _db.words.Add(word);
        _db.SaveChanges();
        var words = _db.words.ToList();
        return Ok("Word added!\n"+words);
    }

    [HttpDelete("DeleteWord/{wordId}")]
    public ActionResult DeleteWord([FromRoute] int wordId)
    {
        var word = _db.words.SingleOrDefault(wordid => wordid.id == wordId);
        _db.words.Remove(word);
        _db.SaveChanges();
        var words = _db.words.ToList();
        return Ok("Word deleted!\n"+words);
    }

    [HttpPatch("ModifyWord/{wordId}")]
    public ActionResult ModifyWord([FromRoute] int wordId, int user_id, String foreign_word, String translation, [FromBody] Word wordtoupdate)
    {
        var word = _db.words.SingleOrDefault(wordid => wordid.id == wordId);
        if (word != null)
        {
            wordtoupdate.user_id = user_id;
            wordtoupdate.foreign_word = foreign_word;
            wordtoupdate.translation = translation;
            return Ok("Word updated!");
        }
        else
        {
            return NotFound("Word with provided ID not found");
        }
    }

    // przykładowy endpoint
    // - czasownik http 
    //[HttpGet("UpdateToDb")]
    //public async Task<ActionResult> UpdateToDb()
    //{
    //    var posts = await _phClient.GetPostsAsync();


    //    var postsToDb = posts.GroupBy(post => post.UserId).Select(x => new Users()
    //    {
    //        Id = x.Key,
    //        Posts = x.ToList()
    //    });
    //    var updatedPosts = 0;
    //    foreach (var userPosts in postsToDb)
    //    {
    //        var existingUser = await _db.Users.FindAsync(userPosts.Id);
    //        if (existingUser != null)
    //            continue;

    //        updatedPosts += userPosts.Posts.Count;
    //        _db.Users.Add(userPosts);
    //    }
    //    await _db.SaveChangesAsync();
    //    return Ok($"Updated {updatedPosts} posts");
    //}
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