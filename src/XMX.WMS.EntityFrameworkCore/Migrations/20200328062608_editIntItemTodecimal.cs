using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editIntItemTodecimal : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "expbody_plan_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "expbody_fulfill_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "expbody_binding_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "expbody_plan_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "expbody_fulfill_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "expbody_binding_quantity",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));
        }
    }
}
