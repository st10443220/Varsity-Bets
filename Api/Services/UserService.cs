using Api.Data;
using Api.DTOs;
using Api.Models;

namespace Api.Services
{
    public class UserService : IUserService
    {
        private readonly ApplicationDbContext _context;

        public UserService(ApplicationDbContext context) => _context = context;

        public async Task<UserDto> SyncUserAsync(UserProfile profile)
        {
            var existing = await _context.UserProfiles.FindAsync(profile.FirebaseUid);
            if (existing == null)
            {
                _context.UserProfiles.Add(profile);
            }
            else
            {
                existing.FullName = profile.FullName;
                existing.Username = profile.Username;
            }
            await _context.SaveChangesAsync();
            return new UserDto
            {
                FirebaseUid = profile.FirebaseUid,
                FullName = profile.FullName,
                Username = profile.Username,
            };
        }
    }
}
