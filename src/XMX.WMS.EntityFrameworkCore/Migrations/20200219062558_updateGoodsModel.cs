using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class updateGoodsModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "goods_area_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "goods_describe",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "goods_distribution_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "goods_en_name",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "goods_monitor_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "goods_price",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "goods_type",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "goods_unit2",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "goods_warehousing_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "goods_water_high",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "goods_water_low",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "goods_weight",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_area_id",
                table: "GoodsInfo",
                column: "goods_area_id");

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_AreaInfo_goods_area_id",
                table: "GoodsInfo",
                column: "goods_area_id",
                principalTable: "AreaInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_AreaInfo_goods_area_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_area_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_area_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_describe",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_distribution_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_en_name",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_monitor_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_price",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_type",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_unit2",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_warehousing_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_water_high",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_water_low",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_weight",
                table: "GoodsInfo");
        }
    }
}
