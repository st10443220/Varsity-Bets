using Api.DTOs;
using Api.Models;
using Api.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

[Route("api/[controller]")]
[ApiController]
[Authorize]
public class UsersController : ControllerBase
{
    private readonly IUserService _userService;

    public UsersController(IUserService userService) => _userService = userService;

    [HttpPost("sync")]
    public async Task<IActionResult> SyncUser([FromBody] UserProfile userProfile)
    {
        var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;
        if (tokenUid != userProfile.FirebaseUid)
            return Forbid();

        var result = await _userService.SyncUserAsync(userProfile);
        return Ok(result);
    }

    [HttpGet("profile")]
    public async Task<IActionResult> GetUserProfile()
    {
        var firebaseUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;

        if (string.IsNullOrEmpty(firebaseUid))
            return Unauthorized();

        var userProfile = await _userService.GetUserProfileAsync(firebaseUid);

        if (userProfile == null)
            return NotFound();

        return Ok(
            new UserDto
            {
                FirebaseUid = userProfile.FirebaseUid,
                FullName = userProfile.FullName,
                Username = userProfile.Username,
            }
        );
    }
}
