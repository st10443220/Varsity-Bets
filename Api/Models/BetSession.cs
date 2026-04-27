using System.ComponentModel.DataAnnotations.Schema;

namespace Api.Models
{
    public class BetSession
    {
        public int Id { get; set; }

        // This links to the 'User' navigation property below
        [ForeignKey("User")]
        public string UserProfileFirebaseUid { get; set; } = string.Empty;

        // This links to the 'Category' navigation property below
        [ForeignKey("Category")]
        public int BetCategoryId { get; set; }

        public decimal BuyInAmount { get; set; }
        public decimal CashOutAmount { get; set; }
        public DateTime StartTime { get; set; } = DateTime.UtcNow;
        public DateTime? EndTime { get; set; }

        // Navigation properties
        // These are the "objects" EF uses to join the tables
        public UserProfile? User { get; set; }
        public BetCategory? Category { get; set; }
    }
}
