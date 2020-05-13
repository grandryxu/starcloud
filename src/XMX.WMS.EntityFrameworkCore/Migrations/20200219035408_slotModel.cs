using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class slotModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "SlotInfo",
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
                    slot_name = table.Column<string>(nullable: true),
                    slot_code = table.Column<string>(nullable: true),
                    slot_layer = table.Column<int>(nullable: false),
                    slot_column = table.Column<int>(nullable: false),
                    slot_row = table.Column<int>(nullable: false),
                    slot_stock_status = table.Column<int>(nullable: false),
                    slot_closed_status = table.Column<int>(nullable: false),
                    slot_imp_status = table.Column<int>(nullable: false),
                    slot_exp_status = table.Column<int>(nullable: false),
                    slot_size_level = table.Column<string>(nullable: true),
                    slot_moveable_status = table.Column<int>(nullable: false),
                    slot_creat_uid = table.Column<string>(nullable: true),
                    slot_creat_datetime = table.Column<DateTime>(nullable: false),
                    slot_modify_uid = table.Column<string>(nullable: true),
                    slot_modify_datetime = table.Column<DateTime>(nullable: false),
                    slot_is_enable = table.Column<int>(nullable: false),
                    slot_is_delete = table.Column<int>(nullable: false),
                    slot_warehouse_id = table.Column<Guid>(nullable: true),
                    slot_area_id = table.Column<Guid>(nullable: true),
                    slot_row_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_SlotInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_SlotInfo_AreaInfo_slot_area_id",
                        column: x => x.slot_area_id,
                        principalTable: "AreaInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_SlotInfo_WarehouseInfo_slot_warehouse_id",
                        column: x => x.slot_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_area_id",
                table: "SlotInfo",
                column: "slot_area_id");

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_warehouse_id",
                table: "SlotInfo",
                column: "slot_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "SlotInfo");
        }
    }
}
