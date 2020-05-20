using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class flatpickcompany : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "picking_company_id",
                table: "PickingTask",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "flat_company_id",
                table: "FlatBankTask",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_picking_company_id",
                table: "PickingTask",
                column: "picking_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_company_id",
                table: "FlatBankTask",
                column: "flat_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_FlatBankTask_CompanyInfo_flat_company_id",
                table: "FlatBankTask",
                column: "flat_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_CompanyInfo_picking_company_id",
                table: "PickingTask",
                column: "picking_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_FlatBankTask_CompanyInfo_flat_company_id",
                table: "FlatBankTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_CompanyInfo_picking_company_id",
                table: "PickingTask");

            migrationBuilder.DropIndex(
                name: "IX_PickingTask_picking_company_id",
                table: "PickingTask");

            migrationBuilder.DropIndex(
                name: "IX_FlatBankTask_flat_company_id",
                table: "FlatBankTask");

            migrationBuilder.DropColumn(
                name: "picking_company_id",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "flat_company_id",
                table: "FlatBankTask");
        }
    }
}
