using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delAlarmArea : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Alarm_CompanyInfo_company_id",
                table: "Alarm");

            migrationBuilder.DropForeignKey(
                name: "FK_Alarm_GoodsInfo_goods_id",
                table: "Alarm");

            migrationBuilder.DropForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_GoodsInfo_expstock_goods_id",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_SlotInfo_expstock_slot_code",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_WarehouseInfo_expstock_warehouse_id",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_CompanyInfo_impstock_company_id",
                table: "ImportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_GoodsInfo_impstock_goods_id",
                table: "ImportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_WarehouseInfo_impstock_warehouse_id",
                table: "ImportStock");

            migrationBuilder.DropIndex(
                name: "IX_ImportStock_impstock_company_id",
                table: "ImportStock");

            migrationBuilder.DropIndex(
                name: "IX_Alarm_company_id",
                table: "Alarm");

            migrationBuilder.DropColumn(
                name: "impstock_company_id",
                table: "ImportStock");

            migrationBuilder.DropColumn(
                name: "company_id",
                table: "Alarm");

            migrationBuilder.AlterColumn<Guid>(
                name: "impstock_warehouse_id",
                table: "ImportStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "impstock_goods_id",
                table: "ImportStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_warehouse_id",
                table: "ExportStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_slot_code",
                table: "ExportStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_goods_id",
                table: "ExportStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "area_warehouse_id",
                table: "AreaInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "goods_id",
                table: "Alarm",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Alarm_GoodsInfo_goods_id",
                table: "Alarm",
                column: "goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo",
                column: "area_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_GoodsInfo_expstock_goods_id",
                table: "ExportStock",
                column: "expstock_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_SlotInfo_expstock_slot_code",
                table: "ExportStock",
                column: "expstock_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_WarehouseInfo_expstock_warehouse_id",
                table: "ExportStock",
                column: "expstock_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_GoodsInfo_impstock_goods_id",
                table: "ImportStock",
                column: "impstock_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_WarehouseInfo_impstock_warehouse_id",
                table: "ImportStock",
                column: "impstock_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Alarm_GoodsInfo_goods_id",
                table: "Alarm");

            migrationBuilder.DropForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_GoodsInfo_expstock_goods_id",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_SlotInfo_expstock_slot_code",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportStock_WarehouseInfo_expstock_warehouse_id",
                table: "ExportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_GoodsInfo_impstock_goods_id",
                table: "ImportStock");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportStock_WarehouseInfo_impstock_warehouse_id",
                table: "ImportStock");

            migrationBuilder.AlterColumn<Guid>(
                name: "impstock_warehouse_id",
                table: "ImportStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "impstock_goods_id",
                table: "ImportStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "impstock_company_id",
                table: "ImportStock",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_warehouse_id",
                table: "ExportStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_slot_code",
                table: "ExportStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "expstock_goods_id",
                table: "ExportStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "area_warehouse_id",
                table: "AreaInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "goods_id",
                table: "Alarm",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "company_id",
                table: "Alarm",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_ImportStock_impstock_company_id",
                table: "ImportStock",
                column: "impstock_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_Alarm_company_id",
                table: "Alarm",
                column: "company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_Alarm_CompanyInfo_company_id",
                table: "Alarm",
                column: "company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Alarm_GoodsInfo_goods_id",
                table: "Alarm",
                column: "goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AreaInfo_WarehouseInfo_area_warehouse_id",
                table: "AreaInfo",
                column: "area_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_GoodsInfo_expstock_goods_id",
                table: "ExportStock",
                column: "expstock_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_SlotInfo_expstock_slot_code",
                table: "ExportStock",
                column: "expstock_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportStock_WarehouseInfo_expstock_warehouse_id",
                table: "ExportStock",
                column: "expstock_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_CompanyInfo_impstock_company_id",
                table: "ImportStock",
                column: "impstock_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_GoodsInfo_impstock_goods_id",
                table: "ImportStock",
                column: "impstock_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportStock_WarehouseInfo_impstock_warehouse_id",
                table: "ImportStock",
                column: "impstock_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
