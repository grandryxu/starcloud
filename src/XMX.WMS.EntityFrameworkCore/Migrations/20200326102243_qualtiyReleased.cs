using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class qualtiyReleased : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "QualityReleased",
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
                    quare_goods_code = table.Column<string>(nullable: true),
                    quare_goods_name = table.Column<string>(nullable: true),
                    quare_batch_no = table.Column<string>(nullable: true),
                    quare_stock_in_code = table.Column<string>(nullable: true),
                    quare_quality_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_QualityReleased", x => x.Id);
                    table.ForeignKey(
                        name: "FK_QualityReleased_QualityInfo_quare_quality_id",
                        column: x => x.quare_quality_id,
                        principalTable: "QualityInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_QualityReleased_quare_quality_id",
                table: "QualityReleased",
                column: "quare_quality_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "QualityReleased");
        }
    }
}
