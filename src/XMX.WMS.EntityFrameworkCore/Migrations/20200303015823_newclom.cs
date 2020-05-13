using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class newclom : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "main_manual_flag",
                table: "HistoryTaskMainInfo",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "main_execute_flag",
                table: "HistoryTaskMainInfo",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "main_manual_flag",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<string>(
                name: "main_execute_flag",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(int));
        }
    }
}
