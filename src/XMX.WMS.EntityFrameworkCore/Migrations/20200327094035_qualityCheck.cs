using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class qualityCheck : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "QualityCheck",
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
                    check_bill = table.Column<string>(nullable: true),
                    bill_type = table.Column<string>(nullable: true),
                    check_goods_code = table.Column<string>(nullable: true),
                    check_goods_name = table.Column<string>(nullable: true),
                    check_batch_no = table.Column<string>(nullable: true),
                    origin_quality_status = table.Column<string>(nullable: true),
                    checked_quality_status = table.Column<string>(nullable: true),
                    checked_quality_status_id = table.Column<Guid>(nullable: false),
                    check_time = table.Column<DateTime>(nullable: false),
                    remark = table.Column<string>(nullable: true),
                    stock_num = table.Column<decimal>(nullable: false),
                    check_num = table.Column<decimal>(nullable: false),
                    check_inventory_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QualityCheck", x => x.Id);
                    table.ForeignKey(
                        name: "FK_QualityCheck_InventoryInfo_check_inventory_id",
                        column: x => x.check_inventory_id,
                        principalTable: "InventoryInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheck_check_inventory_id",
                table: "QualityCheck",
                column: "check_inventory_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "QualityCheck");
        }
    }
}
