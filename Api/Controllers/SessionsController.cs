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
        public async Task<ActionResult<SessionDto>> Start([FromBody] StartSessionDto request)
        {
            var tokenUid = User.Claims.FirstOrDefault(c => c.Type == "user_id")?.Value;

            if (string.IsNullOrEmpty(tokenUid))
                return Unauthorized();

            var session = new BetSession
            {
                UserProfileFirebaseUid = tokenUid,
                BuyInAmount = request.BuyInAmount,
                BetCategoryId = request.BetCategoryId,
            };

            try
            {
                var result = await _sessionService.StartSessionAsync(session);

                return CreatedAtAction(nameof(GetById), new { id = result.Id }, MapToDto(result));
            }
            catch (InvalidOperationException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
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

        private static SessionDto MapToDto(BetSession session) =>
            new SessionDto
            {
                Id = session.Id,
                CategoryName = session.Category?.Name ?? "Unknown",
                CategoryIcon = session.Category?.IconName ?? "help",
                BuyIn = session.BuyInAmount,
                CashOut = session.CashOutAmount,
                Profit = session.Profit,
                StartTime = session.StartTime,
                EndTime = session.EndTime,
                IsActive = session.IsActive,
            };
    }
}
