using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class packModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "PackInfo",
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
                    pack_code = table.Column<string>(nullable: true),
                    pack_name = table.Column<string>(nullable: true),
                    pack_picture = table.Column<string>(nullable: true),
                    pack_creat_uid = table.Column<string>(nullable: true),
                    pack_creat_datetime = table.Column<DateTime>(nullable: false),
                    pack_modify_uid = table.Column<string>(nullable: true),
                    pack_modify_datetime = table.Column<DateTime>(nullable: false),
                    pack_is_enable = table.Column<int>(nullable: false),
                    pack_is_delete = table.Column<int>(nullable: false),
                    pack_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PackInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_PackInfo_CompanyInfo_pack_company_id",
                        column: x => x.pack_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_PackInfo_pack_company_id",
                table: "PackInfo",
                column: "pack_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "PackInfo");
        }
    }
}
