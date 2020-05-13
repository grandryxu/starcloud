using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delImpExpRowMany : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_CompanyInfo_expbody_company_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_GoodsInfo_expbody_goods_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_QualityInfo_expbody_quality_status",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillhead_BillInfo_exphead_bill_id",
                table: "ExportBillhead");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillhead_CompanyInfo_exphead_company_id",
                table: "ExportBillhead");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_CompanyInfo_exporder_company_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_GoodsInfo_exporder_goods_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_QualityInfo_exporder_quality_status",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_SlotInfo_exporder_slot_code",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_WarehouseInfo_exporder_warehouse_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_CompanyInfo_row_company_id",
                table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_WarehouseInfo_row_warehouse_id",
                table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_SlotInfo_WarehouseInfo_slot_warehouse_id",
                table: "SlotInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTasking_CompanyInfo_task_company_id",
                table: "StockTasking");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTasking_WarehouseInfo_task_warehouse_id",
                table: "StockTasking");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_StockTasking_stock_tasking_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_CompanyInfo_task_company_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_GoodsInfo_task_goods_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_TunnelInfo_CompanyInfo_tunnel_company_id",
                table: "TunnelInfo");

            migrationBuilder.DropIndex(
                name: "IX_TunnelInfo_tunnel_company_id",
                table: "TunnelInfo");

            migrationBuilder.DropIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_StockTaskingDetail_task_company_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropIndex(
                name: "IX_StockTasking_task_company_id",
                table: "StockTasking");

            migrationBuilder.DropIndex(
                name: "IX_SlotInfo_slot_warehouse_id",
                table: "SlotInfo");

            migrationBuilder.DropIndex(
                name: "IX_RowInfo_row_company_id",
                table: "RowInfo");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_exporder_company_id",
                table: "ExportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_exporder_goods_id",
                table: "ExportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_exporder_quality_status",
                table: "ExportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportOrder_exporder_warehouse_id",
                table: "ExportOrder");

            migrationBuilder.DropIndex(
                name: "IX_ExportBillhead_exphead_company_id",
                table: "ExportBillhead");

            migrationBuilder.DropIndex(
                name: "IX_ExportBillbody_expbody_company_id",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "tunnel_company_id",
                table: "TunnelInfo");

            migrationBuilder.DropColumn(
                name: "main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "task_company_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropColumn(
                name: "task_company_id",
                table: "StockTasking");

            migrationBuilder.DropColumn(
                name: "slot_warehouse_id",
                table: "SlotInfo");

            migrationBuilder.DropColumn(
                name: "row_company_id",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "exporder_company_id",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_goods_id",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_quality_status",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_warehouse_id",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exphead_company_id",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "expbody_company_id",
                table: "ExportBillbody");

            migrationBuilder.AlterColumn<Guid>(
                name: "main_slot_code",
                table: "TaskMainInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "task_slot_id",
                table: "StockTaskingDetail",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "task_goods_id",
                table: "StockTaskingDetail",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "stock_tasking_id",
                table: "StockTaskingDetail",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "task_warehouse_id",
                table: "StockTasking",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "row_warehouse_id",
                table: "RowInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "row_tunnel_id",
                table: "RowInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "exporder_slot_code",
                table: "ExportOrder",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "exporder_body_id",
                table: "ExportOrder",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "exphead_bill_id",
                table: "ExportBillhead",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_quality_status",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_imphead_id",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_goods_id",
                table: "ExportBillbody",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_GoodsInfo_expbody_goods_id",
                table: "ExportBillbody",
                column: "expbody_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody",
                column: "expbody_imphead_id",
                principalTable: "ExportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_QualityInfo_expbody_quality_status",
                table: "ExportBillbody",
                column: "expbody_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillhead_BillInfo_exphead_bill_id",
                table: "ExportBillhead",
                column: "exphead_bill_id",
                principalTable: "BillInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder",
                column: "exporder_body_id",
                principalTable: "ExportBillbody",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_SlotInfo_exporder_slot_code",
                table: "ExportOrder",
                column: "exporder_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo",
                column: "row_tunnel_id",
                principalTable: "TunnelInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_WarehouseInfo_row_warehouse_id",
                table: "RowInfo",
                column: "row_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTasking_WarehouseInfo_task_warehouse_id",
                table: "StockTasking",
                column: "task_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_StockTasking_stock_tasking_id",
                table: "StockTaskingDetail",
                column: "stock_tasking_id",
                principalTable: "StockTasking",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_GoodsInfo_task_goods_id",
                table: "StockTaskingDetail",
                column: "task_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail",
                column: "task_slot_id",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_GoodsInfo_expbody_goods_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_QualityInfo_expbody_quality_status",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillhead_BillInfo_exphead_bill_id",
                table: "ExportBillhead");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_ExportOrder_SlotInfo_exporder_slot_code",
                table: "ExportOrder");

            migrationBuilder.DropForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_WarehouseInfo_row_warehouse_id",
                table: "RowInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTasking_WarehouseInfo_task_warehouse_id",
                table: "StockTasking");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_StockTasking_stock_tasking_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_GoodsInfo_task_goods_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo");

            migrationBuilder.AddColumn<Guid>(
                name: "tunnel_company_id",
                table: "TunnelInfo",
                nullable: true);

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
                name: "task_slot_id",
                table: "StockTaskingDetail",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "task_goods_id",
                table: "StockTaskingDetail",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "stock_tasking_id",
                table: "StockTaskingDetail",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "task_company_id",
                table: "StockTaskingDetail",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "task_warehouse_id",
                table: "StockTasking",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "task_company_id",
                table: "StockTasking",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "stacker_warehouse_id",
                table: "StackerInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "slot_warehouse_id",
                table: "SlotInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "row_warehouse_id",
                table: "RowInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "row_tunnel_id",
                table: "RowInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "row_company_id",
                table: "RowInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "rgv_warehouse_id",
                table: "RGVInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "exporder_slot_code",
                table: "ExportOrder",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "exporder_body_id",
                table: "ExportOrder",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "exporder_company_id",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "exporder_goods_id",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "exporder_quality_status",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "exporder_warehouse_id",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "exphead_bill_id",
                table: "ExportBillhead",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "exphead_company_id",
                table: "ExportBillhead",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_quality_status",
                table: "ExportBillbody",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_imphead_id",
                table: "ExportBillbody",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "expbody_goods_id",
                table: "ExportBillbody",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "expbody_company_id",
                table: "ExportBillbody",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_TunnelInfo_tunnel_company_id",
                table: "TunnelInfo",
                column: "tunnel_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTaskingDetail_task_company_id",
                table: "StockTaskingDetail",
                column: "task_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_StockTasking_task_company_id",
                table: "StockTasking",
                column: "task_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_SlotInfo_slot_warehouse_id",
                table: "SlotInfo",
                column: "slot_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_company_id",
                table: "RowInfo",
                column: "row_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_company_id",
                table: "ExportOrder",
                column: "exporder_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_goods_id",
                table: "ExportOrder",
                column: "exporder_goods_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_quality_status",
                table: "ExportOrder",
                column: "exporder_quality_status");

            migrationBuilder.CreateIndex(
                name: "IX_ExportOrder_exporder_warehouse_id",
                table: "ExportOrder",
                column: "exporder_warehouse_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillhead_exphead_company_id",
                table: "ExportBillhead",
                column: "exphead_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_ExportBillbody_expbody_company_id",
                table: "ExportBillbody",
                column: "expbody_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_CompanyInfo_expbody_company_id",
                table: "ExportBillbody",
                column: "expbody_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_GoodsInfo_expbody_goods_id",
                table: "ExportBillbody",
                column: "expbody_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody",
                column: "expbody_imphead_id",
                principalTable: "ExportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_QualityInfo_expbody_quality_status",
                table: "ExportBillbody",
                column: "expbody_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillhead_BillInfo_exphead_bill_id",
                table: "ExportBillhead",
                column: "exphead_bill_id",
                principalTable: "BillInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillhead_CompanyInfo_exphead_company_id",
                table: "ExportBillhead",
                column: "exphead_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_ExportBillbody_exporder_body_id",
                table: "ExportOrder",
                column: "exporder_body_id",
                principalTable: "ExportBillbody",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_CompanyInfo_exporder_company_id",
                table: "ExportOrder",
                column: "exporder_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_GoodsInfo_exporder_goods_id",
                table: "ExportOrder",
                column: "exporder_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_QualityInfo_exporder_quality_status",
                table: "ExportOrder",
                column: "exporder_quality_status",
                principalTable: "QualityInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_SlotInfo_exporder_slot_code",
                table: "ExportOrder",
                column: "exporder_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ExportOrder_WarehouseInfo_exporder_warehouse_id",
                table: "ExportOrder",
                column: "exporder_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RGVInfo_WarehouseInfo_rgv_warehouse_id",
                table: "RGVInfo",
                column: "rgv_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_CompanyInfo_row_company_id",
                table: "RowInfo",
                column: "row_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo",
                column: "row_tunnel_id",
                principalTable: "TunnelInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_WarehouseInfo_row_warehouse_id",
                table: "RowInfo",
                column: "row_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_SlotInfo_WarehouseInfo_slot_warehouse_id",
                table: "SlotInfo",
                column: "slot_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StackerInfo_WarehouseInfo_stacker_warehouse_id",
                table: "StackerInfo",
                column: "stacker_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTasking_CompanyInfo_task_company_id",
                table: "StockTasking",
                column: "task_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTasking_WarehouseInfo_task_warehouse_id",
                table: "StockTasking",
                column: "task_warehouse_id",
                principalTable: "WarehouseInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_StockTasking_stock_tasking_id",
                table: "StockTaskingDetail",
                column: "stock_tasking_id",
                principalTable: "StockTasking",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_CompanyInfo_task_company_id",
                table: "StockTaskingDetail",
                column: "task_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_GoodsInfo_task_goods_id",
                table: "StockTaskingDetail",
                column: "task_goods_id",
                principalTable: "GoodsInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_StockTaskingDetail_SlotInfo_task_slot_id",
                table: "StockTaskingDetail",
                column: "task_slot_id",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_SlotInfo_main_slot_code",
                table: "TaskMainInfo",
                column: "main_slot_code",
                principalTable: "SlotInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TunnelInfo_CompanyInfo_tunnel_company_id",
                table: "TunnelInfo",
                column: "tunnel_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
