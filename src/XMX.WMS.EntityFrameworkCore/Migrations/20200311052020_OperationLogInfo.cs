using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class OperationLogInfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            //migrationBuilder.AddColumn<Guid>(
            //    name: "history_task_id",
            //    table: "ImportOrder",
            //    nullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "history_task_id",
            //    table: "ExportOrder",
            //    nullable: true);

            migrationBuilder.CreateTable(
                name: "OperationLogInfo",
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
                    operation_modify_pre_data = table.Column<string>(nullable: true),
                    operation_modify_final_data = table.Column<string>(nullable: true),
                    operation_search_content = table.Column<string>(nullable: true),
                    operation_remark = table.Column<string>(nullable: true),
                    operation_type_name = table.Column<string>(nullable: true),
                    operation_module_name = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_OperationLogInfo", x => x.Id);
                });

            //migrationBuilder.CreateIndex(
            //    name: "IX_ImportOrder_history_task_id",
            //    table: "ImportOrder",
            //    column: "history_task_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_ExportOrder_history_task_id",
            //    table: "ExportOrder",
            //    column: "history_task_id");

            //migrationBuilder.AddForeignKey(
            //    name: "FK_ExportOrder_HistoryTaskMainInfo_history_task_id",
            //    table: "ExportOrder",
            //    column: "history_task_id",
            //    principalTable: "HistoryTaskMainInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PlatFormInfo_main_platform_id",
            //    table: "HistoryTaskMainInfo",
            //    column: "main_platform_id",
            //    principalTable: "PlatFormInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id",
            //    table: "HistoryTaskMainInfo",
            //    column: "main_port_id",
            //    principalTable: "PortInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id2",
            //    table: "HistoryTaskMainInfo",
            //    column: "main_port_id2",
            //    principalTable: "PortInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_ImportOrder_HistoryTaskMainInfo_history_task_id",
            //    table: "ImportOrder",
            //    column: "history_task_id",
            //    principalTable: "HistoryTaskMainInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            //migrationBuilder.DropForeignKey(
            //    name: "FK_ExportOrder_HistoryTaskMainInfo_history_task_id",
            //    table: "ExportOrder");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PlatFormInfo_main_platform_id",
            //    table: "HistoryTaskMainInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id",
            //    table: "HistoryTaskMainInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_HistoryTaskMainInfo_PortInfo_main_port_id2",
            //    table: "HistoryTaskMainInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_ImportOrder_HistoryTaskMainInfo_history_task_id",
            //    table: "ImportOrder");

            migrationBuilder.DropTable(
                name: "OperationLogInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_ImportOrder_history_task_id",
            //    table: "ImportOrder");

            //migrationBuilder.DropIndex(
            //    name: "IX_ExportOrder_history_task_id",
            //    table: "ExportOrder");

            //migrationBuilder.DropColumn(
            //    name: "history_task_id",
            //    table: "ImportOrder");

            //migrationBuilder.DropColumn(
            //    name: "history_task_id",
            //    table: "ExportOrder");
        }
    }
}
