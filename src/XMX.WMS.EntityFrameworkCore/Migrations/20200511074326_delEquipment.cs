using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delEquipment : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
