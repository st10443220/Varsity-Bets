using Api.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class AnalyticsController : ControllerBase
    {
        private readonly IAnalyticsService _analyticsService;

        public AnalyticsController(IAnalyticsService analyticsService)
        {
            _analyticsService = analyticsService;
        }

        [HttpGet("user/{uid}")]
        public async Task<IActionResult> GetStats(string uid)
        {
            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;

            if (tokenUid != uid)
            {
                return Forbid("You cannot access analytics for another user.");
            }

            var stats = await _analyticsService.GetUserStatsAsync(uid);
            return Ok(stats);
        }
    }
}
