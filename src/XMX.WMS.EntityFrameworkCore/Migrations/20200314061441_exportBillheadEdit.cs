using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class exportBillheadEdit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "exphead_bill_date",
                table: "ExportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "exphead_remark",
                table: "ExportBillhead",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "exphead_bill_date",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "exphead_remark",
                table: "ExportBillhead");
        }
    }
}
