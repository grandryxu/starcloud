using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class taskslotcode : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<Guid>(
                name: "main_slot_code",
                table: "TaskMainInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "main_inslot_code",
                table: "TaskMainInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "main_slot_code",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "main_inslot_code",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_inslot_code",
                table: "TaskMainInfo",
                column: "main_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code");

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_inslot_code",
                table: "HistoryTaskMainInfo",
                column: "main_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_HistoryTaskMainInfo_main_slot_code",
                table: "HistoryTaskMainInfo",
                column: "main_slot_code");

            migrationBuilder.AddForeignKey(
                name: "FK_HistoryTaskMainInfo_SlotInfo_main_inslot_code",
                table: "HistoryTaskMainInfo",
                column: "main_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_HistoryTaskMainInfo_SlotInfo_main_slot_code",
                table: "HistoryTaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_inslot_code",
                table: "TaskMainInfo",
                column: "main_inslot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_HistoryTaskMainInfo_SlotInfo_main_inslot_code",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_HistoryTaskMainInfo_SlotInfo_main_slot_code",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_inslot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_TaskMainInfo_main_inslot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_TaskMainInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_HistoryTaskMainInfo_main_inslot_code",
                table: "HistoryTaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_HistoryTaskMainInfo_main_slot_code",
                table: "HistoryTaskMainInfo");

            migrationBuilder.AlterColumn<string>(
                name: "main_slot_code",
                table: "TaskMainInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "main_inslot_code",
                table: "TaskMainInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "main_slot_code",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "main_inslot_code",
                table: "HistoryTaskMainInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);
        }
    }
}
