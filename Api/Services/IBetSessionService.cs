using Api.Models;

namespace Api.Services
{
    public interface IBetSessionService
    {
        Task<BetSession> StartSessionAsync(BetSession session);
        Task<BetSession?> EndSessionAsync(int sessionId, decimal finalCashOut);
        Task<BetSession?> GetSessionByIdAsync(int id);
        Task<IEnumerable<BetSession>> GetUserHistoryAsync(string firebaseUid);
    }
}
