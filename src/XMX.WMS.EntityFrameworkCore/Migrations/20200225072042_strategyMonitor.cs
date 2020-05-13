using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class strategyMonitor : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "StrategyMonitor",
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
                    monitor_name = table.Column<string>(nullable: true),
                    monitor_expired_days = table.Column<int>(nullable: false),
                    monitor_days_max = table.Column<int>(nullable: false),
                    monitor_stock_max = table.Column<int>(nullable: false),
                    monitor_stock_min = table.Column<int>(nullable: false),
                    monitor_recheck_days = table.Column<int>(nullable: false),
                    monitor_remark = table.Column<string>(nullable: true),
                    monitor_creat_uid = table.Column<string>(nullable: true),
                    monitor_creat_datetime = table.Column<DateTime>(nullable: false),
                    monitor_modify_uid = table.Column<string>(nullable: true),
                    monitor_modify_datetime = table.Column<DateTime>(nullable: false),
                    monitor_is_enable = table.Column<int>(nullable: false),
                    monitor_is_delete = table.Column<int>(nullable: false),
                    monitor_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StrategyMonitor", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StrategyMonitor_CompanyInfo_monitor_company_id",
                        column: x => x.monitor_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StrategyMonitor_monitor_company_id",
                table: "StrategyMonitor",
                column: "monitor_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StrategyMonitor");
        }
    }
}
