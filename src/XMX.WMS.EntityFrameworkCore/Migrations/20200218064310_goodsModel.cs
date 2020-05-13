using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class goodsModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "GoodsInfo",
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
                    goods_code = table.Column<string>(nullable: true),
                    goods_external_id = table.Column<string>(nullable: true),
                    goods_name = table.Column<string>(nullable: true),
                    goods_short_name = table.Column<string>(nullable: true),
                    goods_standard = table.Column<string>(nullable: true),
                    goods_model = table.Column<string>(nullable: true),
                    goods_unit = table.Column<string>(nullable: true),
                    goods_stock_qty = table.Column<decimal>(nullable: false),
                    goods_small_qty = table.Column<decimal>(nullable: false),
                    goods_medium_qty = table.Column<decimal>(nullable: false),
                    goods_large_qty = table.Column<decimal>(nullable: false),
                    goods_length = table.Column<decimal>(nullable: false),
                    goods_width = table.Column<decimal>(nullable: false),
                    goods_height = table.Column<decimal>(nullable: false),
                    goods_stock_max = table.Column<decimal>(nullable: false),
                    goods_stock_min = table.Column<decimal>(nullable: false),
                    goods_factory = table.Column<string>(nullable: true),
                    goods_expiry_date = table.Column<int>(nullable: false),
                    goods_recheck_date = table.Column<int>(nullable: false),
                    goods_ABC_class = table.Column<string>(nullable: true),
                    goods_pack = table.Column<string>(nullable: true),
                    goods_picture = table.Column<string>(nullable: true),
                    goods_creat_uid = table.Column<string>(nullable: true),
                    goods_creat_datetime = table.Column<DateTime>(nullable: false),
                    goods_modify_uid = table.Column<string>(nullable: true),
                    goods_modify_datetime = table.Column<DateTime>(nullable: false),
                    goods_is_enable = table.Column<int>(nullable: false),
                    goods_is_delete = table.Column<int>(nullable: false),
                    goods_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_GoodsInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_GoodsInfo_CompanyInfo_goods_company_id",
                        column: x => x.goods_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_company_id",
                table: "GoodsInfo",
                column: "goods_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "GoodsInfo");
        }
    }
}
