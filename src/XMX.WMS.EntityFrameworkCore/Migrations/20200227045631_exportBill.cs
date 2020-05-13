using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class exportBill : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ExportBillhead",
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
                    exphead_code = table.Column<string>(nullable: true),
                    exphead_external_id = table.Column<string>(nullable: true),
                    exphead_external_code = table.Column<string>(nullable: true),
                    exphead_date = table.Column<DateTime>(nullable: false),
                    exphead_execute_flag = table.Column<int>(nullable: false),
                    exphead_noused_flag = table.Column<int>(nullable: false),
                    exphead_audit_flag = table.Column<int>(nullable: false),
                    exphead_upload_flag = table.Column<int>(nullable: false),
                    exphead_creat_uid = table.Column<string>(nullable: true),
                    exphead_creat_datetime = table.Column<DateTime>(nullable: false),
                    exphead_modify_uid = table.Column<string>(nullable: true),
                    exphead_modify_datetime = table.Column<DateTime>(nullable: false),
                    exphead_is_enable = table.Column<int>(nullable: false),
                    exphead_is_delete = table.Column<int>(nullable: false),
                    exphead_company_id = table.Column<Guid>(nullable: true),
                    exphead_bill_id = table.Column<Guid>(nullable: true),
                    exphead_warehouse_id = table.Column<Guid>(nullable: true),
                    exphead_custom_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportBillhead", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ExportBillhead_BillInfo_exphead_bill_id",
                        column: x => x.exphead_bill_id,
                        principalTable: "BillInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillhead_CompanyInfo_exphead_company_id",
                        column: x => x.exphead_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillhead_CustomInfo_exphead_custom_id",
                        column: x => x.exphead_custom_id,
                        principalTable: "CustomInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillhead_WarehouseInfo_exphead_warehouse_id",
                        column: x => x.exphead_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "ExportBillbody",
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
                    expbody_list_id = table.Column<string>(nullable: true),
                    expbody_external_listid = table.Column<string>(nullable: true),
                    expbody_batch_no = table.Column<string>(nullable: true),
                    expbody_lots_no = table.Column<string>(nullable: true),
                    expbody_product_date = table.Column<DateTime>(nullable: false),
                    expbody_product_lineid = table.Column<string>(nullable: true),
                    expbody_remark = table.Column<string>(nullable: true),
                    expbody_bill_bar = table.Column<string>(nullable: true),
                    expbody_vaildate_date = table.Column<DateTime>(nullable: false),
                    expbody_recheck_date = table.Column<DateTime>(nullable: false),
                    expbody_plan_quantity = table.Column<decimal>(nullable: false),
                    expbody_binding_quantity = table.Column<decimal>(nullable: false),
                    expbody_fulfill_quantity = table.Column<decimal>(nullable: false),
                    expbody_execute_flag = table.Column<int>(nullable: false),
                    expbody_audit_flag = table.Column<int>(nullable: false),
                    expbody_audit_uid = table.Column<string>(nullable: true),
                    expbody_audit_datetime = table.Column<DateTime>(nullable: false),
                    expbody_noused_flag = table.Column<int>(nullable: false),
                    expbody_noused_uid = table.Column<string>(nullable: true),
                    expbody_noused_datetime = table.Column<DateTime>(nullable: false),
                    expbody_upload_flag = table.Column<int>(nullable: false),
                    expbody_upload_datetime = table.Column<DateTime>(nullable: false),
                    expbody_creat_uid = table.Column<string>(nullable: true),
                    expbody_creat_datetime = table.Column<DateTime>(nullable: false),
                    expbody_modify_uid = table.Column<string>(nullable: true),
                    expbody_modify_datetime = table.Column<DateTime>(nullable: false),
                    expbody_is_enable = table.Column<int>(nullable: false),
                    expbody_is_delete = table.Column<int>(nullable: false),
                    expbody_company_id = table.Column<Guid>(nullable: true),
                    expbody_imphead_id = table.Column<Guid>(nullable: true),
                    expbody_goods_id = table.Column<Guid>(nullable: true),
                    expbody_quality_status = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportBillbody", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ExportBillbody_CompanyInfo_expbody_company_id",
                        column: x => x.expbody_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillbody_GoodsInfo_expbody_goods_id",
                        column: x => x.expbody_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                        column: x => x.expbody_imphead_id,
                        principalTable: "ExportBillhead",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportBillbody_QualityInfo_expbody_quality_status",
                        column: x => x.expbody_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillbody_expbody_company_id",
                table: "ExportBillbody",
                column: "expbody_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillbody_expbody_goods_id",
                table: "ExportBillbody",
                column: "expbody_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillbody_expbody_imphead_id",
                table: "ExportBillbody",
                column: "expbody_imphead_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillbody_expbody_quality_status",
                table: "ExportBillbody",
                column: "expbody_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillhead_exphead_bill_id",
                table: "ExportBillhead",
                column: "exphead_bill_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillhead_exphead_company_id",
                table: "ExportBillhead",
                column: "exphead_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillhead_exphead_custom_id",
                table: "ExportBillhead",
                column: "exphead_custom_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillhead_exphead_warehouse_id",
                table: "ExportBillhead",
                column: "exphead_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExportBillbody");

            migrationBuilder.DropTable(
                name: "ExportBillhead");
        }
    }
}
