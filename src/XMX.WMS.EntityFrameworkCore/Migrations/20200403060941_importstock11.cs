using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class importstock11 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "task_id",
                table: "ImportStock",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_task_id",
                table: "ImportStock",
                column: "task_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_TaskMainInfo_task_id",
                table: "ImportStock",
                column: "task_id",
                principalTable: "TaskMainInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_TaskMainInfo_task_id",
                table: "ImportStock");

            migrationBuilder.DropIndex(
                name: "IX_ImportStock_task_id",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "task_id",
                table: "ImportStock");
        }
    }
}
