using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

#pragma warning disable CA1814 // Prefer jagged arrays over multidimensional

namespace Api.Migrations
{
    /// <inheritdoc />
    public partial class InitialCreate : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "BetCategories",
                columns: table => new
                {
                    Id = table
                        .Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Description = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    HexColor = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IconName = table.Column<string>(type: "nvarchar(max)", nullable: false),
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BetCategories", x => x.Id);
                }
            );

            migrationBuilder.CreateTable(
                name: "UserProfiles",
                columns: table => new
                {
                    FirebaseUid = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    FullName = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    Username = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CreatedAt = table.Column<DateTime>(type: "datetime2", nullable: false),
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserProfiles", x => x.FirebaseUid);
                }
            );

            migrationBuilder.CreateTable(
                name: "BetSessions",
                columns: table => new
                {
                    Id = table
                        .Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    UserProfileFirebaseUid = table.Column<string>(
                        type: "nvarchar(450)",
                        nullable: false
                    ),
                    BetCategoryId = table.Column<int>(type: "int", nullable: false),
                    BuyInAmount = table.Column<decimal>(
                        type: "decimal(18,2)",
                        precision: 18,
                        scale: 2,
                        nullable: false
                    ),
                    CashOutAmount = table.Column<decimal>(
                        type: "decimal(18,2)",
                        precision: 18,
                        scale: 2,
                        nullable: false
                    ),
                    StartTime = table.Column<DateTime>(type: "datetime2", nullable: false),
                    EndTime = table.Column<DateTime>(type: "datetime2", nullable: true),
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BetSessions", x => x.Id);
                    table.ForeignKey(
                        name: "FK_BetSessions_BetCategories_BetCategoryId",
                        column: x => x.BetCategoryId,
                        principalTable: "BetCategories",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade
                    );
                    table.ForeignKey(
                        name: "FK_BetSessions_UserProfiles_UserProfileFirebaseUid",
                        column: x => x.UserProfileFirebaseUid,
                        principalTable: "UserProfiles",
                        principalColumn: "FirebaseUid",
                        onDelete: ReferentialAction.Cascade
                    );
                }
            );

            migrationBuilder.InsertData(
                table: "BetCategories",
                columns: new[] { "Id", "Description", "HexColor", "IconName", "Name" },
                values: new object[,]
                {
                    { 1, "Track your spins", "#E74C3C", "casino", "Roulette" },
                    { 2, "Hit or Stand", "#2C3E50", "style", "Blackjack" },
                    { 3, "Spin to win", "#F1C40F", "seven_poker", "Slots" },
                }
            );

            migrationBuilder.CreateIndex(
                name: "IX_BetSessions_BetCategoryId",
                table: "BetSessions",
                column: "BetCategoryId"
            );

            migrationBuilder.CreateIndex(
                name: "IX_BetSessions_UserProfileFirebaseUid",
                table: "BetSessions",
                column: "UserProfileFirebaseUid"
            );
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(name: "BetSessions");

            migrationBuilder.DropTable(name: "BetCategories");

            migrationBuilder.DropTable(name: "UserProfiles");
        }
    }
}
