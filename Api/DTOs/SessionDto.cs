namespace Api.DTOs
{
    public class SessionDto
    {
        public int Id { get; set; }
        public string CategoryName { get; set; } = string.Empty;
        public string CategoryIcon { get; set; } = string.Empty;
        public decimal BuyIn { get; set; }
        public decimal CashOut { get; set; }
        public decimal Profit { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public bool IsActive { get; set; }
    }
}
