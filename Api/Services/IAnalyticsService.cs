namespace Api.Services
{
    public interface IAnalyticsService
    {
        Task<object> GetUserStatsAsync(string firebaseUid);
    }
}
