using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class AddCompanyAddress : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Address",
                table: "CompanyInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "AddressDetail",
                table: "CompanyInfo",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Address",
                table: "CompanyInfo");

            migrationBuilder.DropColumn(
                name: "AddressDetail",
                table: "CompanyInfo");
        }
    }
}
