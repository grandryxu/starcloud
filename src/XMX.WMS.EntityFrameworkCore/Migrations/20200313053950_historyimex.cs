using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class historyimex : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "history_task_id",
                table: "ImportOrder",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "history_task_id",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_history_task_id",
                table: "ImportOrder",
                column: "history_task_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_history_task_id",
                table: "ExportOrder",
                column: "history_task_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_HistoryTaskMainInfo_history_task_id",
                table: "ExportOrder",
                column: "history_task_id",
                principalTable: "HistoryTaskMainInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportOrder_HistoryTaskMainInfo_history_task_id",
                table: "ImportOrder",
                column: "history_task_id",
                principalTable: "HistoryTaskMainInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_HistoryTaskMainInfo_history_task_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportOrder_HistoryTaskMainInfo_history_task_id",
                table: "ImportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ImportOrder_history_task_id",
                table: "ImportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_history_task_id",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "history_task_id",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "history_task_id",
                table: "ExportOrder");
        }
    }
}
