using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class encodingRule : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "EncodingRule",
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
                    code_name = table.Column<string>(nullable: true),
                    code_prefix = table.Column<string>(nullable: true),
                    code_date_type = table.Column<int>(nullable: false),
                    code_suffix_length = table.Column<int>(nullable: false),
                    code_record = table.Column<int>(nullable: false),
                    code_creat_uid = table.Column<string>(nullable: true),
                    code_creat_datetime = table.Column<DateTime>(nullable: false),
                    code_modify_uid = table.Column<string>(nullable: true),
                    code_modify_datetime = table.Column<DateTime>(nullable: false),
                    code_is_enable = table.Column<int>(nullable: false),
                    code_is_delete = table.Column<int>(nullable: false),
                    code_company_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EncodingRule", x => x.Id);
                    table.ForeignKey(
                        name: "FK_EncodingRule_CompanyInfo_code_company_id",
                        column: x => x.code_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_EncodingRule_code_company_id",
                table: "EncodingRule",
                column: "code_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "EncodingRule");
        }
    }
}
