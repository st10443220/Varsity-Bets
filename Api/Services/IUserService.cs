using Api.DTOs;
using Api.Models;

namespace Api.Services
{
    public interface IUserService
    {
        Task<UserDto> SyncUserAsync(UserProfile profile);
    }
}
