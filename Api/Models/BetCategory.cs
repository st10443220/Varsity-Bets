namespace Api.Models
{
    public class BetCategory
    {
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public string HexColor { get; set; } = "#2ECC71";
    }
}
