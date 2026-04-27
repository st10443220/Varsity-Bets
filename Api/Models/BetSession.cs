using System.ComponentModel.DataAnnotations.Schema;

namespace Api.Models
{
    public class BetSession
    {
        public int Id { get; set; }

        [ForeignKey("User")]
        public string UserProfileFirebaseUid { get; set; } = string.Empty;

        [ForeignKey("Category")]
        public int BetCategoryId { get; set; }

        // Added precision attributes here
        [Column(TypeName = "decimal(18,2)")]
        public decimal BuyInAmount { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal CashOutAmount { get; set; }

        public DateTime StartTime { get; set; } = DateTime.UtcNow;
        public DateTime? EndTime { get; set; }

        // Navigation properties
        public UserProfile? User { get; set; }
        public BetCategory? Category { get; set; }
    }
}
