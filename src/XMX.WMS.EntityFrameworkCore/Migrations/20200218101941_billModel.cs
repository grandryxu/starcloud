using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class billModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "BillInfo",
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
                    bill_name = table.Column<string>(nullable: true),
                    bill_type = table.Column<int>(nullable: false),
                    bill_remark = table.Column<string>(nullable: true),
                    bill_creat_uid = table.Column<string>(nullable: true),
                    bill_creat_datetime = table.Column<DateTime>(nullable: false),
                    bill_modify_uid = table.Column<string>(nullable: true),
                    bill_modify_datetime = table.Column<DateTime>(nullable: false),
                    bill_is_enable = table.Column<int>(nullable: false),
                    bill_is_delete = table.Column<int>(nullable: false),
                    bill_company_id = table.Column<Guid>(nullable: true),
                    bill_rule_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_BillInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_BillInfo_CompanyInfo_bill_company_id",
                        column: x => x.bill_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_BillInfo_bill_company_id",
                table: "BillInfo",
                column: "bill_company_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "BillInfo");
        }
    }
}
