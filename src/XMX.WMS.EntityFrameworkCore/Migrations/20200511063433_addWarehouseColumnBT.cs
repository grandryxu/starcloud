using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addWarehouseColumnBT : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_CompanyInfo_expstock_company_id",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseStock_CompanyInfo_warehouse_company_id",
                table: "WarehouseStock");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock");

            migrationBuilder.DropIndex(
                name: "IX_WarehouseStock_warehouse_company_id",
                table: "WarehouseStock");

            migrationBuilder.DropIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropIndex(
                name: "IX_ExportStock_expstock_company_id",
                table: "ExportStock");

            migrationBuilder.DropColumn(
                name: "warehouse_company_id",
                table: "WarehouseStock");

            migrationBuilder.DropColumn(
                name: "inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "expstock_company_id",
                table: "ExportStock");

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_id",
                table: "WarehouseStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo",
                column: "warehouse_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock",
                column: "warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock");

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_id",
                table: "WarehouseStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseStock",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "inventory_company_id",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AddColumn<Guid>(
                name: "expstock_company_id",
                table: "ExportStock",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_WarehouseStock_warehouse_company_id",
                table: "WarehouseStock",
                column: "warehouse_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_company_id",
                table: "ExportStock",
                column: "expstock_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_CompanyInfo_expstock_company_id",
                table: "ExportStock",
                column: "expstock_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo",
                column: "warehouse_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseStock_CompanyInfo_warehouse_company_id",
                table: "WarehouseStock",
                column: "warehouse_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock",
                column: "warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
