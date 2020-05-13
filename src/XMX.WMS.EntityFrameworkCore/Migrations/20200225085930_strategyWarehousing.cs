using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class strategyWarehousing : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "warehouse_code",
                table: "WarehouseInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "warehouse_slot_type",
                table: "WarehouseInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "StrategyWarehousing",
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
                    warehousing_name = table.Column<string>(nullable: true),
                    warehousing_buzy = table.Column<int>(nullable: false),
                    warehousing_buzy_priority = table.Column<int>(nullable: false),
                    warehousing_select = table.Column<int>(nullable: false),
                    warehousing_select_priority = table.Column<int>(nullable: false),
                    warehousing_remark = table.Column<string>(nullable: true),
                    warehousing_creat_uid = table.Column<string>(nullable: true),
                    warehousing_creat_datetime = table.Column<DateTime>(nullable: false),
                    warehousing_modify_uid = table.Column<string>(nullable: true),
                    warehousing_modify_datetime = table.Column<DateTime>(nullable: false),
                    warehousing_is_enable = table.Column<int>(nullable: false),
                    warehousing_is_delete = table.Column<int>(nullable: false),
                    warehousing_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StrategyWarehousing", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StrategyWarehousing_CompanyInfo_warehousing_company_id",
                        column: x => x.warehousing_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StrategyWarehousing_warehousing_company_id",
                table: "StrategyWarehousing",
                column: "warehousing_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "warehouse_code",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "warehouse_slot_type",
                table: "WarehouseInfo");
        }
    }
}
