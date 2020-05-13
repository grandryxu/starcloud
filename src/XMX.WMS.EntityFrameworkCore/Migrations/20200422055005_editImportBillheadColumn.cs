using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editImportBillheadColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "imphead_creat_datetime",
                table: "ImportBillhead");

            migrationBuilder.DropColumn(
                name: "imphead_creat_uid",
                table: "ImportBillhead");

            migrationBuilder.DropColumn(
                name: "imphead_is_delete",
                table: "ImportBillhead");

            migrationBuilder.DropColumn(
                name: "imphead_modify_datetime",
                table: "ImportBillhead");

            migrationBuilder.DropColumn(
                name: "imphead_modify_uid",
                table: "ImportBillhead");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "imphead_creat_datetime",
                table: "ImportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "imphead_creat_uid",
                table: "ImportBillhead",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "imphead_is_delete",
                table: "ImportBillhead",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "imphead_modify_datetime",
                table: "ImportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "imphead_modify_uid",
                table: "ImportBillhead",
                nullable: true);
        }
    }
}
