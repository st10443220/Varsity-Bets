using Api.Data;
using Api.Models;
using Microsoft.EntityFrameworkCore;

namespace Api.Services
{
    public class BetSessionService : IBetSessionService
    {
        private readonly ApplicationDbContext _context;

        public BetSessionService(ApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<BetSession> StartSessionAsync(BetSession session)
        {
            var hasActiveSession = await _context.BetSessions.AnyAsync(s =>
                s.UserProfileFirebaseUid == session.UserProfileFirebaseUid && s.EndTime == null
            );

            if (hasActiveSession)
            {
                throw new InvalidOperationException(
                    "You already have an active session running. Please end it before starting a new one."
                );
            }

            if (session.BuyInAmount <= 0)
            {
                throw new ArgumentException("You need a positive buy-in to start a session.");
            }

            var categoryExists = await _context.BetCategories.AnyAsync(c =>
                c.Id == session.BetCategoryId
            );
            if (!categoryExists)
            {
                throw new KeyNotFoundException("The selected betting category does not exist.");
            }

            session.StartTime = DateTime.UtcNow;

            _context.BetSessions.Add(session);
            await _context.SaveChangesAsync();

            await _context.Entry(session).Reference(s => s.Category).LoadAsync();

            return session;
        }

        public async Task<BetSession?> EndSessionAsync(int sessionId, decimal finalCashOut)
        {
            var session = await _context.BetSessions.FindAsync(sessionId);

            if (session == null)
                return null;

            if (session.EndTime != null)
            {
                throw new InvalidOperationException("This session has already been closed.");
            }

            session.CashOutAmount = finalCashOut;
            session.EndTime = DateTime.UtcNow;

            await _context.SaveChangesAsync();
            return session;
        }

        public async Task<BetSession?> GetSessionByIdAsync(int id)
        {
            return await _context
                .BetSessions.Include(s => s.Category)
                .FirstOrDefaultAsync(s => s.Id == id);
        }

        public async Task<IEnumerable<BetSession>> GetUserHistoryAsync(string firebaseUid)
        {
            return await _context
                .BetSessions.Where(s => s.UserProfileFirebaseUid == firebaseUid)
                .OrderByDescending(s => s.StartTime)
                .ToListAsync();
        }
    }
}
