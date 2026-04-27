using System.ComponentModel.DataAnnotations;

namespace Api.Models
{
    public class BetCategory
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Description { get; set; } = string.Empty;
        public string HexColor { get; set; } = "#2ECC71";
    }
}
