using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class exportStock : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ExportStock",
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
                    expstock_batch_no = table.Column<string>(nullable: true),
                    expstock_remark = table.Column<string>(nullable: true),
                    expstock_quantity = table.Column<decimal>(nullable: false),
                    expstock_stock_code = table.Column<string>(nullable: true),
                    expstock_execute_flag = table.Column<int>(nullable: false),
                    expstock_noused_flag = table.Column<int>(nullable: false),
                    expstock_noused_uid = table.Column<string>(nullable: true),
                    expstock_noused_datetime = table.Column<DateTime>(nullable: false),
                    expstock_creat_uid = table.Column<string>(nullable: true),
                    expstock_creat_datetime = table.Column<DateTime>(nullable: false),
                    expstock_modify_uid = table.Column<string>(nullable: true),
                    expstock_modify_datetime = table.Column<DateTime>(nullable: false),
                    expstock_is_enable = table.Column<int>(nullable: false),
                    expstock_is_delete = table.Column<int>(nullable: false),
                    expstock_company_id = table.Column<Guid>(nullable: true),
                    expstock_goods_id = table.Column<Guid>(nullable: true),
                    expstock_slot_code = table.Column<Guid>(nullable: true),
                    expstock_warehouse_id = table.Column<Guid>(nullable: true),
                    expstock_port_id = table.Column<Guid>(nullable: true),
                    expstock_platform_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportStock", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ExportStock_CompanyInfo_expstock_company_id",
                        column: x => x.expstock_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportStock_GoodsInfo_expstock_goods_id",
                        column: x => x.expstock_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportStock_PlatFormInfo_expstock_platform_id",
                        column: x => x.expstock_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportStock_PortInfo_expstock_port_id",
                        column: x => x.expstock_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportStock_SlotInfo_expstock_slot_code",
                        column: x => x.expstock_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportStock_WarehouseInfo_expstock_warehouse_id",
                        column: x => x.expstock_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_company_id",
                table: "ExportStock",
                column: "expstock_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_goods_id",
                table: "ExportStock",
                column: "expstock_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_platform_id",
                table: "ExportStock",
                column: "expstock_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_port_id",
                table: "ExportStock",
                column: "expstock_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_slot_code",
                table: "ExportStock",
                column: "expstock_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_ExportStock_expstock_warehouse_id",
                table: "ExportStock",
                column: "expstock_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExportStock");
        }
    }
}
