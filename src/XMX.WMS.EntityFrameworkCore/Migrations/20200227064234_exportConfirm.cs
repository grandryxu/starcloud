using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class exportConfirm : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ExportConfirm",
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
                    confirm_batch_no = table.Column<string>(nullable: true),
                    confirm_lots_no = table.Column<string>(nullable: true),
                    confirm_product_date = table.Column<DateTime>(nullable: false),
                    confirm_product_lineid = table.Column<string>(nullable: true),
                    confirm_remark = table.Column<string>(nullable: true),
                    confirm_bill_bar = table.Column<string>(nullable: true),
                    confirm_vaildate_date = table.Column<DateTime>(nullable: false),
                    confirm_recheck_date = table.Column<DateTime>(nullable: false),
                    confirm_quantity = table.Column<decimal>(nullable: false),
                    confirm_box_code = table.Column<string>(nullable: true),
                    confirm_stock_code = table.Column<string>(nullable: true),
                    confirm_upload_flag = table.Column<string>(nullable: true),
                    confirm_upload_datetime = table.Column<DateTime>(nullable: false),
                    confirm_creat_uid = table.Column<string>(nullable: true),
                    confirm_creat_datetime = table.Column<DateTime>(nullable: false),
                    confirm_modify_uid = table.Column<string>(nullable: true),
                    confirm_modify_datetime = table.Column<DateTime>(nullable: false),
                    confirm_is_enable = table.Column<int>(nullable: false),
                    confirm_is_delete = table.Column<int>(nullable: false),
                    confirm_company_id = table.Column<Guid>(nullable: true),
                    confirm_goods_id = table.Column<Guid>(nullable: true),
                    confirm_quality_status = table.Column<Guid>(nullable: true),
                    confirm_slot_code = table.Column<Guid>(nullable: true),
                    confirm_warehouse_id = table.Column<Guid>(nullable: true),
                    confirm_port_id = table.Column<Guid>(nullable: true),
                    confirm_platform_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportConfirm", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_CompanyInfo_confirm_company_id",
                        column: x => x.confirm_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_GoodsInfo_confirm_goods_id",
                        column: x => x.confirm_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_PlatFormInfo_confirm_platform_id",
                        column: x => x.confirm_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_PortInfo_confirm_port_id",
                        column: x => x.confirm_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_QualityInfo_confirm_quality_status",
                        column: x => x.confirm_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_SlotInfo_confirm_slot_code",
                        column: x => x.confirm_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportConfirm_WarehouseInfo_confirm_warehouse_id",
                        column: x => x.confirm_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_company_id",
                table: "ExportConfirm",
                column: "confirm_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_goods_id",
                table: "ExportConfirm",
                column: "confirm_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_platform_id",
                table: "ExportConfirm",
                column: "confirm_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_port_id",
                table: "ExportConfirm",
                column: "confirm_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_quality_status",
                table: "ExportConfirm",
                column: "confirm_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_slot_code",
                table: "ExportConfirm",
                column: "confirm_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_ExportConfirm_confirm_warehouse_id",
                table: "ExportConfirm",
                column: "confirm_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExportConfirm");
        }
    }
}
