using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class versions : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PalletizingInfo_WarehouseInfo_rgv_warehouse_id",
                table: "PalletizingInfo");

            migrationBuilder.RenameColumn(
                name: "rgv_warehouse_id",
                table: "PalletizingInfo",
                newName: "palletizing_warehouse_id");

            migrationBuilder.RenameIndex(
                name: "IX_PalletizingInfo_rgv_warehouse_id",
                table: "PalletizingInfo",
                newName: "IX_PalletizingInfo_palletizing_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_PalletizingInfo_WarehouseInfo_palletizing_warehouse_id",
                table: "PalletizingInfo",
                column: "palletizing_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PalletizingInfo_WarehouseInfo_palletizing_warehouse_id",
                table: "PalletizingInfo");

            migrationBuilder.RenameColumn(
                name: "palletizing_warehouse_id",
                table: "PalletizingInfo",
                newName: "rgv_warehouse_id");

            migrationBuilder.RenameIndex(
                name: "IX_PalletizingInfo_palletizing_warehouse_id",
                table: "PalletizingInfo",
                newName: "IX_PalletizingInfo_rgv_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_PalletizingInfo_WarehouseInfo_rgv_warehouse_id",
                table: "PalletizingInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
