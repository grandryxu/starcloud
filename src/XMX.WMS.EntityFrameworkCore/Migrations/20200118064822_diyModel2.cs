using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class diyModel2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpRoles_IOTWxConfig_CompanyId",
                table: "AbpRoles");

            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_IOTWxConfig_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_IOTWxConfig_CompanyId",
                table: "DepartmentInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_IOTWxConfig_IOTWxConfig_ParentId",
                table: "IOTWxConfig");

            migrationBuilder.DropPrimaryKey(
                name: "PK_IOTWxConfig",
                table: "IOTWxConfig");

            migrationBuilder.RenameTable(
                name: "IOTWxConfig",
                newName: "CompanyInfo");

            migrationBuilder.RenameIndex(
                name: "IX_IOTWxConfig_ParentId",
                table: "CompanyInfo",
                newName: "IX_CompanyInfo_ParentId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_CompanyInfo",
                table: "CompanyInfo",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_AbpRoles_CompanyInfo_CompanyId",
                table: "AbpRoles",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_CompanyInfo_CompanyInfo_ParentId",
                table: "CompanyInfo",
                column: "ParentId",
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
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpRoles_CompanyInfo_CompanyId",
                table: "AbpRoles");

            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_CompanyInfo_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_CompanyInfo_CompanyInfo_ParentId",
                table: "CompanyInfo");

            migrationBuilder.DropForeignKey(
                name: "FK_DepartmentInfo_CompanyInfo_CompanyId",
                table: "DepartmentInfo");

            migrationBuilder.DropPrimaryKey(
                name: "PK_CompanyInfo",
                table: "CompanyInfo");

            migrationBuilder.RenameTable(
                name: "CompanyInfo",
                newName: "IOTWxConfig");

            migrationBuilder.RenameIndex(
                name: "IX_CompanyInfo_ParentId",
                table: "IOTWxConfig",
                newName: "IX_IOTWxConfig_ParentId");

            migrationBuilder.AddPrimaryKey(
                name: "PK_IOTWxConfig",
                table: "IOTWxConfig",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_AbpRoles_IOTWxConfig_CompanyId",
                table: "AbpRoles",
                column: "CompanyId",
                principalTable: "IOTWxConfig",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_AbpUsers_IOTWxConfig_CompanyId",
                table: "AbpUsers",
                column: "CompanyId",
                principalTable: "IOTWxConfig",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_DepartmentInfo_IOTWxConfig_CompanyId",
                table: "DepartmentInfo",
                column: "CompanyId",
                principalTable: "IOTWxConfig",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_IOTWxConfig_IOTWxConfig_ParentId",
                table: "IOTWxConfig",
                column: "ParentId",
                principalTable: "IOTWxConfig",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
