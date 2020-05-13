using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class qualitywarehouse : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "inventory_warehouse_id",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheck_inventory_warehouse_id",
                table: "QualityCheck",
                column: "inventory_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_inventory_warehouse_id",
                table: "QualityCheck",
                column: "inventory_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_inventory_warehouse_id",
                table: "QualityCheck");

            migrationBuilder.DropIndex(
                name: "IX_QualityCheck_inventory_warehouse_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "inventory_warehouse_id",
                table: "QualityCheck");
        }
    }
}
