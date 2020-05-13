using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class strategyDistribution : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "StrategyDistribution",
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
                    distribution_name = table.Column<string>(nullable: true),
                    distribution_order = table.Column<int>(nullable: false),
                    distribution_order_priority = table.Column<int>(nullable: false),
                    distribution_unpack = table.Column<int>(nullable: false),
                    distribution_unpack_priority = table.Column<int>(nullable: false),
                    distribution_fefo = table.Column<int>(nullable: false),
                    distribution_fefo_priority = table.Column<int>(nullable: false),
                    distribution_remark = table.Column<string>(nullable: true),
                    distribution_creat_uid = table.Column<string>(nullable: true),
                    distribution_creat_datetime = table.Column<DateTime>(nullable: false),
                    distribution_modify_uid = table.Column<string>(nullable: true),
                    distribution_modify_datetime = table.Column<DateTime>(nullable: false),
                    distribution_is_enable = table.Column<int>(nullable: false),
                    distribution_is_delete = table.Column<int>(nullable: false),
                    distribution_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_StrategyDistribution", x => x.Id);
                    table.ForeignKey(
                        name: "FK_StrategyDistribution_CompanyInfo_distribution_company_id",
                        column: x => x.distribution_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_StrategyDistribution_distribution_company_id",
                table: "StrategyDistribution",
                column: "distribution_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "StrategyDistribution");
        }
    }
}
