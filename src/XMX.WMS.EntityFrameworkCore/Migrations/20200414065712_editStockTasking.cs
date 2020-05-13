using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editStockTasking : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StocktakingDetail");

            migrationBuilder.DropTable(
                name: "Stocktaking");

            migrationBuilder.CreateTable(
                name: "StockTasking",
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
                    task_code = table.Column<string>(nullable: true),
                    task_type = table.Column<int>(nullable: false),
                    task_bill_bar = table.Column<string>(nullable: true),
                    task_stock_code = table.Column<string>(nullable: true),
                    task_count = table.Column<decimal>(nullable: false),
                    task_state = table.Column<int>(nullable: false),
                    task_remark = table.Column<string>(nullable: true),
                    task_operate_person = table.Column<string>(nullable: true),
                    task_operate_time = table.Column<DateTime>(nullable: false),
                    task_company_id = table.Column<Guid>(nullable: true),
                    task_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StockTasking", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StockTasking_CompanyInfo_task_company_id",
                        column: x => x.task_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StockTasking_WarehouseInfo_task_warehouse_id",
                        column: x => x.task_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "StockTaskingDetail",
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
                    task_state = table.Column<int>(nullable: false),
                    task_dcount = table.Column<decimal>(nullable: false),
                    task_acount = table.Column<decimal>(nullable: false),
                    task_operate_person = table.Column<string>(nullable: true),
                    task_operate_time = table.Column<DateTime>(nullable: false),
                    task_company_id = table.Column<Guid>(nullable: true),
                    stock_tasking_id = table.Column<Guid>(nullable: true),
                    task_goods_id = table.Column<Guid>(nullable: true),
                    task_inventory_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StockTaskingDetail", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StockTaskingDetail_StockTasking_stock_tasking_id",
                        column: x => x.stock_tasking_id,
                        principalTable: "StockTasking",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StockTaskingDetail_CompanyInfo_task_company_id",
                        column: x => x.task_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StockTaskingDetail_GoodsInfo_task_goods_id",
                        column: x => x.task_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_StockTaskingDetail_InventoryInfo_task_inventory_id",
                        column: x => x.task_inventory_id,
                        principalTable: "InventoryInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StockTasking_task_company_id",
                table: "StockTasking",
                column: "task_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTasking_task_warehouse_id",
                table: "StockTasking",
                column: "task_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTaskingDetail_stock_tasking_id",
                table: "StockTaskingDetail",
                column: "stock_tasking_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTaskingDetail_task_company_id",
                table: "StockTaskingDetail",
                column: "task_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTaskingDetail_task_goods_id",
                table: "StockTaskingDetail",
                column: "task_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTaskingDetail_task_inventory_id",
                table: "StockTaskingDetail",
                column: "task_inventory_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StockTaskingDetail");

            migrationBuilder.DropTable(
                name: "StockTasking");

            migrationBuilder.CreateTable(
                name: "Stocktaking",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    CreatorUserId = table.Column<long>(nullable: true),
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    bill_bar = table.Column<string>(nullable: true),
                    company_id = table.Column<Guid>(nullable: true),
                    count = table.Column<decimal>(nullable: false),
                    operate_person = table.Column<string>(nullable: true),
                    operate_time = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(nullable: true),
                    state = table.Column<int>(nullable: false),
                    stock_code = table.Column<string>(nullable: true),
                    stocktaking_code = table.Column<string>(nullable: true),
                    type = table.Column<int>(nullable: false),
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
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    acount = table.Column<decimal>(nullable: false),
                    company_id = table.Column<Guid>(nullable: true),
                    dcount = table.Column<decimal>(nullable: false),
                    goods_id = table.Column<Guid>(nullable: true),
                    inventory_id = table.Column<Guid>(nullable: true),
                    operate_person = table.Column<string>(nullable: true),
                    operate_time = table.Column<DateTime>(nullable: false),
                    state = table.Column<int>(nullable: false),
                    stocktaking_id = table.Column<Guid>(nullable: true)
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
    }
}
