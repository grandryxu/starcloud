using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class importorder : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "imbody_recheck_date",
                table: "ImportBillbody",
                newName: "impbody_recheck_date");

            migrationBuilder.AlterColumn<decimal>(
                name: "imporder_quantity",
                table: "ImportOrder",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "impbody_plan_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "impbody_fulfill_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "impbody_binding_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(int));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "impbody_recheck_date",
                table: "ImportBillbody",
                newName: "imbody_recheck_date");

            migrationBuilder.AlterColumn<int>(
                name: "imporder_quantity",
                table: "ImportOrder",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "impbody_plan_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "impbody_fulfill_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<int>(
                name: "impbody_binding_quantity",
                table: "ImportBillbody",
                nullable: false,
                oldClrType: typeof(decimal));
        }
    }
}
