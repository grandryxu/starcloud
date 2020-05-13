using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editUnitAndOther : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_slot_code",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "goods_unit2",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "goods_unit",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_slot_code",
                table: "InventoryInfo",
                column: "inventory_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_unit",
                table: "GoodsInfo",
                column: "goods_unit");

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_unit2",
                table: "GoodsInfo",
                column: "goods_unit2");

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_UnitInfo_goods_unit",
                table: "GoodsInfo",
                column: "goods_unit",
                principalTable: "UnitInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_UnitInfo_goods_unit2",
                table: "GoodsInfo",
                column: "goods_unit2",
                principalTable: "UnitInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_SlotInfo_inventory_slot_code",
                table: "InventoryInfo",
                column: "inventory_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_UnitInfo_goods_unit",
                table: "GoodsInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_UnitInfo_goods_unit2",
                table: "GoodsInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_SlotInfo_inventory_slot_code",
                table: "InventoryInfo");

            migrationBuilder.DropIndex(
                name: "IX_InventoryInfo_inventory_slot_code",
                table: "InventoryInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_unit",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_unit2",
                table: "GoodsInfo");

            migrationBuilder.AlterColumn<string>(
                name: "inventory_slot_code",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<string>(
                name: "goods_unit2",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "goods_unit",
                table: "GoodsInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);
        }
    }
}
