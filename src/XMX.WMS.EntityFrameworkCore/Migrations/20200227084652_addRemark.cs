using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addRemark : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "platform_remark",
                table: "PlatFormInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "pack_remark",
                table: "PackInfo",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "platform_remark",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "pack_remark",
                table: "PackInfo");
        }
    }
}
