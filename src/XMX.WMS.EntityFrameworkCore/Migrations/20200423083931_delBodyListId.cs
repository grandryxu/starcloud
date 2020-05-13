using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delBodyListId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "impbody_list_id",
                table: "ImportBillbody");

            migrationBuilder.DropColumn(
                name: "exphead_bill_date",
                table: "ExportBillhead");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "impbody_list_id",
                table: "ImportBillbody",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "exphead_bill_date",
                table: "ExportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));
        }
    }
}
