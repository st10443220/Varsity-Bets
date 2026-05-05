using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Api.Models
{
    public class BetSession
    {
        [Key]
        public int Id { get; set; }

        [ForeignKey("User")]
        [Required]
        public string UserProfileFirebaseUid { get; set; } = string.Empty;

        [ForeignKey("Category")]
        [Required]
        public int BetCategoryId { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal BuyInAmount { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal CashOutAmount { get; set; }

        public DateTime StartTime { get; set; } = DateTime.UtcNow;
        public DateTime? EndTime { get; set; }

        public UserProfile? User { get; set; }
        public BetCategory? Category { get; set; }

        [NotMapped]
        public decimal Profit => CashOutAmount - BuyInAmount;

        [NotMapped]
        public bool IsActive => EndTime == null;
    }
}
