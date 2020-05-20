using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class ss : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_main_inslot_code",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PlatFormInfo_main_platform_id",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PortInfo_main_port_id",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PortInfo_main_port_id2",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_main_slot_code",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "main_execute_flag",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "main_malfunction",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "main_manual_flag",
                table: "PickingTask");

            migrationBuilder.RenameColumn(
                name: "main_stock_code",
                table: "PickingTask",
                newName: "picking_stock_code");

            migrationBuilder.RenameColumn(
                name: "main_slot_code",
                table: "PickingTask",
                newName: "picking_slot_code");

            migrationBuilder.RenameColumn(
                name: "main_priority",
                table: "PickingTask",
                newName: "picking_priority");

            migrationBuilder.RenameColumn(
                name: "main_port_id2",
                table: "PickingTask",
                newName: "picking_port_id2");

            migrationBuilder.RenameColumn(
                name: "main_port_id",
                table: "PickingTask",
                newName: "picking_port_id");

            migrationBuilder.RenameColumn(
                name: "main_platform_id",
                table: "PickingTask",
                newName: "picking_platform_id");

            migrationBuilder.RenameColumn(
                name: "main_no",
                table: "PickingTask",
                newName: "picking_malfunction");

            migrationBuilder.RenameColumn(
                name: "main_mode",
                table: "PickingTask",
                newName: "picking_no");

            migrationBuilder.RenameColumn(
                name: "main_inslot_code",
                table: "PickingTask",
                newName: "picking_inslot_code");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_main_slot_code",
                table: "PickingTask",
                newName: "IX_PickingTask_picking_slot_code");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_main_port_id2",
                table: "PickingTask",
                newName: "IX_PickingTask_picking_port_id2");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_main_port_id",
                table: "PickingTask",
                newName: "IX_PickingTask_picking_port_id");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_main_platform_id",
                table: "PickingTask",
                newName: "IX_PickingTask_picking_platform_id");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_main_inslot_code",
                table: "PickingTask",
                newName: "IX_PickingTask_picking_inslot_code");

            migrationBuilder.AddColumn<int>(
                name: "picking_execute_flag",
                table: "PickingTask",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "picking_manual_flag",
                table: "PickingTask",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "picking_mode",
                table: "PickingTask",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_inslot_code",
                table: "PickingTask",
                column: "picking_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PlatFormInfo_picking_platform_id",
                table: "PickingTask",
                column: "picking_platform_id",
                principalTable: "PlatFormInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PortInfo_picking_port_id",
                table: "PickingTask",
                column: "picking_port_id",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PortInfo_picking_port_id2",
                table: "PickingTask",
                column: "picking_port_id2",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask",
                column: "picking_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_inslot_code",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PlatFormInfo_picking_platform_id",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PortInfo_picking_port_id",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_PortInfo_picking_port_id2",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "picking_execute_flag",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "picking_manual_flag",
                table: "PickingTask");

            migrationBuilder.DropColumn(
                name: "picking_mode",
                table: "PickingTask");

            migrationBuilder.RenameColumn(
                name: "picking_stock_code",
                table: "PickingTask",
                newName: "main_stock_code");

            migrationBuilder.RenameColumn(
                name: "picking_slot_code",
                table: "PickingTask",
                newName: "main_slot_code");

            migrationBuilder.RenameColumn(
                name: "picking_priority",
                table: "PickingTask",
                newName: "main_priority");

            migrationBuilder.RenameColumn(
                name: "picking_port_id2",
                table: "PickingTask",
                newName: "main_port_id2");

            migrationBuilder.RenameColumn(
                name: "picking_port_id",
                table: "PickingTask",
                newName: "main_port_id");

            migrationBuilder.RenameColumn(
                name: "picking_platform_id",
                table: "PickingTask",
                newName: "main_platform_id");

            migrationBuilder.RenameColumn(
                name: "picking_no",
                table: "PickingTask",
                newName: "main_mode");

            migrationBuilder.RenameColumn(
                name: "picking_malfunction",
                table: "PickingTask",
                newName: "main_no");

            migrationBuilder.RenameColumn(
                name: "picking_inslot_code",
                table: "PickingTask",
                newName: "main_inslot_code");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_picking_slot_code",
                table: "PickingTask",
                newName: "IX_PickingTask_main_slot_code");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_picking_port_id2",
                table: "PickingTask",
                newName: "IX_PickingTask_main_port_id2");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_picking_port_id",
                table: "PickingTask",
                newName: "IX_PickingTask_main_port_id");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_picking_platform_id",
                table: "PickingTask",
                newName: "IX_PickingTask_main_platform_id");

            migrationBuilder.RenameIndex(
                name: "IX_PickingTask_picking_inslot_code",
                table: "PickingTask",
                newName: "IX_PickingTask_main_inslot_code");

            migrationBuilder.AddColumn<int>(
                name: "main_execute_flag",
                table: "PickingTask",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "main_malfunction",
                table: "PickingTask",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "main_manual_flag",
                table: "PickingTask",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_main_inslot_code",
                table: "PickingTask",
                column: "main_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PlatFormInfo_main_platform_id",
                table: "PickingTask",
                column: "main_platform_id",
                principalTable: "PlatFormInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PortInfo_main_port_id",
                table: "PickingTask",
                column: "main_port_id",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_PortInfo_main_port_id2",
                table: "PickingTask",
                column: "main_port_id2",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_main_slot_code",
                table: "PickingTask",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
