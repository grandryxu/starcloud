using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class delBillCustomDepart : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_BillInfo_CompanyInfo_bill_company_id",
                table: "BillInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_BillInfo_EncodingRule_bill_rule_id",
                table: "BillInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_CustomInfo_CompanyInfo_custom_company_id",
                table: "CustomInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_CustomInfo_CustomTypeInfo_custom_type_id",
                table: "CustomInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_CustomTypeInfo_CompanyInfo_customtype_company_id",
                table: "CustomTypeInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_EncodingRule_CompanyInfo_code_company_id",
                table: "EncodingRule");

            migrationBuilder.DropIndex(
                name: "IX_CustomInfo_custom_company_id",
                table: "CustomInfo");

            migrationBuilder.DropIndex(
                name: "IX_BillInfo_bill_rule_id",
                table: "BillInfo");

            migrationBuilder.DropColumn(
                name: "custom_company_id",
                table: "CustomInfo");

            migrationBuilder.DropColumn(
                name: "bill_rule_id",
                table: "BillInfo");

            migrationBuilder.AlterColumn<Guid>(
                name: "code_company_id",
                table: "EncodingRule",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "DepartmentInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "customtype_company_id",
                table: "CustomTypeInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "custom_type_id",
                table: "CustomInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "bill_company_id",
                table: "BillInfo",
                nullable: false,
                oldClrType: typeof(Guid),
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_BillInfo_CompanyInfo_bill_company_id",
                table: "BillInfo",
                column: "bill_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_CustomInfo_CustomTypeInfo_custom_type_id",
                table: "CustomInfo",
                column: "custom_type_id",
                principalTable: "CustomTypeInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_CustomTypeInfo_CompanyInfo_customtype_company_id",
                table: "CustomTypeInfo",
                column: "customtype_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_EncodingRule_CompanyInfo_code_company_id",
                table: "EncodingRule",
                column: "code_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_BillInfo_CompanyInfo_bill_company_id",
                table: "BillInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_CustomInfo_CustomTypeInfo_custom_type_id",
                table: "CustomInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_CustomTypeInfo_CompanyInfo_customtype_company_id",
                table: "CustomTypeInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_EncodingRule_CompanyInfo_code_company_id",
                table: "EncodingRule");

            migrationBuilder.AlterColumn<Guid>(
                name: "code_company_id",
                table: "EncodingRule",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "CompanyId",
                table: "DepartmentInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "customtype_company_id",
                table: "CustomTypeInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AlterColumn<Guid>(
                name: "custom_type_id",
                table: "CustomInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "custom_company_id",
                table: "CustomInfo",
                nullable: true);

            migrationBuilder.AlterColumn<Guid>(
                name: "bill_company_id",
                table: "BillInfo",
                nullable: true,
                oldClrType: typeof(Guid));

            migrationBuilder.AddColumn<Guid>(
                name: "bill_rule_id",
                table: "BillInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_CustomInfo_custom_company_id",
                table: "CustomInfo",
                column: "custom_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_BillInfo_bill_rule_id",
                table: "BillInfo",
                column: "bill_rule_id");

            migrationBuilder.AddForeignKey(
                name: "FK_BillInfo_CompanyInfo_bill_company_id",
                table: "BillInfo",
                column: "bill_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_BillInfo_EncodingRule_bill_rule_id",
                table: "BillInfo",
                column: "bill_rule_id",
                principalTable: "EncodingRule",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_CustomInfo_CompanyInfo_custom_company_id",
                table: "CustomInfo",
                column: "custom_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_CustomInfo_CustomTypeInfo_custom_type_id",
                table: "CustomInfo",
                column: "custom_type_id",
                principalTable: "CustomTypeInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_CustomTypeInfo_CompanyInfo_customtype_company_id",
                table: "CustomTypeInfo",
                column: "customtype_company_id",
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

            migrationBuilder.AddForeignKey(
                name: "FK_EncodingRule_CompanyInfo_code_company_id",
                table: "EncodingRule",
                column: "code_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
