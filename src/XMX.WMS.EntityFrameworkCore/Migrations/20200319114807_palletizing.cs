using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class palletizing : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "PalletizingInfo",
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
                    palletizing_code = table.Column<string>(nullable: true),
                    palletizing_name = table.Column<string>(nullable: true),
                    palletizing_remark = table.Column<string>(nullable: true),
                    online_state = table.Column<int>(nullable: false),
                    alarm_state = table.Column<int>(nullable: false),
                    rgv_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PalletizingInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_PalletizingInfo_WarehouseInfo_rgv_warehouse_id",
                        column: x => x.rgv_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_PalletizingInfo_rgv_warehouse_id",
                table: "PalletizingInfo",
                column: "rgv_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "PalletizingInfo");
        }
    }
}
