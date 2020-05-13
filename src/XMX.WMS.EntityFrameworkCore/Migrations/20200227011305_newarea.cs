using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class newarea : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "area_code",
                table: "AreaInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "area_remark",
                table: "AreaInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_AreaInfo_area_warehouse_id",
                table: "AreaInfo",
                column: "area_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo",
                column: "area_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo");

            migrationBuilder.DropIndex(
                name: "IX_AreaInfo_area_warehouse_id",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_code",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_remark",
                table: "AreaInfo");
        }
    }
}
