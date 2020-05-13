using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editHistoryTask : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "main_creat_datetime",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_modify_datetime",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "main_modify_uid",
                table: "HistoryTaskMainInfo",
                newName: "material_name");

            migrationBuilder.RenameColumn(
                name: "main_creat_uid",
                table: "HistoryTaskMainInfo",
                newName: "exporder_batch_no");

            migrationBuilder.AddColumn<decimal>(
                name: "exporder_quantity",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<Guid>(
                name: "main_company_id",
                table: "HistoryTaskMainInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "material_id",
                table: "HistoryTaskMainInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_company_id",
                table: "HistoryTaskMainInfo",
                column: "main_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_HistoryTaskMainInfo_CompanyInfo_main_company_id",
                table: "HistoryTaskMainInfo",
                column: "main_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_HistoryTaskMainInfo_CompanyInfo_main_company_id",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_HistoryTaskMainInfo_main_company_id",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropColumn(
                name: "exporder_quantity",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_company_id",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropColumn(
                name: "material_id",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "material_name",
                table: "HistoryTaskMainInfo",
                newName: "main_modify_uid");

            migrationBuilder.RenameColumn(
                name: "exporder_batch_no",
                table: "HistoryTaskMainInfo",
                newName: "main_creat_uid");

            migrationBuilder.AddColumn<DateTime>(
                name: "main_creat_datetime",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "main_modify_datetime",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }
    }
}
