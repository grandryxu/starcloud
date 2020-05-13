using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delTableColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "warehouse_creat_datetime",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "warehouse_creat_uid",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "warehouse_is_delete",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "warehouse_modify_datetime",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "warehouse_modify_uid",
                table: "WarehouseInfo");

            migrationBuilder.DropColumn(
                name: "unit_creat_datetime",
                table: "UnitInfo");

            migrationBuilder.DropColumn(
                name: "unit_creat_uid",
                table: "UnitInfo");

            migrationBuilder.DropColumn(
                name: "unit_is_delete",
                table: "UnitInfo");

            migrationBuilder.DropColumn(
                name: "unit_modify_datetime",
                table: "UnitInfo");

            migrationBuilder.DropColumn(
                name: "unit_modify_uid",
                table: "UnitInfo");

            migrationBuilder.DropColumn(
                name: "tunnelPort_creat_datetime",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnelPort_creat_uid",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnelPort_is_delete",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnelPort_is_enable",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnelPort_modify_datetime",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnelPort_modify_uid",
                table: "TunnelPort");

            migrationBuilder.DropColumn(
                name: "tunnel_creat_datetime",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "tunnel_creat_uid",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "tunnel_is_delete",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "tunnel_modify_datetime",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "tunnel_modify_uid",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "size_creat_datetime",
                table: "SlotSize");

            migrationBuilder.DropColumn(
                name: "size_creat_uid",
                table: "SlotSize");

            migrationBuilder.DropColumn(
                name: "size_is_delete",
                table: "SlotSize");

            migrationBuilder.DropColumn(
                name: "size_modify_datetime",
                table: "SlotSize");

            migrationBuilder.DropColumn(
                name: "size_modify_uid",
                table: "SlotSize");

            migrationBuilder.DropColumn(
                name: "slot_creat_datetime",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_creat_uid",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_is_delete",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_modify_datetime",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_modify_uid",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "row_creat_datetime",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "row_creat_uid",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "row_is_delete",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "row_modify_datetime",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "row_modify_uid",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "goods_company_id",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "quality_creat_datetime",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "quality_creat_uid",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "quality_is_delete",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "quality_modify_datetime",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "quality_modify_uid",
                table: "QualityInfo");

            migrationBuilder.DropColumn(
                name: "port_creat_datetime",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "port_creat_uid",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "port_is_delete",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "port_modify_datetime",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "port_modify_uid",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "platform_creat_datetime",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "platform_creat_uid",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "platform_is_delete",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "platform_modify_datetime",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "platform_modify_uid",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "pack_creat_datetime",
                table: "PackInfo");

            migrationBuilder.DropColumn(
                name: "pack_creat_uid",
                table: "PackInfo");

            migrationBuilder.DropColumn(
                name: "pack_is_delete",
                table: "PackInfo");

            migrationBuilder.DropColumn(
                name: "pack_modify_datetime",
                table: "PackInfo");

            migrationBuilder.DropColumn(
                name: "pack_modify_uid",
                table: "PackInfo");

            migrationBuilder.DropColumn(
                name: "inventory_creat_datetime",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "inventory_creat_uid",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "inventory_is_delete",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "inventory_modify_datetime",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "inventory_modify_uid",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "goods_creat_datetime",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_creat_uid",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_is_delete",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_modify_datetime",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_modify_uid",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_pack",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "confirm_creat_datetime",
                table: "ExportConfirm");

            migrationBuilder.DropColumn(
                name: "confirm_creat_uid",
                table: "ExportConfirm");

            migrationBuilder.DropColumn(
                name: "confirm_is_delete",
                table: "ExportConfirm");

            migrationBuilder.DropColumn(
                name: "confirm_modify_datetime",
                table: "ExportConfirm");

            migrationBuilder.DropColumn(
                name: "confirm_modify_uid",
                table: "ExportConfirm");

            migrationBuilder.DropColumn(
                name: "customtype_creat_datetime",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "customtype_creat_uid",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "customtype_is_delete",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "customtype_modify_datetime",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "customtype_modify_uid",
                table: "CustomTypeInfo");

            migrationBuilder.DropColumn(
                name: "custom_creat_datetime",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_creat_uid",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_is_delete",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_modify_datetime",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "custom_modify_uid",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "bill_creat_datetime",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "bill_creat_uid",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "bill_is_delete",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "bill_modify_datetime",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "bill_modify_uid",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "area_creat_datetime",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_creat_uid",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_is_delete",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_modify_datetime",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "area_modify_uid",
                table: "AreaInfo");

            migrationBuilder.AddColumn<Guid>(
                name: "goods_pack_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_pack_id",
                table: "GoodsInfo",
                column: "goods_pack_id");

            migrationBuilder.CreateIndex(
                name: "IX_BillInfo_bill_rule_id",
                table: "BillInfo",
                column: "bill_rule_id");

            migrationBuilder.AddForeignKey(
                name: "FK_BillInfo_EncodingRule_bill_rule_id",
                table: "BillInfo",
                column: "bill_rule_id",
                principalTable: "EncodingRule",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_PackInfo_goods_pack_id",
                table: "GoodsInfo",
                column: "goods_pack_id",
                principalTable: "PackInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_BillInfo_EncodingRule_bill_rule_id",
                table: "BillInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_PackInfo_goods_pack_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_pack_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_BillInfo_bill_rule_id",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "goods_pack_id",
                table: "GoodsInfo");

            migrationBuilder.AddColumn<DateTime>(
                name: "warehouse_creat_datetime",
                table: "WarehouseInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "warehouse_creat_uid",
                table: "WarehouseInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "warehouse_is_delete",
                table: "WarehouseInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "warehouse_modify_datetime",
                table: "WarehouseInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "warehouse_modify_uid",
                table: "WarehouseInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "unit_creat_datetime",
                table: "UnitInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "unit_creat_uid",
                table: "UnitInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "unit_is_delete",
                table: "UnitInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "unit_modify_datetime",
                table: "UnitInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "unit_modify_uid",
                table: "UnitInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "tunnelPort_creat_datetime",
                table: "TunnelPort",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "tunnelPort_creat_uid",
                table: "TunnelPort",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tunnelPort_is_delete",
                table: "TunnelPort",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "tunnelPort_is_enable",
                table: "TunnelPort",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "tunnelPort_modify_datetime",
                table: "TunnelPort",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "tunnelPort_modify_uid",
                table: "TunnelPort",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "tunnel_creat_datetime",
                table: "TunnelInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "tunnel_creat_uid",
                table: "TunnelInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "tunnel_is_delete",
                table: "TunnelInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "tunnel_modify_datetime",
                table: "TunnelInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "tunnel_modify_uid",
                table: "TunnelInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "size_creat_datetime",
                table: "SlotSize",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "size_creat_uid",
                table: "SlotSize",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "size_is_delete",
                table: "SlotSize",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "size_modify_datetime",
                table: "SlotSize",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "size_modify_uid",
                table: "SlotSize",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "slot_creat_datetime",
                table: "SlotInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "slot_creat_uid",
                table: "SlotInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "slot_is_delete",
                table: "SlotInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "slot_modify_datetime",
                table: "SlotInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "slot_modify_uid",
                table: "SlotInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "row_creat_datetime",
                table: "RowInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "row_creat_uid",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "row_is_delete",
                table: "RowInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "row_modify_datetime",
                table: "RowInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "row_modify_uid",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "goods_company_id",
                table: "QualityInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "quality_creat_datetime",
                table: "QualityInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "quality_creat_uid",
                table: "QualityInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "quality_is_delete",
                table: "QualityInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "quality_modify_datetime",
                table: "QualityInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "quality_modify_uid",
                table: "QualityInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "port_creat_datetime",
                table: "PortInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "port_creat_uid",
                table: "PortInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "port_is_delete",
                table: "PortInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "port_modify_datetime",
                table: "PortInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "port_modify_uid",
                table: "PortInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "platform_creat_datetime",
                table: "PlatFormInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "platform_creat_uid",
                table: "PlatFormInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "platform_is_delete",
                table: "PlatFormInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "platform_modify_datetime",
                table: "PlatFormInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "platform_modify_uid",
                table: "PlatFormInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "pack_creat_datetime",
                table: "PackInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "pack_creat_uid",
                table: "PackInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "pack_is_delete",
                table: "PackInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "pack_modify_datetime",
                table: "PackInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "pack_modify_uid",
                table: "PackInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "inventory_creat_datetime",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "inventory_creat_uid",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "inventory_is_delete",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "inventory_modify_datetime",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "inventory_modify_uid",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "goods_creat_datetime",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "goods_creat_uid",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "goods_is_delete",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "goods_modify_datetime",
                table: "GoodsInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "goods_modify_uid",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "goods_pack",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "confirm_creat_datetime",
                table: "ExportConfirm",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "confirm_creat_uid",
                table: "ExportConfirm",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "confirm_is_delete",
                table: "ExportConfirm",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "confirm_modify_datetime",
                table: "ExportConfirm",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "confirm_modify_uid",
                table: "ExportConfirm",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "customtype_creat_datetime",
                table: "CustomTypeInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "customtype_creat_uid",
                table: "CustomTypeInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "customtype_is_delete",
                table: "CustomTypeInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "customtype_modify_datetime",
                table: "CustomTypeInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "customtype_modify_uid",
                table: "CustomTypeInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "custom_creat_datetime",
                table: "CustomInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "custom_creat_uid",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "custom_is_delete",
                table: "CustomInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "custom_modify_datetime",
                table: "CustomInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "custom_modify_uid",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "bill_creat_datetime",
                table: "BillInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "bill_creat_uid",
                table: "BillInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "bill_is_delete",
                table: "BillInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "bill_modify_datetime",
                table: "BillInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "bill_modify_uid",
                table: "BillInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "area_creat_datetime",
                table: "AreaInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "area_creat_uid",
                table: "AreaInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "area_is_delete",
                table: "AreaInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "area_modify_datetime",
                table: "AreaInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "area_modify_uid",
                table: "AreaInfo",
                nullable: true);
        }
    }
}
