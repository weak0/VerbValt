using Microsoft.EntityFrameworkCore;

namespace VerbVaultCSharp.Entity;

public class VerbVaultDb : DbContext
{
    public VerbVaultDb(DbContextOptions<VerbVaultDb> options) : base(options) { }
    public DbSet<Word> words { get; set; }

}