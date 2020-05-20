using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class xxx : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
            //    table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_InventoryInfo_PortInfo_inventory_port_id",
            //    table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
            //    table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_RowInfo_AreaInfo_row_area_id",
            //    table: "RowInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_RowInfo_SlotSize_row_size_id",
            //    table: "RowInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
            //    table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
            //    table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_WarehouseStock_CompanyInfo_warehouse_company_id",
            //    table: "WarehouseStock");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock");

            //migrationBuilder.DropIndex(
            //    name: "IX_WarehouseStock_warehouse_company_id",
            //    table: "WarehouseStock");

            //migrationBuilder.DropIndex(
            //    name: "IX_TaskMainInfo_main_company_id",
            //    table: "TaskMainInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_RowInfo_row_area_id",
            //    table: "RowInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_RowInfo_row_size_id",
            //    table: "RowInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_RowInfo_row_tunnel_id",
            //    table: "RowInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_InventoryInfo_inventory_company_id",
            //    table: "InventoryInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_InventoryInfo_inventory_port_id",
            //    table: "InventoryInfo");

            //migrationBuilder.DropIndex(
            //    name: "IX_InventoryInfo_inventory_warehouse_id",
            //    table: "InventoryInfo");

            //migrationBuilder.DropColumn(
            //    name: "warehouse_company_id",
            //    table: "WarehouseStock");

            //migrationBuilder.DropColumn(
            //    name: "main_company_id",
            //    table: "TaskMainInfo");

            //migrationBuilder.DropColumn(
            //    name: "row_area_id",
            //    table: "RowInfo");

            //migrationBuilder.DropColumn(
            //    name: "row_size_id",
            //    table: "RowInfo");

            //migrationBuilder.DropColumn(
            //    name: "row_tunnel_id",
            //    table: "RowInfo");

            //migrationBuilder.DropColumn(
            //    name: "inventory_company_id",
            //    table: "InventoryInfo");

            //migrationBuilder.DropColumn(
            //    name: "inventory_datetime",
            //    table: "InventoryInfo");

            //migrationBuilder.DropColumn(
            //    name: "inventory_port_id",
            //    table: "InventoryInfo");

            //migrationBuilder.DropColumn(
            //    name: "inventory_warehouse_id",
            //    table: "InventoryInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_id",
                table: "WarehouseStock",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "main_slot_code",
                table: "TaskMainInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "slot_company_id",
            //    table: "SlotInfo",
            //    nullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "slot_tunnel_id",
            //    table: "SlotInfo",
            //    nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "port_company_id",
            //    table: "PortInfo",
            //    nullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "platform_company_id",
            //    table: "PlatFormInfo",
            //    nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_quality_status",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_goods_id",
                table: "InventoryInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "DepartmentInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "area_company_id",
            //    table: "AreaInfo",
            //    nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "AbpUsers",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            //migrationBuilder.CreateTable(
            //    name: "AGVTask",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        agv_id = table.Column<string>(nullable: true),
            //        agv_no = table.Column<int>(nullable: false),
            //        agv_priority = table.Column<int>(nullable: false),
            //        agv_port_id = table.Column<string>(nullable: true),
            //        agv_platform_id = table.Column<string>(nullable: true),
            //        agv_port_id2 = table.Column<string>(nullable: true),
            //        agv_stock_code = table.Column<string>(nullable: true),
            //        agv_malfunction = table.Column<string>(nullable: true),
            //        agv_execute_flag = table.Column<int>(nullable: false),
            //        agv_manual_flag = table.Column<int>(nullable: false),
            //        agv_creat_uid = table.Column<string>(nullable: true),
            //        agv_creat_datetime = table.Column<DateTime>(nullable: false),
            //        agv_modify_uid = table.Column<string>(nullable: true),
            //        agv_modify_datetime = table.Column<string>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_AGVTask", x => x.Id);
            //    });

            //migrationBuilder.CreateTable(
            //    name: "ExportBillSyncLog",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        expbill_id = table.Column<Guid>(nullable: true),
            //        expbill_info = table.Column<string>(nullable: true),
            //        expbill_creat_datetime = table.Column<DateTime>(nullable: false),
            //        expbill_result = table.Column<string>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_ExportBillSyncLog", x => x.Id);
            //    });

            //migrationBuilder.CreateTable(
            //    name: "FlatBankTask",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        flat_no = table.Column<int>(nullable: false),
            //        flat_priority = table.Column<int>(nullable: false),
            //        flat_mode = table.Column<int>(nullable: false),
            //        flat_stock_code = table.Column<string>(nullable: true),
            //        flat_execute_flag = table.Column<int>(nullable: false),
            //        flat_manual_flag = table.Column<int>(nullable: false),
            //        flat_malfunction = table.Column<string>(nullable: true),
            //        flat_slot_code = table.Column<Guid>(nullable: false),
            //        flat_inslot_code = table.Column<Guid>(nullable: true),
            //        flat_port_id = table.Column<Guid>(nullable: true),
            //        flat_platform_id = table.Column<Guid>(nullable: true),
            //        flat_port_id2 = table.Column<Guid>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_FlatBankTask", x => x.Id);
            //        table.ForeignKey(
            //            name: "FK_FlatBankTask_SlotInfo_flat_inslot_code",
            //            column: x => x.flat_inslot_code,
            //            principalTable: "SlotInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_FlatBankTask_PlatFormInfo_flat_platform_id",
            //            column: x => x.flat_platform_id,
            //            principalTable: "PlatFormInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_FlatBankTask_PortInfo_flat_port_id",
            //            column: x => x.flat_port_id,
            //            principalTable: "PortInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_FlatBankTask_PortInfo_flat_port_id2",
            //            column: x => x.flat_port_id2,
            //            principalTable: "PortInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_FlatBankTask_SlotInfo_flat_slot_code",
            //            column: x => x.flat_slot_code,
            //            principalTable: "SlotInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Cascade);
            //    });

            //migrationBuilder.CreateTable(
            //    name: "ImportApplyLog",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        import_id = table.Column<Guid>(nullable: true),
            //        import_info = table.Column<string>(nullable: true),
            //        import_creat_datetime = table.Column<DateTime>(nullable: false),
            //        import_result = table.Column<string>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_ImportApplyLog", x => x.Id);
            //    });

            //migrationBuilder.CreateTable(
            //    name: "PickingTask",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        main_no = table.Column<string>(nullable: true),
            //        main_priority = table.Column<int>(nullable: false),
            //        main_mode = table.Column<int>(nullable: false),
            //        main_stock_code = table.Column<string>(nullable: true),
            //        main_malfunction = table.Column<string>(nullable: true),
            //        main_execute_flag = table.Column<int>(nullable: false),
            //        main_manual_flag = table.Column<int>(nullable: false),
            //        main_slot_code = table.Column<Guid>(nullable: false),
            //        main_inslot_code = table.Column<Guid>(nullable: true),
            //        main_port_id = table.Column<Guid>(nullable: true),
            //        main_platform_id = table.Column<Guid>(nullable: true),
            //        main_port_id2 = table.Column<Guid>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_PickingTask", x => x.Id);
            //        table.ForeignKey(
            //            name: "FK_PickingTask_SlotInfo_main_inslot_code",
            //            column: x => x.main_inslot_code,
            //            principalTable: "SlotInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_PickingTask_PlatFormInfo_main_platform_id",
            //            column: x => x.main_platform_id,
            //            principalTable: "PlatFormInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_PickingTask_PortInfo_main_port_id",
            //            column: x => x.main_port_id,
            //            principalTable: "PortInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_PickingTask_PortInfo_main_port_id2",
            //            column: x => x.main_port_id2,
            //            principalTable: "PortInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Restrict);
            //        table.ForeignKey(
            //            name: "FK_PickingTask_SlotInfo_main_slot_code",
            //            column: x => x.main_slot_code,
            //            principalTable: "SlotInfo",
            //            principalColumn: "Id",
            //            onDelete: ReferentialAction.Cascade);
            //    });

            //migrationBuilder.CreateTable(
            //    name: "RGVTask",
            //    columns: table => new
            //    {
            //        Id = table.Column<Guid>(nullable: false),
            //        CreationTime = table.Column<DateTime>(nullable: false),
            //        CreatorUserId = table.Column<long>(nullable: true),
            //        LastModificationTime = table.Column<DateTime>(nullable: true),
            //        LastModifierUserId = table.Column<long>(nullable: true),
            //        IsDeleted = table.Column<bool>(nullable: false),
            //        DeleterUserId = table.Column<long>(nullable: true),
            //        DeletionTime = table.Column<DateTime>(nullable: true),
            //        rgv_id = table.Column<string>(nullable: true),
            //        rgv_no = table.Column<int>(nullable: false),
            //        rgv_priority = table.Column<int>(nullable: false),
            //        rgv_port_id = table.Column<string>(nullable: true),
            //        rgv_platform_id = table.Column<string>(nullable: true),
            //        rgv_port_id2 = table.Column<string>(nullable: true),
            //        rgv_stock_code = table.Column<string>(nullable: true),
            //        rgv_malfunction = table.Column<string>(nullable: true),
            //        rgv_execute_flag = table.Column<int>(nullable: false),
            //        rgv_manual_flag = table.Column<int>(nullable: false),
            //        rgv_creat_uid = table.Column<string>(nullable: true),
            //        rgv_creat_datetime = table.Column<DateTime>(nullable: false),
            //        rgv_modify_uid = table.Column<string>(nullable: true),
            //        rgv_modify_datetime = table.Column<string>(nullable: true)
            //    },
            //    constraints: table =>
            //    {
            //        table.PrimaryKey("PK_RGVTask", x => x.Id);
            //    });

            //migrationBuilder.CreateIndex(
            //    name: "IX_SlotInfo_slot_company_id",
            //    table: "SlotInfo",
            //    column: "slot_company_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_SlotInfo_slot_tunnel_id",
            //    table: "SlotInfo",
            //    column: "slot_tunnel_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PortInfo_port_company_id",
            //    table: "PortInfo",
            //    column: "port_company_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PlatFormInfo_platform_company_id",
            //    table: "PlatFormInfo",
            //    column: "platform_company_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_AreaInfo_area_company_id",
            //    table: "AreaInfo",
            //    column: "area_company_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_FlatBankTask_flat_inslot_code",
            //    table: "FlatBankTask",
            //    column: "flat_inslot_code");

            //migrationBuilder.CreateIndex(
            //    name: "IX_FlatBankTask_flat_platform_id",
            //    table: "FlatBankTask",
            //    column: "flat_platform_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_FlatBankTask_flat_port_id",
            //    table: "FlatBankTask",
            //    column: "flat_port_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_FlatBankTask_flat_port_id2",
            //    table: "FlatBankTask",
            //    column: "flat_port_id2");

            //migrationBuilder.CreateIndex(
            //    name: "IX_FlatBankTask_flat_slot_code",
            //    table: "FlatBankTask",
            //    column: "flat_slot_code");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PickingTask_main_inslot_code",
            //    table: "PickingTask",
            //    column: "main_inslot_code");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PickingTask_main_platform_id",
            //    table: "PickingTask",
            //    column: "main_platform_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PickingTask_main_port_id",
            //    table: "PickingTask",
            //    column: "main_port_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PickingTask_main_port_id2",
            //    table: "PickingTask",
            //    column: "main_port_id2");

            //migrationBuilder.CreateIndex(
            //    name: "IX_PickingTask_main_slot_code",
            //    table: "PickingTask",
            //    column: "main_slot_code");

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_AreaInfo_CompanyInfo_area_company_id",
            //    table: "AreaInfo",
            //    column: "area_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo",
                column: "inventory_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo",
                column: "inventory_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_PlatFormInfo_CompanyInfo_platform_company_id",
            //    table: "PlatFormInfo",
            //    column: "platform_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_PortInfo_CompanyInfo_port_company_id",
            //    table: "PortInfo",
            //    column: "port_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_SlotInfo_CompanyInfo_slot_company_id",
            //    table: "SlotInfo",
            //    column: "slot_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_SlotInfo_TunnelInfo_slot_tunnel_id",
            //    table: "SlotInfo",
            //    column: "slot_tunnel_id",
            //    principalTable: "TunnelInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo",
                column: "warehouse_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock",
                column: "warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_AreaInfo_CompanyInfo_area_company_id",
                table: "AreaInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_PlatFormInfo_CompanyInfo_platform_company_id",
                table: "PlatFormInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_PortInfo_CompanyInfo_port_company_id",
                table: "PortInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_SlotInfo_CompanyInfo_slot_company_id",
                table: "SlotInfo");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_SlotInfo_TunnelInfo_slot_tunnel_id",
            //    table: "SlotInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock");

            migrationBuilder.DropTable(
                name: "AGVTask");

            migrationBuilder.DropTable(
                name: "ExportBillSyncLog");

            migrationBuilder.DropTable(
                name: "FlatBankTask");

            migrationBuilder.DropTable(
                name: "ImportApplyLog");

            migrationBuilder.DropTable(
                name: "PickingTask");

            migrationBuilder.DropTable(
                name: "RGVTask");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_company_id",
                table: "SlotInfo");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_tunnel_id",
                table: "SlotInfo");

            migrationBuilder.DropIndex(
                name: "IX_PortInfo_port_company_id",
                table: "PortInfo");

            migrationBuilder.DropIndex(
                name: "IX_PlatFormInfo_platform_company_id",
                table: "PlatFormInfo");

            migrationBuilder.DropIndex(
                name: "IX_AreaInfo_area_company_id",
                table: "AreaInfo");

            migrationBuilder.DropColumn(
                name: "slot_company_id",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "slot_tunnel_id",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "port_company_id",
                table: "PortInfo");

            migrationBuilder.DropColumn(
                name: "platform_company_id",
                table: "PlatFormInfo");

            migrationBuilder.DropColumn(
                name: "area_company_id",
                table: "AreaInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_id",
                table: "WarehouseStock",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseStock",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "warehouse_company_id",
                table: "WarehouseInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "main_slot_code",
                table: "TaskMainInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "main_company_id",
                table: "TaskMainInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "row_area_id",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "row_size_id",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "row_tunnel_id",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_quality_status",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "inventory_goods_id",
                table: "InventoryInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "inventory_company_id",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "inventory_datetime",
                table: "InventoryInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<Guid>(
                name: "inventory_port_id",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "inventory_warehouse_id",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "DepartmentInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "AbpUsers",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.CreateIndex(
                name: "IX_WarehouseStock_warehouse_company_id",
                table: "WarehouseStock",
                column: "warehouse_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_area_id",
                table: "RowInfo",
                column: "row_area_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_size_id",
                table: "RowInfo",
                column: "row_size_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_tunnel_id",
                table: "RowInfo",
                column: "row_tunnel_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_port_id",
                table: "InventoryInfo",
                column: "inventory_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_warehouse_id",
                table: "InventoryInfo",
                column: "inventory_warehouse_id");

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
            //    table: "InventoryInfo",
            //    column: "inventory_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_GoodsInfo_inventory_goods_id",
                table: "InventoryInfo",
                column: "inventory_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_InventoryInfo_PortInfo_inventory_port_id",
            //    table: "InventoryInfo",
            //    column: "inventory_port_id",
            //    principalTable: "PortInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_QualityInfo_inventory_quality_status",
                table: "InventoryInfo",
                column: "inventory_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_InventoryInfo_WarehouseInfo_inventory_warehouse_id",
            //    table: "InventoryInfo",
            //    column: "inventory_warehouse_id",
            //    principalTable: "WarehouseInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_RowInfo_AreaInfo_row_area_id",
            //    table: "RowInfo",
            //    column: "row_area_id",
            //    principalTable: "AreaInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_RowInfo_SlotSize_row_size_id",
            //    table: "RowInfo",
            //    column: "row_size_id",
            //    principalTable: "SlotSize",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
            //    table: "RowInfo",
            //    column: "row_tunnel_id",
            //    principalTable: "TunnelInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
            //    table: "TaskMainInfo",
            //    column: "main_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseInfo_CompanyInfo_warehouse_company_id",
                table: "WarehouseInfo",
                column: "warehouse_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_WarehouseStock_CompanyInfo_warehouse_company_id",
            //    table: "WarehouseStock",
            //    column: "warehouse_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_WarehouseStock_WarehouseInfo_warehouse_id",
                table: "WarehouseStock",
                column: "warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
