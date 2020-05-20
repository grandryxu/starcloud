using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editForCompanyId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            //migrationBuilder.DropForeignKey(
            //    name: "FK_SlotSize_CompanyInfo_size_company_id",
            //    table: "SlotSize");

            //migrationBuilder.DropForeignKey(
            //    name: "FK_SlotSize_WarehouseInfo_size_warehouse_id",
            //    table: "SlotSize");

            //migrationBuilder.DropIndex(
            //    name: "IX_SlotSize_size_company_id",
            //    table: "SlotSize");

            //migrationBuilder.DropIndex(
            //    name: "IX_SlotSize_size_warehouse_id",
            //    table: "SlotSize");

            //migrationBuilder.DropColumn(
            //    name: "size_company_id",
            //    table: "SlotSize");

            //migrationBuilder.DropColumn(
            //    name: "size_warehouse_id",
            //    table: "SlotSize");

            migrationBuilder.AddColumn<Guid>(
                name: "main_company_id",
                table: "TaskMainInfo",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "check_company_id",
                table: "QualityCheck",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "inventory_company_id",
                table: "InventoryInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_QualityCheck_check_company_id",
                table: "QualityCheck",
                column: "check_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo",
                column: "inventory_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_QualityCheck_CompanyInfo_check_company_id",
                table: "QualityCheck",
                column: "check_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo",
                column: "main_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_InventoryInfo_CompanyInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_QualityCheck_CompanyInfo_check_company_id",
                table: "QualityCheck");

            migrationBuilder.DropForeignKey(
                name: "FK_TaskMainInfo_CompanyInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_TaskMainInfo_main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropIndex(
                name: "IX_QualityCheck_check_company_id",
                table: "QualityCheck");

            migrationBuilder.DropIndex(
                name: "IX_InventoryInfo_inventory_company_id",
                table: "InventoryInfo");

            migrationBuilder.DropColumn(
                name: "main_company_id",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "check_company_id",
                table: "QualityCheck");

            migrationBuilder.DropColumn(
                name: "inventory_company_id",
                table: "InventoryInfo");

            //migrationBuilder.AddColumn<Guid>(
            //    name: "size_company_id",
            //    table: "SlotSize",
            //    nullable: true);

            //migrationBuilder.AddColumn<Guid>(
            //    name: "size_warehouse_id",
            //    table: "SlotSize",
            //    nullable: true);

            //migrationBuilder.CreateIndex(
            //    name: "IX_SlotSize_size_company_id",
            //    table: "SlotSize",
            //    column: "size_company_id");

            //migrationBuilder.CreateIndex(
            //    name: "IX_SlotSize_size_warehouse_id",
            //    table: "SlotSize",
            //    column: "size_warehouse_id");

            //migrationBuilder.AddForeignKey(
            //    name: "FK_SlotSize_CompanyInfo_size_company_id",
            //    table: "SlotSize",
            //    column: "size_company_id",
            //    principalTable: "CompanyInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);

            //migrationBuilder.AddForeignKey(
            //    name: "FK_SlotSize_WarehouseInfo_size_warehouse_id",
            //    table: "SlotSize",
            //    column: "size_warehouse_id",
            //    principalTable: "WarehouseInfo",
            //    principalColumn: "Id",
            //    onDelete: ReferentialAction.Restrict);
        }
    }
}
