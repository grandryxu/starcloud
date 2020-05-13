using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addWarehouseId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "impbody_warehouse_id",
                table: "ImportBillbody",
                nullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_width",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_weight",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_water_low",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_water_high",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "goods_type",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_qty",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_min",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_max",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_small_qty",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_price",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_medium_qty",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_length",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_large_qty",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_height",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(decimal));

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_warehouse_id",
                table: "ImportBillbody",
                column: "impbody_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ImportBillbody_WarehouseInfo_impbody_warehouse_id",
                table: "ImportBillbody",
                column: "impbody_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ImportBillbody_WarehouseInfo_impbody_warehouse_id",
                table: "ImportBillbody");

            migrationBuilder.DropIndex(
                name: "IX_ImportBillbody_impbody_warehouse_id",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "impbody_warehouse_id",
                table: "ImportBillbody");

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_width",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_weight",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_water_low",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_water_high",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "goods_type",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_qty",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_min",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_stock_max",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_small_qty",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_price",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_medium_qty",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_length",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_large_qty",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);

            migrationBuilder.AlterColumn<decimal>(
                name: "goods_height",
                table: "GoodsInfo",
                nullable: false,
                oldClrType: typeof(decimal),
                oldNullable: true);
        }
    }
}
