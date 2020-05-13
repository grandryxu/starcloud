using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class importStock : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ImportStock",
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
                    impstock_batch_no = table.Column<string>(nullable: true),
                    impstock_remark = table.Column<string>(nullable: true),
                    impstock_quantity = table.Column<int>(nullable: false),
                    impstock_stock_code = table.Column<string>(nullable: true),
                    impstock_execute_flag = table.Column<int>(nullable: false),
                    impstock_noused_flag = table.Column<int>(nullable: false),
                    impstock_noused_uid = table.Column<string>(nullable: true),
                    impstock_noused_datetime = table.Column<DateTime>(nullable: false),
                    impstock_creat_uid = table.Column<string>(nullable: true),
                    impstock_creat_datetime = table.Column<DateTime>(nullable: false),
                    impstock_modify_uid = table.Column<string>(nullable: true),
                    impstock_modify_datetime = table.Column<DateTime>(nullable: false),
                    impstock_is_enable = table.Column<int>(nullable: false),
                    impstock_is_delete = table.Column<int>(nullable: false),
                    impstock_company_id = table.Column<Guid>(nullable: true),
                    impstock_goods_id = table.Column<Guid>(nullable: true),
                    impstock_slot_code = table.Column<Guid>(nullable: true),
                    impstock_warehouse_id = table.Column<Guid>(nullable: true),
                    impstock_port_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ImportStock", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ImportStock_CompanyInfo_impstock_company_id",
                        column: x => x.impstock_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportStock_GoodsInfo_impstock_goods_id",
                        column: x => x.impstock_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportStock_PortInfo_impstock_port_id",
                        column: x => x.impstock_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportStock_SlotInfo_impstock_slot_code",
                        column: x => x.impstock_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportStock_WarehouseInfo_impstock_warehouse_id",
                        column: x => x.impstock_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_company_id",
                table: "ImportStock",
                column: "impstock_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_goods_id",
                table: "ImportStock",
                column: "impstock_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_port_id",
                table: "ImportStock",
                column: "impstock_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_slot_code",
                table: "ImportStock",
                column: "impstock_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_warehouse_id",
                table: "ImportStock",
                column: "impstock_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ImportStock");
        }
    }
}
