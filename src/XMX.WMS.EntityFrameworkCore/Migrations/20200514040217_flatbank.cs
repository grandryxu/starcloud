using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class flatbank : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
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
                    flat_no = table.Column<int>(nullable: false),
                    flat_priority = table.Column<int>(nullable: false),
                    flat_mode = table.Column<int>(nullable: false),
                    flat_stock_code = table.Column<string>(nullable: true),
                    flat_execute_flag = table.Column<int>(nullable: false),
                    flat_manual_flag = table.Column<int>(nullable: false),
                    flat_malfunction = table.Column<string>(nullable: true),
                    flat_slot_code = table.Column<Guid>(nullable: false),
                    flat_inslot_code = table.Column<Guid>(nullable: true),
                    flat_port_id = table.Column<Guid>(nullable: true),
                    flat_platform_id = table.Column<Guid>(nullable: true),
                    flat_port_id2 = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_FlatBankTask", x => x.Id);
                    table.ForeignKey(
                        name: "FK_FlatBankTask_SlotInfo_flat_inslot_code",
                        column: x => x.flat_inslot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_FlatBankTask_PlatFormInfo_flat_platform_id",
                        column: x => x.flat_platform_id,
                        principalTable: "PlatFormInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_FlatBankTask_PortInfo_flat_port_id",
                        column: x => x.flat_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_FlatBankTask_PortInfo_flat_port_id2",
                        column: x => x.flat_port_id2,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
                        column: x => x.flat_slot_code,
                        principalTable: "SlotInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_inslot_code",
                table: "FlatBankTask",
                column: "flat_inslot_code");

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_platform_id",
                table: "FlatBankTask",
                column: "flat_platform_id");

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_port_id",
                table: "FlatBankTask",
                column: "flat_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_port_id2",
                table: "FlatBankTask",
                column: "flat_port_id2");

            migrationBuilder.CreateIndex(
                name: "IX_FlatBankTask_flat_slot_code",
                table: "FlatBankTask",
                column: "flat_slot_code");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "FlatBankTask");
        }
    }
}
