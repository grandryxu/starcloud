using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editGoodsInfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_distribution_id",
                table: "GoodsInfo",
                column: "goods_distribution_id");

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_monitor_id",
                table: "GoodsInfo",
                column: "goods_monitor_id");

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_warehousing_id",
                table: "GoodsInfo",
                column: "goods_warehousing_id");

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_StrategyDistribution_goods_distribution_id",
                table: "GoodsInfo",
                column: "goods_distribution_id",
                principalTable: "StrategyDistribution",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_StrategyMonitor_goods_monitor_id",
                table: "GoodsInfo",
                column: "goods_monitor_id",
                principalTable: "StrategyMonitor",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_StrategyWarehousing_goods_warehousing_id",
                table: "GoodsInfo",
                column: "goods_warehousing_id",
                principalTable: "StrategyWarehousing",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_StrategyDistribution_goods_distribution_id",
                table: "GoodsInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_StrategyMonitor_goods_monitor_id",
                table: "GoodsInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_StrategyWarehousing_goods_warehousing_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_distribution_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_monitor_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_warehousing_id",
                table: "GoodsInfo");
        }
    }
}
