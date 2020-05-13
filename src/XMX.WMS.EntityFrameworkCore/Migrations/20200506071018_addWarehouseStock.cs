using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addWarehouseStock : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "WarehouseStock",
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
                    warehouse_stock = table.Column<decimal>(nullable: false),
                    warehouse_date = table.Column<string>(nullable: true),
                    warehouse_company_id = table.Column<Guid>(nullable: true),
                    warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_WarehouseStock", x => x.Id);
                    table.ForeignKey(
                        name: "FK_WarehouseStock_CompanyInfo_warehouse_company_id",
                        column: x => x.warehouse_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                        column: x => x.warehouse_id,
                        principalTable: "WarehouseInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_WarehouseStock_warehouse_company_id",
                table: "WarehouseStock",
                column: "warehouse_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_WarehouseStock_warehouse_id",
                table: "WarehouseStock",
                column: "warehouse_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "WarehouseStock");
        }
    }
}
