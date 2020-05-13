using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class alarmtable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Alarm",
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
                    alarm_name = table.Column<string>(nullable: true),
                    alarm_value = table.Column<int>(nullable: false),
                    impbody_bill_bar = table.Column<string>(nullable: true),
                    thresholdz_value = table.Column<int>(nullable: false),
                    area_remark = table.Column<string>(nullable: true),
                    strategy_id = table.Column<Guid>(nullable: true),
                    company_id = table.Column<Guid>(nullable: true),
                    goods_id = table.Column<Guid>(nullable: true),
                    inventory_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Alarm", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Alarm_CompanyInfo_company_id",
                        column: x => x.company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Alarm_GoodsInfo_goods_id",
                        column: x => x.goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Alarm_InventoryInfo_inventory_id",
                        column: x => x.inventory_id,
                        principalTable: "InventoryInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_Alarm_StrategyMonitor_strategy_id",
                        column: x => x.strategy_id,
                        principalTable: "StrategyMonitor",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Alarm_company_id",
                table: "Alarm",
                column: "company_id");

            migrationBuilder.CreateIndex(
                name: "IX_Alarm_goods_id",
                table: "Alarm",
                column: "goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_Alarm_inventory_id",
                table: "Alarm",
                column: "inventory_id");

            migrationBuilder.CreateIndex(
                name: "IX_Alarm_strategy_id",
                table: "Alarm",
                column: "strategy_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Alarm");
        }
    }
}
