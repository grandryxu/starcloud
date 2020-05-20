using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editRowSlot : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "row_is_enable",
                table: "RowInfo");

            migrationBuilder.AddColumn<Guid>(
                name: "slot_out_id",
                table: "SlotInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "row_status",
                table: "RowInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_out_id",
                table: "SlotInfo",
                column: "slot_out_id");

            migrationBuilder.AddForeignKey(
                name: "FK_SlotInfo_SlotInfo_slot_out_id",
                table: "SlotInfo",
                column: "slot_out_id",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_SlotInfo_SlotInfo_slot_out_id",
                table: "SlotInfo");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_out_id",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_out_id",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "row_status",
                table: "RowInfo");

            migrationBuilder.AddColumn<int>(
                name: "row_is_enable",
                table: "RowInfo",
                nullable: false,
                defaultValue: 0);
        }
    }
}
