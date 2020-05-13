using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class inventoryInfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "InventoryInfo",
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
                    inventory_batch_no = table.Column<string>(nullable: true),
                    inventory_lots_no = table.Column<string>(nullable: true),
                    inventory_product_date = table.Column<DateTime>(nullable: false),
                    inventory_product_lineid = table.Column<string>(nullable: true),
                    inventory_remark = table.Column<string>(nullable: true),
                    inventory_bill_bar = table.Column<string>(nullable: true),
                    inventory_vaildate_date = table.Column<DateTime>(nullable: false),
                    inventory_recheck_date = table.Column<DateTime>(nullable: false),
                    inventory_quantity = table.Column<decimal>(nullable: false),
                    inventory_box_code = table.Column<string>(nullable: true),
                    inventory_stock_code = table.Column<string>(nullable: true),
                    inventory_slot_code = table.Column<string>(nullable: true),
                    inventory_status = table.Column<int>(nullable: false),
                    inventory_stock_status = table.Column<int>(nullable: false),
                    inventory_date = table.Column<DateTime>(nullable: false),
                    inventory_datetime = table.Column<DateTime>(nullable: false),
                    inventory_creat_uid = table.Column<string>(nullable: true),
                    inventory_creat_datetime = table.Column<DateTime>(nullable: false),
                    inventory_modify_uid = table.Column<string>(nullable: true),
                    inventory_modify_datetime = table.Column<DateTime>(nullable: false),
                    inventory_is_enable = table.Column<int>(nullable: false),
                    inventory_is_delete = table.Column<int>(nullable: false),
                    inventory_company_id = table.Column<Guid>(nullable: true),
                    inventory_goods_id = table.Column<Guid>(nullable: true),
                    inventory_quality_status = table.Column<Guid>(nullable: true),
                    inventory_warehouse_id = table.Column<Guid>(nullable: true),
                    inventory_port_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_InventoryInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                        column: x => x.inventory_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                        column: x => x.inventory_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_InventoryInfo_PortInfo_inventory_port_id",
                        column: x => x.inventory_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                        column: x => x.inventory_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
                        column: x => x.inventory_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_goods_id",
                table: "InventoryInfo",
                column: "inventory_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_port_id",
                table: "InventoryInfo",
                column: "inventory_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_quality_status",
                table: "InventoryInfo",
                column: "inventory_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_warehouse_id",
                table: "InventoryInfo",
                column: "inventory_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "InventoryInfo");
        }
    }
}
