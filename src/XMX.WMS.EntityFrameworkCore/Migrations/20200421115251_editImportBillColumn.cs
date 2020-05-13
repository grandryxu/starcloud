using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editImportBillColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "impstock_creat_datetime",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "impstock_creat_uid",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "impstock_is_delete",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "impstock_modify_datetime",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "impstock_modify_uid",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "imporder_creat_datetime",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "imporder_creat_uid",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "imporder_is_delete",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "imporder_modify_datetime",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "imporder_modify_uid",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "impbody_creat_datetime",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "impbody_creat_uid",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "impbody_is_delete",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "impbody_modify_datetime",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "impbody_modify_uid",
                table: "ImportBillbody");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "impstock_creat_datetime",
                table: "ImportStock",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "impstock_creat_uid",
                table: "ImportStock",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "impstock_is_delete",
                table: "ImportStock",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "impstock_modify_datetime",
                table: "ImportStock",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "impstock_modify_uid",
                table: "ImportStock",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "imporder_creat_datetime",
                table: "ImportOrder",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "imporder_creat_uid",
                table: "ImportOrder",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "imporder_is_delete",
                table: "ImportOrder",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "imporder_modify_datetime",
                table: "ImportOrder",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "imporder_modify_uid",
                table: "ImportOrder",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "impbody_creat_datetime",
                table: "ImportBillbody",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "impbody_creat_uid",
                table: "ImportBillbody",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "impbody_is_delete",
                table: "ImportBillbody",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "impbody_modify_datetime",
                table: "ImportBillbody",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "impbody_modify_uid",
                table: "ImportBillbody",
                nullable: true);
        }
    }
}
