using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class ccflats : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "rgv_creat_datetime",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_creat_uid",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_id",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_modify_datetime",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_modify_uid",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "agv_creat_datetime",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "agv_creat_uid",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "agv_id",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "agv_modify_datetime",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "agv_modify_uid",
                table: "AGVTask");

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_port_id2",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_port_id",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_platform_id",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "rgv_inslot_code",
                table: "RGVTask",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "rgv_slot_code",
                table: "RGVTask",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.AlterColumn<Guid>(
                name: "agv_port_id2",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "agv_port_id",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "agv_platform_id",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "agv_inslot_code",
                table: "AGVTask",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "agv_slot_code",
                table: "AGVTask",
                nullable: false,
                defaultValue: new Guid("00000000-0000-0000-0000-000000000000"));

            migrationBuilder.CreateIndex(
                name: "IX_RGVTask_rgv_inslot_code",
                table: "RGVTask",
                column: "rgv_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_RGVTask_rgv_platform_id",
                table: "RGVTask",
                column: "rgv_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_RGVTask_rgv_port_id",
                table: "RGVTask",
                column: "rgv_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_RGVTask_rgv_port_id2",
                table: "RGVTask",
                column: "rgv_port_id2");

            migrationBuilder.CreateIndex(
                name: "IX_RGVTask_rgv_slot_code",
                table: "RGVTask",
                column: "rgv_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_AGVTask_agv_inslot_code",
                table: "AGVTask",
                column: "agv_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_AGVTask_agv_platform_id",
                table: "AGVTask",
                column: "agv_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_AGVTask_agv_port_id",
                table: "AGVTask",
                column: "agv_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_AGVTask_agv_port_id2",
                table: "AGVTask",
                column: "agv_port_id2");

            migrationBuilder.CreateIndex(
                name: "IX_AGVTask_agv_slot_code",
                table: "AGVTask",
                column: "agv_slot_code");

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_inslot_code",
                table: "AGVTask",
                column: "agv_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_PlatFormInfo_agv_platform_id",
                table: "AGVTask",
                column: "agv_platform_id",
                principalTable: "PlatFormInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_PortInfo_agv_port_id",
                table: "AGVTask",
                column: "agv_port_id",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_PortInfo_agv_port_id2",
                table: "AGVTask",
                column: "agv_port_id2",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask",
                column: "agv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_inslot_code",
                table: "RGVTask",
                column: "rgv_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_PlatFormInfo_rgv_platform_id",
                table: "RGVTask",
                column: "rgv_platform_id",
                principalTable: "PlatFormInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_PortInfo_rgv_port_id",
                table: "RGVTask",
                column: "rgv_port_id",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_PortInfo_rgv_port_id2",
                table: "RGVTask",
                column: "rgv_port_id2",
                principalTable: "PortInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask",
                column: "rgv_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_inslot_code",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_PlatFormInfo_agv_platform_id",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_PortInfo_agv_port_id",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_PortInfo_agv_port_id2",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_AGVTask_SlotInfo_agv_slot_code",
                table: "AGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_inslot_code",
                table: "RGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_PlatFormInfo_rgv_platform_id",
                table: "RGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_PortInfo_rgv_port_id",
                table: "RGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_PortInfo_rgv_port_id2",
                table: "RGVTask");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVTask_SlotInfo_rgv_slot_code",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_RGVTask_rgv_inslot_code",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_RGVTask_rgv_platform_id",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_RGVTask_rgv_port_id",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_RGVTask_rgv_port_id2",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_RGVTask_rgv_slot_code",
                table: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_AGVTask_agv_inslot_code",
                table: "AGVTask");

            migrationBuilder.DropIndex(
                name: "IX_AGVTask_agv_platform_id",
                table: "AGVTask");

            migrationBuilder.DropIndex(
                name: "IX_AGVTask_agv_port_id",
                table: "AGVTask");

            migrationBuilder.DropIndex(
                name: "IX_AGVTask_agv_port_id2",
                table: "AGVTask");

            migrationBuilder.DropIndex(
                name: "IX_AGVTask_agv_slot_code",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_inslot_code",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "rgv_slot_code",
                table: "RGVTask");

            migrationBuilder.DropColumn(
                name: "agv_inslot_code",
                table: "AGVTask");

            migrationBuilder.DropColumn(
                name: "agv_slot_code",
                table: "AGVTask");

            migrationBuilder.AlterColumn<string>(
                name: "rgv_port_id2",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "rgv_port_id",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "rgv_platform_id",
                table: "RGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "rgv_creat_datetime",
                table: "RGVTask",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "rgv_creat_uid",
                table: "RGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "rgv_id",
                table: "RGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "rgv_modify_datetime",
                table: "RGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "rgv_modify_uid",
                table: "RGVTask",
                nullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "agv_port_id2",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "agv_port_id",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "agv_platform_id",
                table: "AGVTask",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "agv_creat_datetime",
                table: "AGVTask",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "agv_creat_uid",
                table: "AGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "agv_id",
                table: "AGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "agv_modify_datetime",
                table: "AGVTask",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "agv_modify_uid",
                table: "AGVTask",
                nullable: true);
        }
    }
}
