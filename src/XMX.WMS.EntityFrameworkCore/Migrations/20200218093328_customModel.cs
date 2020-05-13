using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class customModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "CustomInfo",
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
                    custom_code = table.Column<string>(nullable: true),
                    custom_fid = table.Column<string>(nullable: true),
                    custom_name = table.Column<string>(nullable: true),
                    custom_short_name = table.Column<string>(nullable: true),
                    custom_address = table.Column<string>(nullable: true),
                    custom_linkman = table.Column<string>(nullable: true),
                    custom_phone = table.Column<string>(nullable: true),
                    custom_telephone = table.Column<string>(nullable: true),
                    custom_fax = table.Column<string>(nullable: true),
                    custom_creat_uid = table.Column<string>(nullable: true),
                    custom_creat_datetime = table.Column<DateTime>(nullable: false),
                    custom_modify_uid = table.Column<string>(nullable: true),
                    custom_modify_datetime = table.Column<DateTime>(nullable: false),
                    custom_is_enable = table.Column<int>(nullable: false),
                    custom_is_delete = table.Column<int>(nullable: false),
                    custom_company_id = table.Column<Guid>(nullable: true),
                    custom_type_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CustomInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_CustomInfo_CompanyInfo_custom_company_id",
                        column: x => x.custom_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_CustomInfo_CustomTypeInfo_custom_type_id",
                        column: x => x.custom_type_id,
                        principalTable: "CustomTypeInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_CustomInfo_custom_company_id",
                table: "CustomInfo",
                column: "custom_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_CustomInfo_custom_type_id",
                table: "CustomInfo",
                column: "custom_type_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "CustomInfo");
        }
    }
}
