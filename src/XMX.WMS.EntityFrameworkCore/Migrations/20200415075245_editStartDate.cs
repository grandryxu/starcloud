using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editStartDate : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "startDate",
                table: "StockTasking",
                newName: "task_start_date");

            migrationBuilder.RenameColumn(
                name: "endDate",
                table: "StockTasking",
                newName: "task_end_date");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "task_start_date",
                table: "StockTasking",
                newName: "startDate");

            migrationBuilder.RenameColumn(
                name: "task_end_date",
                table: "StockTasking",
                newName: "endDate");
        }
    }
}
