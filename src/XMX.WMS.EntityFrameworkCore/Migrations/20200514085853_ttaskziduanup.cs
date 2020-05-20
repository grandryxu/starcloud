using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class ttaskziduanup : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
                table: "FlatBankTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask");

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_slot_code",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "picking_slot_code",
                table: "PickingTask",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "flat_slot_code",
                table: "FlatBankTask",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "agv_slot_code",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask",
                column: "agv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
                table: "FlatBankTask",
                column: "flat_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask",
                column: "picking_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask",
                column: "rgv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
                table: "FlatBankTask");

            migrationBuilder.DropForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask");

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_slot_code",
                table: "RGVTask",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "picking_slot_code",
                table: "PickingTask",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "flat_slot_code",
                table: "FlatBankTask",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "agv_slot_code",
                table: "AGVTask",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask",
                column: "agv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
                table: "FlatBankTask",
                column: "flat_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_PickingTask_SlotInfo_picking_slot_code",
                table: "PickingTask",
                column: "picking_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask",
                column: "rgv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
