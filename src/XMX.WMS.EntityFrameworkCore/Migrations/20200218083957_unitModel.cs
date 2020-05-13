using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class unitModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "UnitInfo",
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
                    unit_name = table.Column<string>(nullable: true),
                    unit_creat_uid = table.Column<string>(nullable: true),
                    unit_creat_datetime = table.Column<string>(nullable: true),
                    unit_modify_uid = table.Column<string>(nullable: true),
                    unit_modify_datetime = table.Column<string>(nullable: true),
                    unit_is_enable = table.Column<int>(nullable: false),
                    unit_is_delete = table.Column<int>(nullable: false),
                    unit_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UnitInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_UnitInfo_CompanyInfo_unit_company_id",
                        column: x => x.unit_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_UnitInfo_unit_company_id",
                table: "UnitInfo",
                column: "unit_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "UnitInfo");
        }
    }
}
