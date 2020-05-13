using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class stocktaking : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Stocktaking",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    CreatorUserId = table.Column<long>(nullable: true),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    stocktaking_code = table.Column<string>(nullable: true),
                    type = table.Column<int>(nullable: false),
                    bill_bar = table.Column<string>(nullable: true),
                    stock_code = table.Column<string>(nullable: true),
                    count = table.Column<decimal>(nullable: false),
                    state = table.Column<int>(nullable: false),
                    remark = table.Column<string>(nullable: true),
                    operate_person = table.Column<string>(nullable: true),
                    operate_time = table.Column<DateTime>(nullable: false),
                    company_id = table.Column<Guid>(nullable: true),
                    warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Stocktaking", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Stocktaking_CompanyInfo_company_id",
                        column: x => x.company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Stocktaking_WarehouseInfo_warehouse_id",
                        column: x => x.warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "StocktakingDetail",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    CreatorUserId = table.Column<long>(nullable: true),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    state = table.Column<int>(nullable: false),
                    dcount = table.Column<decimal>(nullable: false),
                    acount = table.Column<decimal>(nullable: false),
                    operate_person = table.Column<string>(nullable: true),
                    operate_time = table.Column<DateTime>(nullable: false),
                    company_id = table.Column<Guid>(nullable: true),
                    stocktaking_id = table.Column<Guid>(nullable: true),
                    goods_id = table.Column<Guid>(nullable: true),
                    inventory_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StocktakingDetail", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StocktakingDetail_CompanyInfo_company_id",
                        column: x => x.company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StocktakingDetail_GoodsInfo_goods_id",
                        column: x => x.goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StocktakingDetail_InventoryInfo_goods_id",
                        column: x => x.goods_id,
                        principalTable: "InventoryInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StocktakingDetail_Stocktaking_stocktaking_id",
                        column: x => x.stocktaking_id,
                        principalTable: "Stocktaking",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Stocktaking_company_id",
                table: "Stocktaking",
                column: "company_id");

            migrationBuilder.CreateIndex(
                name: "IX_Stocktaking_warehouse_id",
                table: "Stocktaking",
                column: "warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_StocktakingDetail_company_id",
                table: "StocktakingDetail",
                column: "company_id");

            migrationBuilder.CreateIndex(
                name: "IX_StocktakingDetail_goods_id",
                table: "StocktakingDetail",
                column: "goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_StocktakingDetail_stocktaking_id",
                table: "StocktakingDetail",
                column: "stocktaking_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StocktakingDetail");

            migrationBuilder.DropTable(
                name: "Stocktaking");
        }
    }
}
