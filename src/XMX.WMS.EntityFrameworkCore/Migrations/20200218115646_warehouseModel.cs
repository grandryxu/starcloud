using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class warehouseModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "WarehouseInfo",
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
                    warehouse_name = table.Column<string>(nullable: true),
                    warehouse_type = table.Column<int>(nullable: false),
                    warehouse_creat_uid = table.Column<string>(nullable: true),
                    warehouse_creat_datetime = table.Column<DateTime>(nullable: false),
                    warehouse_modify_uid = table.Column<string>(nullable: true),
                    warehouse_modify_datetime = table.Column<DateTime>(nullable: false),
                    warehouse_is_enable = table.Column<int>(nullable: false),
                    warehouse_is_delete = table.Column<int>(nullable: false),
                    warehouse_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WarehouseInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                        column: x => x.warehouse_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_PortInfo_port_warehouse_id",
                table: "PortInfo",
                column: "port_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_PlatFormInfo_platform_warehouse_id",
                table: "PlatFormInfo",
                column: "platform_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_WarehouseInfo_warehouse_company_id",
                table: "WarehouseInfo",
                column: "warehouse_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_PlatFormInfo_WarehouseInfo_platform_warehouse_id",
                table: "PlatFormInfo",
                column: "platform_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_PortInfo_WarehouseInfo_port_warehouse_id",
                table: "PortInfo",
                column: "port_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_PlatFormInfo_WarehouseInfo_platform_warehouse_id",
                table: "PlatFormInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_PortInfo_WarehouseInfo_port_warehouse_id",
                table: "PortInfo");

            migrationBuilder.DropTable(
                name: "WarehouseInfo");

            migrationBuilder.DropIndex(
                name: "IX_PortInfo_port_warehouse_id",
                table: "PortInfo");

            migrationBuilder.DropIndex(
                name: "IX_PlatFormInfo_platform_warehouse_id",
                table: "PlatFormInfo");
        }
    }
}
