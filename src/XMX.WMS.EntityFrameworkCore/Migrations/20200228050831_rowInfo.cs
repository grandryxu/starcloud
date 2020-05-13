using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class rowInfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "RowInfo",
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
                    row_type = table.Column<int>(nullable: false),
                    row_name = table.Column<string>(nullable: true),
                    row_remark = table.Column<string>(nullable: true),
                    row_no = table.Column<int>(nullable: false),
                    row_inout_type = table.Column<int>(nullable: false),
                    row_in_state = table.Column<int>(nullable: false),
                    row_out_state = table.Column<int>(nullable: false),
                    row_order = table.Column<int>(nullable: false),
                    row_start_layer = table.Column<string>(nullable: true),
                    row_end_layer = table.Column<int>(nullable: false),
                    row_start_column = table.Column<int>(nullable: false),
                    row_end_column = table.Column<int>(nullable: false),
                    row_movealbe_status = table.Column<int>(nullable: false),
                    row_creat_uid = table.Column<string>(nullable: true),
                    row_creat_datetime = table.Column<DateTime>(nullable: false),
                    row_modify_uid = table.Column<string>(nullable: true),
                    row_modify_datetime = table.Column<DateTime>(nullable: false),
                    row_is_enable = table.Column<int>(nullable: false),
                    row_is_delete = table.Column<int>(nullable: false),
                    row_company_id = table.Column<Guid>(nullable: true),
                    row_warehouse_id = table.Column<Guid>(nullable: true),
                    row_out_id = table.Column<Guid>(nullable: true),
                    row_tunnel_id = table.Column<Guid>(nullable: true),
                    row_size_id = table.Column<Guid>(nullable: true),
                    row_area_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_RowInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_RowInfo_AreaInfo_row_area_id",
                        column: x => x.row_area_id,
                        principalTable: "AreaInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_RowInfo_CompanyInfo_row_company_id",
                        column: x => x.row_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_RowInfo_RowInfo_row_out_id",
                        column: x => x.row_out_id,
                        principalTable: "RowInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_RowInfo_SlotSize_row_size_id",
                        column: x => x.row_size_id,
                        principalTable: "SlotSize",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_RowInfo_WarehouseInfo_row_warehouse_id",
                        column: x => x.row_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_row_id",
                table: "SlotInfo",
                column: "slot_row_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_area_id",
                table: "RowInfo",
                column: "row_area_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_company_id",
                table: "RowInfo",
                column: "row_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_out_id",
                table: "RowInfo",
                column: "row_out_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_size_id",
                table: "RowInfo",
                column: "row_size_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_warehouse_id",
                table: "RowInfo",
                column: "row_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_SlotInfo_RowInfo_slot_row_id",
                table: "SlotInfo",
                column: "slot_row_id",
                principalTable: "RowInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_SlotInfo_RowInfo_slot_row_id",
                table: "SlotInfo");

            migrationBuilder.DropTable(
                name: "RowInfo");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_row_id",
                table: "SlotInfo");
        }
    }
}
