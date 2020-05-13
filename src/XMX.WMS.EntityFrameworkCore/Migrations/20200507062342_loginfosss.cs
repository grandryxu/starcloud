using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class loginfosss : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "opt_result",
                table: "WMSOptLogInfo",
                newName: "OptResult");

            migrationBuilder.RenameColumn(
                name: "opt_action",
                table: "WMSOptLogInfo",
                newName: "OptPath");

            migrationBuilder.AddColumn<string>(
                name: "NewVal",
                table: "WMSOptLogInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "OldVal",
                table: "WMSOptLogInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "OptAction",
                table: "WMSOptLogInfo",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "NewVal",
                table: "WMSOptLogInfo");

            migrationBuilder.DropColumn(
                name: "OldVal",
                table: "WMSOptLogInfo");

            migrationBuilder.DropColumn(
                name: "OptAction",
                table: "WMSOptLogInfo");

            migrationBuilder.RenameColumn(
                name: "OptResult",
                table: "WMSOptLogInfo",
                newName: "opt_result");

            migrationBuilder.RenameColumn(
                name: "OptPath",
                table: "WMSOptLogInfo",
                newName: "opt_action");
        }
    }
}
