using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class qualityCheckDetail : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "QualityCheckDetail",
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
                    check_bill_code = table.Column<string>(nullable: true),
                    inventory_batch_no = table.Column<string>(nullable: true),
                    inventory_lots_no = table.Column<string>(nullable: true),
                    inventory_product_date = table.Column<DateTime>(nullable: false),
                    inventory_product_lineid = table.Column<string>(nullable: true),
                    inventory_quantity = table.Column<decimal>(nullable: false),
                    stock_check_quantity = table.Column<decimal>(nullable: false),
                    inventory_box_code = table.Column<string>(nullable: true),
                    inventory_stock_code = table.Column<string>(nullable: true),
                    inventory_slot_code = table.Column<string>(nullable: true),
                    inventory_status = table.Column<int>(nullable: false),
                    inventory_stock_status = table.Column<int>(nullable: false),
                    quality_check_id = table.Column<Guid>(nullable: true),
                    inventory_goods_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QualityCheckDetail", x => x.Id);
                    table.ForeignKey(
                        name: "FK_QualityCheckDetail_GoodsInfo_inventory_goods_id",
                        column: x => x.inventory_goods_id,
                        principalTable: "GoodsInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_QualityCheckDetail_QualityCheck_quality_check_id",
                        column: x => x.quality_check_id,
                        principalTable: "QualityCheck",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheckDetail_inventory_goods_id",
                table: "QualityCheckDetail",
                column: "inventory_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheckDetail_quality_check_id",
                table: "QualityCheckDetail",
                column: "quality_check_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "QualityCheckDetail");
        }
    }
}
