using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class orderBodyId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "imporder_body_id",
                table: "ImportOrder",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "exporder_body_id",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_body_id",
                table: "ImportOrder",
                column: "imporder_body_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_body_id",
                table: "ExportOrder",
                column: "exporder_body_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder",
                column: "exporder_body_id",
                principalTable: "ExportBillbody",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportOrder_ImportBillbody_imporder_body_id",
                table: "ImportOrder",
                column: "imporder_body_id",
                principalTable: "ImportBillbody",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportOrder_ImportBillbody_imporder_body_id",
                table: "ImportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ImportOrder_imporder_body_id",
                table: "ImportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_exporder_body_id",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "imporder_body_id",
                table: "ImportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_body_id",
                table: "ExportOrder");
        }
    }
}
