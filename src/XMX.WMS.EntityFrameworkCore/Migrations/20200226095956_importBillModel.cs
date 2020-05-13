using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class importBillModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ImportBillhead",
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
                    imphead_code = table.Column<string>(nullable: true),
                    imphead_external_id = table.Column<string>(nullable: true),
                    imphead_external_code = table.Column<string>(nullable: true),
                    imphead_date = table.Column<DateTime>(nullable: false),
                    imphead_execute_flag = table.Column<int>(nullable: false),
                    imphead_noused_flag = table.Column<int>(nullable: false),
                    imphead_audit_flag = table.Column<int>(nullable: false),
                    imphead_upload_flag = table.Column<int>(nullable: false),
                    imphead_creat_uid = table.Column<string>(nullable: true),
                    imphead_creat_datetime = table.Column<DateTime>(nullable: false),
                    imphead_modify_uid = table.Column<string>(nullable: true),
                    imphead_modify_datetime = table.Column<DateTime>(nullable: false),
                    imphead_is_enable = table.Column<int>(nullable: false),
                    imphead_is_delete = table.Column<int>(nullable: false),
                    imphead_company_id = table.Column<Guid>(nullable: true),
                    imphead_bill_id = table.Column<Guid>(nullable: true),
                    imphead_warehouse_id = table.Column<Guid>(nullable: true),
                    imphead_custom_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ImportBillhead", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ImportBillhead_BillInfo_imphead_bill_id",
                        column: x => x.imphead_bill_id,
                        principalTable: "BillInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillhead_CompanyInfo_imphead_company_id",
                        column: x => x.imphead_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillhead_CustomInfo_imphead_custom_id",
                        column: x => x.imphead_custom_id,
                        principalTable: "CustomInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillhead_WarehouseInfo_imphead_warehouse_id",
                        column: x => x.imphead_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "ImportBillbody",
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
                    impbody_list_id = table.Column<string>(nullable: true),
                    impbody_external_listid = table.Column<string>(nullable: true),
                    impbody_batch_no = table.Column<string>(nullable: true),
                    impbody_lots_no = table.Column<string>(nullable: true),
                    impbody_product_date = table.Column<DateTime>(nullable: false),
                    impbody_product_lineid = table.Column<string>(nullable: true),
                    impbody_remark = table.Column<string>(nullable: true),
                    impbody_bill_bar = table.Column<string>(nullable: true),
                    impbody_vaildate_date = table.Column<DateTime>(nullable: false),
                    imbody_recheck_date = table.Column<DateTime>(nullable: false),
                    impbody_plan_quantity = table.Column<int>(nullable: false),
                    impbody_binding_quantity = table.Column<int>(nullable: false),
                    impbody_fulfill_quantity = table.Column<int>(nullable: false),
                    impbody_execute_flag = table.Column<int>(nullable: false),
                    impbody_audit_flag = table.Column<int>(nullable: false),
                    impbody_audit_uid = table.Column<string>(nullable: true),
                    impbody_audit_datetime = table.Column<DateTime>(nullable: false),
                    impbody_noused_flag = table.Column<int>(nullable: false),
                    impbody_noused_uid = table.Column<string>(nullable: true),
                    impbody_noused_datetime = table.Column<string>(nullable: true),
                    impbody_upload_flag = table.Column<int>(nullable: false),
                    impbody_upload_datetime = table.Column<DateTime>(nullable: false),
                    impbody_creat_uid = table.Column<string>(nullable: true),
                    impbody_creat_datetime = table.Column<DateTime>(nullable: false),
                    impbody_modify_uid = table.Column<string>(nullable: true),
                    impbody_modify_datetime = table.Column<DateTime>(nullable: false),
                    impbody_is_enable = table.Column<int>(nullable: false),
                    impbody_is_delete = table.Column<int>(nullable: false),
                    impbody_company_id = table.Column<Guid>(nullable: true),
                    impbody_imphead_id = table.Column<Guid>(nullable: true),
                    impbody_goods_id = table.Column<Guid>(nullable: true),
                    impbody_upload_quantity = table.Column<Guid>(nullable: true),
                    impbody_quality_status = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ImportBillbody", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ImportBillbody_CompanyInfo_impbody_company_id",
                        column: x => x.impbody_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillbody_GoodsInfo_impbody_goods_id",
                        column: x => x.impbody_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillbody_ImportBillhead_impbody_imphead_id",
                        column: x => x.impbody_imphead_id,
                        principalTable: "ImportBillhead",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillbody_QualityInfo_impbody_quality_status",
                        column: x => x.impbody_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportBillbody_QualityInfo_impbody_upload_quantity",
                        column: x => x.impbody_upload_quantity,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_company_id",
                table: "ImportBillbody",
                column: "impbody_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_goods_id",
                table: "ImportBillbody",
                column: "impbody_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_imphead_id",
                table: "ImportBillbody",
                column: "impbody_imphead_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_quality_status",
                table: "ImportBillbody",
                column: "impbody_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillbody_impbody_upload_quantity",
                table: "ImportBillbody",
                column: "impbody_upload_quantity");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillhead_imphead_bill_id",
                table: "ImportBillhead",
                column: "imphead_bill_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillhead_imphead_company_id",
                table: "ImportBillhead",
                column: "imphead_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillhead_imphead_custom_id",
                table: "ImportBillhead",
                column: "imphead_custom_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportBillhead_imphead_warehouse_id",
                table: "ImportBillhead",
                column: "imphead_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ImportBillbody");

            migrationBuilder.DropTable(
                name: "ImportBillhead");
        }
    }
}
