using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class loginfoss : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "opt_time",
                table: "WMSOptLogInfo");

            migrationBuilder.DropColumn(
                name: "userId",
                table: "WMSOptLogInfo");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "opt_time",
                table: "WMSOptLogInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<long>(
                name: "userId",
                table: "WMSOptLogInfo",
                nullable: false,
                defaultValue: 0L);
        }
    }
}
