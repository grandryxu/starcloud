using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editExportStockColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "expstock_creat_datetime",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "expstock_creat_uid",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "expstock_is_delete",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "expstock_modify_datetime",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "expstock_modify_uid",
                table: "ExportStock");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "expstock_creat_datetime",
                table: "ExportStock",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "expstock_creat_uid",
                table: "ExportStock",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "expstock_is_delete",
                table: "ExportStock",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "expstock_modify_datetime",
                table: "ExportStock",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "expstock_modify_uid",
                table: "ExportStock",
                nullable: true);
        }
    }
}
