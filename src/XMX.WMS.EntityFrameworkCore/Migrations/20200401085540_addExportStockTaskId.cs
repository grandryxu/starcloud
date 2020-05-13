using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addExportStockTaskId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "expstock_task_id",
                table: "ExportStock",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_task_id",
                table: "ExportStock",
                column: "expstock_task_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_TaskMainInfo_expstock_task_id",
                table: "ExportStock",
                column: "expstock_task_id",
                principalTable: "TaskMainInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_TaskMainInfo_expstock_task_id",
                table: "ExportStock");

            migrationBuilder.DropIndex(
                name: "IX_ExportStock_expstock_task_id",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "expstock_task_id",
                table: "ExportStock");
        }
    }
}
