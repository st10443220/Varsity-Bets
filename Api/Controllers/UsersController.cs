using Api.Data;
using Api.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : Controller
    {
        private readonly ApplicationDbContext _context;

        public UsersController(ApplicationDbContext context)
        {
            _context = context;
        }

        // POST: api/users/sync
        [HttpPost("sync")]
        public async Task<IActionResult> SyncUser([FromBody] UserProfile userProfile)
        {
            // Not valid user
            if (userProfile == null || string.IsNullOrEmpty(userProfile.FirebaseUid))
            {
                return BadRequest("Invalid user profile data.");
            }

            // Check if existing user
            var existingUser = await _context.UserProfiles.FirstOrDefaultAsync(existingUser =>
                existingUser.FirebaseUid == userProfile.FirebaseUid
            );

            // If user null, create new user
            if (existingUser == null)
            {
                _context.UserProfiles.Add(userProfile);
            }
            else
            {
                // Update the existing user with the new data
                existingUser.FullName = userProfile.FullName;
                existingUser.Username = userProfile.Username;
                existingUser.CreatedAt = userProfile.CreatedAt;
            }

            await _context.SaveChangesAsync();

            return Ok(userProfile);
        }
    }
}
