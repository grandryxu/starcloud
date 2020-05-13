using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addCompanyId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "main_company_id",
                table: "TaskMainInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_company_id",
                table: "TaskMainInfo");
        }
    }
}
