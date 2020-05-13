using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class qualityModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "unit_modify_datetime",
                table: "UnitInfo",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AlterColumn<DateTime>(
                name: "unit_creat_datetime",
                table: "UnitInfo",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "QualityInfo",
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
                    quality_name = table.Column<string>(nullable: true),
                    quality_creat_uid = table.Column<string>(nullable: true),
                    quality_creat_datetime = table.Column<DateTime>(nullable: false),
                    quality_modify_uid = table.Column<string>(nullable: true),
                    quality_modify_datetime = table.Column<DateTime>(nullable: false),
                    quality_is_enable = table.Column<int>(nullable: false),
                    quality_is_delete = table.Column<int>(nullable: false),
                    quality_company_id = table.Column<Guid>(nullable: true),
                    goods_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QualityInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_QualityInfo_CompanyInfo_goods_company_id",
                        column: x => x.goods_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_QualityInfo_goods_company_id",
                table: "QualityInfo",
                column: "goods_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "QualityInfo");

            migrationBuilder.AlterColumn<string>(
                name: "unit_modify_datetime",
                table: "UnitInfo",
                nullable: true,
                oldClrType: typeof(DateTime));

            migrationBuilder.AlterColumn<string>(
                name: "unit_creat_datetime",
                table: "UnitInfo",
                nullable: true,
                oldClrType: typeof(DateTime));
        }
    }
}
