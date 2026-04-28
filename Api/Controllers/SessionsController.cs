using Api.DTOs;
using Api.Models;
using Api.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class SessionsController : ControllerBase
    {
        private readonly IBetSessionService _sessionService;

        public SessionsController(IBetSessionService sessionService)
        {
            _sessionService = sessionService;
        }

        // POST: api/sessions/start
        [HttpPost("start")]
        public async Task<ActionResult<SessionDto>> Start([FromBody] BetSession session)
        {
            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;
            if (string.IsNullOrEmpty(tokenUid))
                return Unauthorized();

            session.UserProfileFirebaseUid = tokenUid;

            var result = await _sessionService.StartSessionAsync(session);

            return CreatedAtAction(nameof(GetById), new { id = result.Id }, MapToDto(result));
        }

        // PATCH: api/sessions/{id}/end
        [HttpPatch("{id}/end")]
        public async Task<IActionResult> End(int id, [FromBody] decimal finalAmount)
        {
            var session = await _sessionService.GetSessionByIdAsync(id);
            if (session == null)
                return NotFound("Session not found.");

            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;
            if (session.UserProfileFirebaseUid != tokenUid)
                return Forbid();

            var updatedSession = await _sessionService.EndSessionAsync(id, finalAmount);
            return Ok(MapToDto(updatedSession!));
        }

        // GET: api/sessions/{id}
        [HttpGet("{id}")]
        public async Task<ActionResult<SessionDto>> GetById(int id)
        {
            var session = await _sessionService.GetSessionByIdAsync(id);
            if (session == null)
                return NotFound();

            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;
            if (session.UserProfileFirebaseUid != tokenUid)
                return Forbid();

            return Ok(MapToDto(session));
        }

        // GET: api/sessions/user/{uid}
        [HttpGet("user/{uid}")]
        public async Task<IActionResult> GetUserHistory(string uid)
        {
            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;
            if (tokenUid != uid)
                return Forbid("You can only view your own session history.");

            var sessions = await _sessionService.GetUserHistoryAsync(uid);
            return Ok(sessions.Select(MapToDto));
        }

        private static SessionDto MapToDto(BetSession s) =>
            new SessionDto
            {
                Id = s.Id,
                CategoryName = s.Category?.Name ?? "Unknown",
                CategoryIcon = s.Category?.IconName ?? "help",
                BuyIn = s.BuyInAmount,
                CashOut = s.CashOutAmount,
                Profit = s.Profit,
                Date = s.StartTime,
                IsActive = s.IsActive,
            };
    }
}
