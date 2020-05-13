using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addInventoryColumnBT : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "inventory_datetime",
                table: "InventoryInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_warehouse_id",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_quality_status",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_goods_id",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_company_id",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "AbpUsers",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo",
                column: "inventory_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo",
                column: "inventory_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
                table: "InventoryInfo",
                column: "inventory_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
                table: "InventoryInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_warehouse_id",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_quality_status",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_goods_id",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_company_id",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<DateTime>(
                name: "inventory_datetime",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "AbpUsers",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo",
                column: "inventory_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo",
                column: "inventory_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
                table: "InventoryInfo",
                column: "inventory_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
