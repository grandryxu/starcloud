using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class fourtable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "HistoryTaskMainInfo",
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
                    main_no = table.Column<int>(nullable: false),
                    main_priority = table.Column<int>(nullable: false),
                    main_mode = table.Column<int>(nullable: false),
                    main_creat_uid = table.Column<string>(nullable: true),
                    main_creat_datetime = table.Column<DateTime>(nullable: false),
                    main_modify_uid = table.Column<string>(nullable: true),
                    main_modify_datetime = table.Column<DateTime>(nullable: false),
                    main_slot_code = table.Column<string>(nullable: true),
                    main_inslot_code = table.Column<string>(nullable: true),
                    main_stock_code = table.Column<string>(nullable: true),
                    main_malfunction = table.Column<string>(nullable: true),
                    main_execute_flag = table.Column<int>(nullable: false),
                    main_manual_flag = table.Column<int>(nullable: false),
                    main_is_delete = table.Column<int>(nullable: false),
                    main_port_id = table.Column<Guid>(nullable: true),
                    main_platform_id = table.Column<Guid>(nullable: true),
                    main_port_id2 = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_HistoryTaskMainInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_HistoryTaskMainInfo_PlatFormInfo_main_platform_id",
                        column: x => x.main_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id",
                        column: x => x.main_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id2",
                        column: x => x.main_port_id2,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "TaskMainInfo",
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
                    main_no = table.Column<int>(nullable: false),
                    main_priority = table.Column<int>(nullable: false),
                    main_mode = table.Column<int>(nullable: false),
                    main_creat_uid = table.Column<string>(nullable: true),
                    main_creat_datetime = table.Column<DateTime>(nullable: false),
                    main_modify_uid = table.Column<string>(nullable: true),
                    main_modify_datetime = table.Column<DateTime>(nullable: false),
                    main_slot_code = table.Column<string>(nullable: true),
                    main_inslot_code = table.Column<string>(nullable: true),
                    main_stock_code = table.Column<string>(nullable: true),
                    main_malfunction = table.Column<string>(nullable: true),
                    main_execute_flag = table.Column<int>(nullable: false),
                    main_manual_flag = table.Column<int>(nullable: false),
                    main_is_delete = table.Column<int>(nullable: false),
                    main_port_id = table.Column<Guid>(nullable: true),
                    main_platform_id = table.Column<Guid>(nullable: true),
                    main_port_id2 = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TaskMainInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TaskMainInfo_PlatFormInfo_main_platform_id",
                        column: x => x.main_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_TaskMainInfo_PortInfo_main_port_id",
                        column: x => x.main_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_TaskMainInfo_PortInfo_main_port_id2",
                        column: x => x.main_port_id2,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "ExportOrder",
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
                    exporder_batch_no = table.Column<string>(nullable: true),
                    exporder_lots_no = table.Column<string>(nullable: true),
                    exporder_product_date = table.Column<DateTime>(nullable: false),
                    exporder_product_lineid = table.Column<string>(nullable: true),
                    exporder_remark = table.Column<string>(nullable: true),
                    exporder_bill_bar = table.Column<string>(nullable: true),
                    exporder_vaildate_date = table.Column<DateTime>(nullable: false),
                    exporder_recheck_date = table.Column<DateTime>(nullable: false),
                    exporder_quantity = table.Column<decimal>(nullable: false),
                    exporder_box_code = table.Column<string>(nullable: true),
                    exporder_stock_code = table.Column<string>(nullable: true),
                    exporder_execute_flag = table.Column<int>(nullable: false),
                    exporder_noused_flag = table.Column<int>(nullable: false),
                    exporder_noused_uid = table.Column<string>(nullable: true),
                    exporder_noused_datetime = table.Column<DateTime>(nullable: false),
                    exporder_upload_flag = table.Column<int>(nullable: false),
                    exporder_upload_datetime = table.Column<DateTime>(nullable: false),
                    exporder_creat_uid = table.Column<string>(nullable: true),
                    exporder_creat_datetime = table.Column<DateTime>(nullable: false),
                    exporder_modify_uid = table.Column<string>(nullable: true),
                    exporder_modify_datetime = table.Column<DateTime>(nullable: false),
                    exporder_is_enable = table.Column<int>(nullable: false),
                    exporder_is_delete = table.Column<int>(nullable: false),
                    task_id = table.Column<Guid>(nullable: true),
                    exporder_company_id = table.Column<Guid>(nullable: true),
                    exporder_goods_id = table.Column<Guid>(nullable: true),
                    exporder_quality_status = table.Column<Guid>(nullable: true),
                    exporder_slot_code = table.Column<Guid>(nullable: true),
                    exporder_warehouse_id = table.Column<Guid>(nullable: true),
                    exporder_port_id = table.Column<Guid>(nullable: true),
                    exporder_platform_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportOrder", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ExportOrder_CompanyInfo_exporder_company_id",
                        column: x => x.exporder_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_GoodsInfo_exporder_goods_id",
                        column: x => x.exporder_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_PlatFormInfo_exporder_platform_id",
                        column: x => x.exporder_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_PortInfo_exporder_port_id",
                        column: x => x.exporder_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_QualityInfo_exporder_quality_status",
                        column: x => x.exporder_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_SlotInfo_exporder_slot_code",
                        column: x => x.exporder_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_WarehouseInfo_exporder_warehouse_id",
                        column: x => x.exporder_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ExportOrder_TaskMainInfo_task_id",
                        column: x => x.task_id,
                        principalTable: "TaskMainInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "ImportOrder",
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
                    imporder_batch_no = table.Column<string>(nullable: true),
                    imporder_lots_no = table.Column<string>(nullable: true),
                    imporder_product_date = table.Column<DateTime>(nullable: false),
                    imporder_product_lineid = table.Column<string>(nullable: true),
                    imporder_remark = table.Column<string>(nullable: true),
                    imporder_bill_bar = table.Column<string>(nullable: true),
                    imporder_vaildate_date = table.Column<DateTime>(nullable: false),
                    imporder_recheck_date = table.Column<DateTime>(nullable: false),
                    imporder_stock_status = table.Column<int>(nullable: false),
                    imporder_quantity = table.Column<int>(nullable: false),
                    imporder_box_code = table.Column<string>(nullable: true),
                    imporder_stock_code = table.Column<string>(nullable: true),
                    imporder_execute_flag = table.Column<int>(nullable: false),
                    imporder_noused_flag = table.Column<int>(nullable: false),
                    imporder_noused_uid = table.Column<string>(nullable: true),
                    imporder_noused_datetime = table.Column<DateTime>(nullable: false),
                    imporder_upload_flag = table.Column<string>(nullable: true),
                    imporder_upload_datetime = table.Column<DateTime>(nullable: false),
                    imporder_creat_uid = table.Column<string>(nullable: true),
                    imporder_creat_datetime = table.Column<DateTime>(nullable: false),
                    imporder_modify_uid = table.Column<string>(nullable: true),
                    imporder_modify_datetime = table.Column<DateTime>(nullable: false),
                    imporder_is_enable = table.Column<int>(nullable: false),
                    imporder_is_delete = table.Column<int>(nullable: false),
                    task_id = table.Column<Guid>(nullable: true),
                    imporder_company_id = table.Column<Guid>(nullable: true),
                    imporder_imporder_id = table.Column<Guid>(nullable: true),
                    imporder_quality_status = table.Column<Guid>(nullable: true),
                    imporder_slot_code = table.Column<Guid>(nullable: true),
                    imporder_warehouse_id = table.Column<Guid>(nullable: true),
                    imporder_port_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ImportOrder", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ImportOrder_CompanyInfo_imporder_company_id",
                        column: x => x.imporder_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_GoodsInfo_imporder_imporder_id",
                        column: x => x.imporder_imporder_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_PortInfo_imporder_port_id",
                        column: x => x.imporder_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_QualityInfo_imporder_quality_status",
                        column: x => x.imporder_quality_status,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_SlotInfo_imporder_slot_code",
                        column: x => x.imporder_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_WarehouseInfo_imporder_warehouse_id",
                        column: x => x.imporder_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_ImportOrder_TaskMainInfo_task_id",
                        column: x => x.task_id,
                        principalTable: "TaskMainInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_company_id",
                table: "ExportOrder",
                column: "exporder_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_goods_id",
                table: "ExportOrder",
                column: "exporder_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_platform_id",
                table: "ExportOrder",
                column: "exporder_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_port_id",
                table: "ExportOrder",
                column: "exporder_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_quality_status",
                table: "ExportOrder",
                column: "exporder_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_slot_code",
                table: "ExportOrder",
                column: "exporder_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_warehouse_id",
                table: "ExportOrder",
                column: "exporder_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_task_id",
                table: "ExportOrder",
                column: "task_id");

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_platform_id",
                table: "HistoryTaskMainInfo",
                column: "main_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_port_id",
                table: "HistoryTaskMainInfo",
                column: "main_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_port_id2",
                table: "HistoryTaskMainInfo",
                column: "main_port_id2");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_company_id",
                table: "ImportOrder",
                column: "imporder_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_imporder_id",
                table: "ImportOrder",
                column: "imporder_imporder_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_port_id",
                table: "ImportOrder",
                column: "imporder_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_quality_status",
                table: "ImportOrder",
                column: "imporder_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_slot_code",
                table: "ImportOrder",
                column: "imporder_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_imporder_warehouse_id",
                table: "ImportOrder",
                column: "imporder_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_ImportOrder_task_id",
                table: "ImportOrder",
                column: "task_id");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_platform_id",
                table: "TaskMainInfo",
                column: "main_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_port_id",
                table: "TaskMainInfo",
                column: "main_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_port_id2",
                table: "TaskMainInfo",
                column: "main_port_id2");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExportOrder");

            migrationBuilder.DropTable(
                name: "HistoryTaskMainInfo");

            migrationBuilder.DropTable(
                name: "ImportOrder");

            migrationBuilder.DropTable(
                name: "TaskMainInfo");
        }
    }
}
