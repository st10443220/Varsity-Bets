using Api.Data;
using Microsoft.EntityFrameworkCore;

namespace Api.Services
{
    public class AnalyticsService : IAnalyticsService
    {
        private readonly ApplicationDbContext _context;

        public AnalyticsService(ApplicationDbContext context) => _context = context;

        public async Task<object> GetUserStatsAsync(string firebaseUid)
        {
            var sessions = await _context
                .BetSessions.Where(s => s.UserProfileFirebaseUid == firebaseUid)
                .ToListAsync();

            if (!sessions.Any())
                return new
                {
                    TotalProfit = 0,
                    WinRate = 0,
                    BestGame = "None",
                };

            var totalProfit = sessions.Sum(s => s.CashOutAmount - s.BuyInAmount);
            var wins = sessions.Count(s => s.CashOutAmount > s.BuyInAmount);
            var winRate = (double)wins / sessions.Count * 100;

            return new
            {
                TotalProfit = totalProfit,
                WinRate = Math.Round(winRate, 2),
                TotalSessions = sessions.Count,
            };
        }
    }
}
