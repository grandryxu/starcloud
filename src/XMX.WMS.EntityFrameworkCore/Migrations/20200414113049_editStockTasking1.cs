using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editStockTasking1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_InventoryInfo_task_inventory_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropColumn(
                name: "task_bill_bar",
                table: "StockTasking");

            migrationBuilder.DropColumn(
                name: "task_stock_code",
                table: "StockTasking");

            migrationBuilder.RenameColumn(
                name: "task_inventory_id",
                table: "StockTaskingDetail",
                newName: "task_slot_id");

            migrationBuilder.RenameIndex(
                name: "IX_StockTaskingDetail_task_inventory_id",
                table: "StockTaskingDetail",
                newName: "IX_StockTaskingDetail_task_slot_id");

            migrationBuilder.AddColumn<string>(
                name: "task_batch_no",
                table: "StockTaskingDetail",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "task_count",
                table: "StockTaskingDetail",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "task_stock_code",
                table: "StockTaskingDetail",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "endDate",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "startDate",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "task_goods_id",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_StockTasking_task_goods_id",
                table: "StockTasking",
                column: "task_goods_id");

            migrationBuilder.AddForeignKey(
                name: "FK_StockTasking_GoodsInfo_task_goods_id",
                table: "StockTasking",
                column: "task_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail",
                column: "task_slot_id",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_StockTasking_GoodsInfo_task_goods_id",
                table: "StockTasking");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropIndex(
                name: "IX_StockTasking_task_goods_id",
                table: "StockTasking");

            migrationBuilder.DropColumn(
                name: "task_batch_no",
                table: "StockTaskingDetail");

            migrationBuilder.DropColumn(
                name: "task_count",
                table: "StockTaskingDetail");

            migrationBuilder.DropColumn(
                name: "task_stock_code",
                table: "StockTaskingDetail");

            migrationBuilder.DropColumn(
                name: "endDate",
                table: "StockTasking");

            migrationBuilder.DropColumn(
                name: "startDate",
                table: "StockTasking");

            migrationBuilder.DropColumn(
                name: "task_goods_id",
                table: "StockTasking");

            migrationBuilder.RenameColumn(
                name: "task_slot_id",
                table: "StockTaskingDetail",
                newName: "task_inventory_id");

            migrationBuilder.RenameIndex(
                name: "IX_StockTaskingDetail_task_slot_id",
                table: "StockTaskingDetail",
                newName: "IX_StockTaskingDetail_task_inventory_id");

            migrationBuilder.AddColumn<string>(
                name: "task_bill_bar",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "task_stock_code",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_InventoryInfo_task_inventory_id",
                table: "StockTaskingDetail",
                column: "task_inventory_id",
                principalTable: "InventoryInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
