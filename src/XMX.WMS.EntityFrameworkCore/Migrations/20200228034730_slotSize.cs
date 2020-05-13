using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class slotSize : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<Guid>(
                name: "slot_size_level",
                table: "SlotInfo",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "SlotSize",
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
                    size_name = table.Column<string>(nullable: true),
                    size_length = table.Column<decimal>(nullable: false),
                    size_height = table.Column<decimal>(nullable: false),
                    size_width = table.Column<decimal>(nullable: false),
                    size_remark = table.Column<string>(nullable: true),
                    size_creat_uid = table.Column<string>(nullable: true),
                    size_creat_datetime = table.Column<DateTime>(nullable: false),
                    size_modify_uid = table.Column<string>(nullable: true),
                    size_modify_datetime = table.Column<DateTime>(nullable: false),
                    size_is_enable = table.Column<int>(nullable: false),
                    size_is_delete = table.Column<int>(nullable: false),
                    size_company_id = table.Column<Guid>(nullable: true),
                    size_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_SlotSize", x => x.Id);
                    table.ForeignKey(
                        name: "FK_SlotSize_CompanyInfo_size_company_id",
                        column: x => x.size_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_SlotSize_WarehouseInfo_size_warehouse_id",
                        column: x => x.size_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_size_level",
                table: "SlotInfo",
                column: "slot_size_level");

            migrationBuilder.CreateIndex(
                name: "IX_SlotSize_size_company_id",
                table: "SlotSize",
                column: "size_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_SlotSize_size_warehouse_id",
                table: "SlotSize",
                column: "size_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_SlotInfo_SlotSize_slot_size_level",
                table: "SlotInfo",
                column: "slot_size_level",
                principalTable: "SlotSize",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_SlotInfo_SlotSize_slot_size_level",
                table: "SlotInfo");

            migrationBuilder.DropTable(
                name: "SlotSize");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_size_level",
                table: "SlotInfo");

            migrationBuilder.AlterColumn<string>(
                name: "slot_size_level",
                table: "SlotInfo",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);
        }
    }
}
