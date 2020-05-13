using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class exportBodyDecimal : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "warehouse_is_delete",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "warehouse_modify_uid",
                table: "HistoryTaskMainInfo",
                newName: "main_modify_uid");

            migrationBuilder.RenameColumn(
                name: "warehouse_modify_datetime",
                table: "HistoryTaskMainInfo",
                newName: "main_modify_datetime");

            migrationBuilder.RenameColumn(
                name: "warehouse_creat_uid",
                table: "HistoryTaskMainInfo",
                newName: "main_creat_uid");

            migrationBuilder.RenameColumn(
                name: "warehouse_creat_datetime",
                table: "HistoryTaskMainInfo",
                newName: "main_creat_datetime");

            migrationBuilder.AddColumn<int>(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0);

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

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "main_modify_uid",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_modify_uid");

            migrationBuilder.RenameColumn(
                name: "main_modify_datetime",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_modify_datetime");

            migrationBuilder.RenameColumn(
                name: "main_creat_uid",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_creat_uid");

            migrationBuilder.RenameColumn(
                name: "main_creat_datetime",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_creat_datetime");

            migrationBuilder.AddColumn<int>(
                name: "warehouse_is_delete",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0);

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
    }
}
