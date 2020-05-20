using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class reporttemps : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "ReportTemp",
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
                    TempId = table.Column<string>(nullable: true),
                    FullName = table.Column<string>(nullable: true),
                    EnCode = table.Column<string>(nullable: true),
                    TempCategory = table.Column<string>(nullable: true),
                    TempStyle = table.Column<string>(nullable: true),
                    TempType = table.Column<string>(nullable: true),
                    Description = table.Column<string>(nullable: true),
                    ParamJson = table.Column<string>(nullable: true),
                    SortCode = table.Column<int>(nullable: true),
                    EnabledMark = table.Column<int>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ReportTemp", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ReportTemp");
        }
    }
}
