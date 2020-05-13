using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class updateCustom : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "customtype_code",
                table: "CustomTypeInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "customtype_remark",
                table: "CustomTypeInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "custom_area",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "custom_city",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "custom_province",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "custom_remark",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "custom_town",
                table: "CustomInfo",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "customtype_code",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "customtype_remark",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "custom_area",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_city",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_province",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_remark",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_town",
                table: "CustomInfo");
        }
    }
}
