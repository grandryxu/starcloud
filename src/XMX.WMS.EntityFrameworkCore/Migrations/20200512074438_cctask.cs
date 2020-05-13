using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class cctask : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "AGVTask",
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
                    agv_id = table.Column<string>(nullable: true),
                    agv_no = table.Column<int>(nullable: false),
                    agv_priority = table.Column<int>(nullable: false),
                    agv_port_id = table.Column<string>(nullable: true),
                    agv_platform_id = table.Column<string>(nullable: true),
                    agv_port_id2 = table.Column<string>(nullable: true),
                    agv_stock_code = table.Column<string>(nullable: true),
                    agv_malfunction = table.Column<string>(nullable: true),
                    agv_execute_flag = table.Column<int>(nullable: false),
                    agv_manual_flag = table.Column<int>(nullable: false),
                    agv_creat_uid = table.Column<string>(nullable: true),
                    agv_creat_datetime = table.Column<DateTime>(nullable: false),
                    agv_modify_uid = table.Column<string>(nullable: true),
                    agv_modify_datetime = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AGVTask", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ExportBillSyncLog",
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
                    expbill_id = table.Column<Guid>(nullable: true),
                    expbill_info = table.Column<string>(nullable: true),
                    expbill_creat_datetime = table.Column<DateTime>(nullable: false),
                    expbill_result = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExportBillSyncLog", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "FlatBankTask",
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
                    flat_id = table.Column<string>(nullable: true),
                    flat_no = table.Column<int>(nullable: false),
                    flat_priority = table.Column<int>(nullable: false),
                    flat_mode = table.Column<int>(nullable: false),
                    flat_platform_id = table.Column<string>(nullable: true),
                    flat_slot_code = table.Column<string>(nullable: true),
                    flat_stock_code = table.Column<string>(nullable: true),
                    flat_execute_flag = table.Column<int>(nullable: false),
                    flat_manual_flag = table.Column<int>(nullable: false),
                    flat_creat_uid = table.Column<string>(nullable: true),
                    flat_creat_datetime = table.Column<DateTime>(nullable: false),
                    flat_modify_uid = table.Column<string>(nullable: true),
                    flat_modify_datetime = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_FlatBankTask", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ImportApplyLog",
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
                    import_id = table.Column<Guid>(nullable: true),
                    import_info = table.Column<string>(nullable: true),
                    import_creat_datetime = table.Column<DateTime>(nullable: false),
                    import_result = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ImportApplyLog", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "PickingTask",
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
                    main_no = table.Column<string>(nullable: true),
                    main_priority = table.Column<int>(nullable: false),
                    main_mode = table.Column<int>(nullable: false),
                    main_stock_code = table.Column<string>(nullable: true),
                    main_malfunction = table.Column<string>(nullable: true),
                    main_execute_flag = table.Column<int>(nullable: false),
                    main_manual_flag = table.Column<int>(nullable: false),
                    main_slot_code = table.Column<Guid>(nullable: false),
                    main_inslot_code = table.Column<Guid>(nullable: true),
                    main_port_id = table.Column<Guid>(nullable: true),
                    main_platform_id = table.Column<Guid>(nullable: true),
                    main_port_id2 = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PickingTask", x => x.Id);
                    table.ForeignKey(
                        name: "FK_PickingTask_SlotInfo_main_inslot_code",
                        column: x => x.main_inslot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PickingTask_PlatFormInfo_main_platform_id",
                        column: x => x.main_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PickingTask_PortInfo_main_port_id",
                        column: x => x.main_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PickingTask_PortInfo_main_port_id2",
                        column: x => x.main_port_id2,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_PickingTask_SlotInfo_main_slot_code",
                        column: x => x.main_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "RGVTask",
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
                    rgv_id = table.Column<string>(nullable: true),
                    rgv_no = table.Column<int>(nullable: false),
                    rgv_priority = table.Column<int>(nullable: false),
                    rgv_port_id = table.Column<string>(nullable: true),
                    rgv_platform_id = table.Column<string>(nullable: true),
                    rgv_port_id2 = table.Column<string>(nullable: true),
                    rgv_stock_code = table.Column<string>(nullable: true),
                    rgv_malfunction = table.Column<string>(nullable: true),
                    rgv_execute_flag = table.Column<int>(nullable: false),
                    rgv_manual_flag = table.Column<int>(nullable: false),
                    rgv_creat_uid = table.Column<string>(nullable: true),
                    rgv_creat_datetime = table.Column<DateTime>(nullable: false),
                    rgv_modify_uid = table.Column<string>(nullable: true),
                    rgv_modify_datetime = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_RGVTask", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_main_inslot_code",
                table: "PickingTask",
                column: "main_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_main_platform_id",
                table: "PickingTask",
                column: "main_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_main_port_id",
                table: "PickingTask",
                column: "main_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_main_port_id2",
                table: "PickingTask",
                column: "main_port_id2");

            migrationBuilder.CreateIndex(
                name: "IX_PickingTask_main_slot_code",
                table: "PickingTask",
                column: "main_slot_code");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "AGVTask");

            migrationBuilder.DropTable(
                name: "ExportBillSyncLog");

            migrationBuilder.DropTable(
                name: "FlatBankTask");

            migrationBuilder.DropTable(
                name: "ImportApplyLog");

            migrationBuilder.DropTable(
                name: "PickingTask");

            migrationBuilder.DropTable(
                name: "RGVTask");
        }
    }
}
