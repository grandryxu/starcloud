using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class equipment_rgv_stacker : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "RGVInfo",
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
                    rgv_code = table.Column<string>(nullable: true),
                    rgv_name = table.Column<string>(nullable: true),
                    rgv_remark = table.Column<string>(nullable: true),
                    online_state = table.Column<int>(nullable: false),
                    alarm_state = table.Column<int>(nullable: false),
                    rgv_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_RGVInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                        column: x => x.rgv_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "StackerInfo",
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
                    stacker_code = table.Column<string>(nullable: true),
                    stacker_name = table.Column<string>(nullable: true),
                    stacker_remark = table.Column<string>(nullable: true),
                    online_state = table.Column<int>(nullable: false),
                    alarm_state = table.Column<int>(nullable: false),
                    stacker_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StackerInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                        column: x => x.stacker_warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_RGVInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_StackerInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "RGVInfo");

            migrationBuilder.DropTable(
                name: "StackerInfo");
        }
    }
}
