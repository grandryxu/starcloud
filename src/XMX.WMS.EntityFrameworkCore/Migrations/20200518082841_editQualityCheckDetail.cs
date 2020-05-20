using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editQualityCheckDetail : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheckDetail_QualityCheck_quality_check_id",
                table: "QualityCheckDetail");

            migrationBuilder.DropColumn(
                name: "check_bill_code",
                table: "QualityCheckDetail");

            migrationBuilder.DropColumn(
                name: "inventory_product_date",
                table: "QualityCheckDetail");

            migrationBuilder.DropColumn(
                name: "inventory_product_lineid",
                table: "QualityCheckDetail");

            migrationBuilder.DropColumn(
                name: "inventory_status",
                table: "QualityCheckDetail");

            migrationBuilder.DropColumn(
                name: "inventory_stock_status",
                table: "QualityCheckDetail");

            migrationBuilder.RenameColumn(
                name: "stock_check_quantity",
                table: "QualityCheckDetail",
                newName: "check_quantity");

            migrationBuilder.AlterColumn<Guid>(
                name: "quality_check_id",
                table: "QualityCheckDetail",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_slot_code",
                table: "QualityCheckDetail",
                nullable: true,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheckDetail_inventory_slot_code",
                table: "QualityCheckDetail",
                column: "inventory_slot_code");

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheckDetail_SlotInfo_inventory_slot_code",
                table: "QualityCheckDetail",
                column: "inventory_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheckDetail_QualityCheck_quality_check_id",
                table: "QualityCheckDetail",
                column: "quality_check_id",
                principalTable: "QualityCheck",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheckDetail_SlotInfo_inventory_slot_code",
                table: "QualityCheckDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheckDetail_QualityCheck_quality_check_id",
                table: "QualityCheckDetail");

            migrationBuilder.DropIndex(
                name: "IX_QualityCheckDetail_inventory_slot_code",
                table: "QualityCheckDetail");

            migrationBuilder.RenameColumn(
                name: "check_quantity",
                table: "QualityCheckDetail",
                newName: "stock_check_quantity");

            migrationBuilder.AlterColumn<Guid>(
                name: "quality_check_id",
                table: "QualityCheckDetail",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<string>(
                name: "inventory_slot_code",
                table: "QualityCheckDetail",
                nullable: true,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "check_bill_code",
                table: "QualityCheckDetail",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "inventory_product_date",
                table: "QualityCheckDetail",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "inventory_product_lineid",
                table: "QualityCheckDetail",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "inventory_status",
                table: "QualityCheckDetail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "inventory_stock_status",
                table: "QualityCheckDetail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheckDetail_QualityCheck_quality_check_id",
                table: "QualityCheckDetail",
                column: "quality_check_id",
                principalTable: "QualityCheck",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
