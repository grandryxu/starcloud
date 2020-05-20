using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editQualityCheck : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_InventoryInfo_check_inventory_id",
                table: "QualityCheck");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_inventory_warehouse_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "bill_type",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_bill",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_goods_code",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_goods_name",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_released",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "checked_quality_status",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "checked_quality_status_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "exist_out_bill",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "stock_num",
                table: "QualityCheck");

            migrationBuilder.RenameColumn(
                name: "remark",
                table: "QualityCheck",
                newName: "check_remark");

            migrationBuilder.RenameColumn(
                name: "quality_check_export_code",
                table: "QualityCheck",
                newName: "check_export_bill");

            migrationBuilder.RenameColumn(
                name: "origin_quality_status",
                table: "QualityCheck",
                newName: "check_code");

            migrationBuilder.RenameColumn(
                name: "inventory_warehouse_id",
                table: "QualityCheck",
                newName: "check_warehouse_id");

            migrationBuilder.RenameColumn(
                name: "check_inventory_id",
                table: "QualityCheck",
                newName: "check_origin_quality");

            migrationBuilder.RenameIndex(
                name: "IX_QualityCheck_inventory_warehouse_id",
                table: "QualityCheck",
                newName: "IX_QualityCheck_check_warehouse_id");

            migrationBuilder.RenameIndex(
                name: "IX_QualityCheck_check_inventory_id",
                table: "QualityCheck",
                newName: "IX_QualityCheck_check_origin_quality");

            migrationBuilder.AddColumn<int>(
                name: "check_bill_status",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<Guid>(
                name: "check_checked_quality",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "check_goods_id",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "check_released_status",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "check_type",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheck_check_checked_quality",
                table: "QualityCheck",
                column: "check_checked_quality");

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheck_check_goods_id",
                table: "QualityCheck",
                column: "check_goods_id");

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_QualityInfo_check_checked_quality",
                table: "QualityCheck",
                column: "check_checked_quality",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_GoodsInfo_check_goods_id",
                table: "QualityCheck",
                column: "check_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_QualityInfo_check_origin_quality",
                table: "QualityCheck",
                column: "check_origin_quality",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_check_warehouse_id",
                table: "QualityCheck",
                column: "check_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_QualityInfo_check_checked_quality",
                table: "QualityCheck");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_GoodsInfo_check_goods_id",
                table: "QualityCheck");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_QualityInfo_check_origin_quality",
                table: "QualityCheck");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_check_warehouse_id",
                table: "QualityCheck");

            migrationBuilder.DropIndex(
                name: "IX_QualityCheck_check_checked_quality",
                table: "QualityCheck");

            migrationBuilder.DropIndex(
                name: "IX_QualityCheck_check_goods_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_bill_status",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_checked_quality",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_goods_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_released_status",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "check_type",
                table: "QualityCheck");

            migrationBuilder.RenameColumn(
                name: "check_warehouse_id",
                table: "QualityCheck",
                newName: "inventory_warehouse_id");

            migrationBuilder.RenameColumn(
                name: "check_remark",
                table: "QualityCheck",
                newName: "remark");

            migrationBuilder.RenameColumn(
                name: "check_origin_quality",
                table: "QualityCheck",
                newName: "check_inventory_id");

            migrationBuilder.RenameColumn(
                name: "check_export_bill",
                table: "QualityCheck",
                newName: "quality_check_export_code");

            migrationBuilder.RenameColumn(
                name: "check_code",
                table: "QualityCheck",
                newName: "origin_quality_status");

            migrationBuilder.RenameIndex(
                name: "IX_QualityCheck_check_warehouse_id",
                table: "QualityCheck",
                newName: "IX_QualityCheck_inventory_warehouse_id");

            migrationBuilder.RenameIndex(
                name: "IX_QualityCheck_check_origin_quality",
                table: "QualityCheck",
                newName: "IX_QualityCheck_check_inventory_id");

            migrationBuilder.AddColumn<string>(
                name: "bill_type",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "check_bill",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "check_goods_code",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "check_goods_name",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "check_released",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "checked_quality_status",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "checked_quality_status_id",
                table: "QualityCheck",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AddColumn<int>(
                name: "exist_out_bill",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "stock_num",
                table: "QualityCheck",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_InventoryInfo_check_inventory_id",
                table: "QualityCheck",
                column: "check_inventory_id",
                principalTable: "InventoryInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_WarehouseInfo_inventory_warehouse_id",
                table: "QualityCheck",
                column: "inventory_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
