using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class customTypeModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CustomTypeInfo",
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
                    customtype_name = table.Column<string>(nullable: true),
                    customtype_creat_uid = table.Column<string>(nullable: true),
                    customtype_creat_datetime = table.Column<DateTime>(nullable: false),
                    customtype_modify_uid = table.Column<string>(nullable: true),
                    customtype_modify_datetime = table.Column<DateTime>(nullable: false),
                    customtype_is_enable = table.Column<int>(nullable: false),
                    customtype_is_delete = table.Column<int>(nullable: false),
                    customtype_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CustomTypeInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CustomTypeInfo_CompanyInfo_customtype_company_id",
                        column: x => x.customtype_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_CustomTypeInfo_customtype_company_id",
                table: "CustomTypeInfo",
                column: "customtype_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CustomTypeInfo");
        }
    }
}
