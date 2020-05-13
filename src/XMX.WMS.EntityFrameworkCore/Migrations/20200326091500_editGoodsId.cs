using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editGoodsId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ImportOrder_GoodsInfo_imporder_imporder_id",
                table: "ImportOrder");

            migrationBuilder.RenameColumn(
                name: "imporder_imporder_id",
                table: "ImportOrder",
                newName: "imporder_goods_id");

            migrationBuilder.RenameIndex(
                name: "IX_ImportOrder_imporder_imporder_id",
                table: "ImportOrder",
                newName: "IX_ImportOrder_imporder_goods_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ImportOrder_GoodsInfo_imporder_goods_id",
                table: "ImportOrder",
                column: "imporder_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ImportOrder_GoodsInfo_imporder_goods_id",
                table: "ImportOrder");

            migrationBuilder.RenameColumn(
                name: "imporder_goods_id",
                table: "ImportOrder",
                newName: "imporder_imporder_id");

            migrationBuilder.RenameIndex(
                name: "IX_ImportOrder_imporder_goods_id",
                table: "ImportOrder",
                newName: "IX_ImportOrder_imporder_imporder_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ImportOrder_GoodsInfo_imporder_imporder_id",
                table: "ImportOrder",
                column: "imporder_imporder_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
