using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class optmodules : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "OptModule",
                table: "WMSOptLogInfo",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "OptModule",
                table: "WMSOptLogInfo");
        }
    }
}
