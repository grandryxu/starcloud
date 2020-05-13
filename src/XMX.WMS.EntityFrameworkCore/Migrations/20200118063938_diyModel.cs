using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class diyModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "CompanyId",
                table: "AbpUsers",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "DepartmentId",
                table: "AbpUsers",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "IsEnable",
                table: "AbpUsers",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "LoginId",
                table: "AbpUsers",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Remark",
                table: "AbpUsers",
                nullable: true);

            migrationBuilder.AddColumn<Guid>(
                name: "CompanyId",
                table: "AbpRoles",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "IsEnable",
                table: "AbpRoles",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "Remark",
                table: "AbpRoles",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "IOTWxConfig",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    CreatorUserId = table.Column<long>(nullable: true),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    Name = table.Column<string>(nullable: true),
                    ShortName = table.Column<string>(nullable: true),
                    ManagerName = table.Column<string>(nullable: true),
                    Remark = table.Column<string>(nullable: true),
                    IsEnable = table.Column<int>(nullable: false),
                    ParentId = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_IOTWxConfig", x => x.Id);
                    table.ForeignKey(
                        name: "FK_IOTWxConfig_IOTWxConfig_ParentId",
                        column: x => x.ParentId,
                        principalTable: "IOTWxConfig",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "DepartmentInfo",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    CreationTime = table.Column<DateTime>(nullable: false),
                    CreatorUserId = table.Column<long>(nullable: true),
                    LastModificationTime = table.Column<DateTime>(nullable: true),
                    LastModifierUserId = table.Column<long>(nullable: true),
                    IsDeleted = table.Column<bool>(nullable: false),
                    DeleterUserId = table.Column<long>(nullable: true),
                    DeletionTime = table.Column<DateTime>(nullable: true),
                    Name = table.Column<string>(nullable: true),
                    ManagerName = table.Column<string>(nullable: true),
                    Remark = table.Column<string>(nullable: true),
                    IsEnable = table.Column<int>(nullable: false),
                    CompanyId = table.Column<Guid>(nullable: true),
                    DepartmentId = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_DepartmentInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_DepartmentInfo_IOTWxConfig_CompanyId",
                        column: x => x.CompanyId,
                        principalTable: "IOTWxConfig",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_DepartmentInfo_DepartmentInfo_DepartmentId",
                        column: x => x.DepartmentId,
                        principalTable: "DepartmentInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_AbpUsers_CompanyId",
                table: "AbpUsers",
                column: "CompanyId");

            migrationBuilder.CreateIndex(
                name: "IX_AbpUsers_DepartmentId",
                table: "AbpUsers",
                column: "DepartmentId");

            migrationBuilder.CreateIndex(
                name: "IX_AbpRoles_CompanyId",
                table: "AbpRoles",
                column: "CompanyId");

            migrationBuilder.CreateIndex(
                name: "IX_DepartmentInfo_CompanyId",
                table: "DepartmentInfo",
                column: "CompanyId");

            migrationBuilder.CreateIndex(
                name: "IX_DepartmentInfo_DepartmentId",
                table: "DepartmentInfo",
                column: "DepartmentId");

            migrationBuilder.CreateIndex(
                name: "IX_IOTWxConfig_ParentId",
                table: "IOTWxConfig",
                column: "ParentId");

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
                name: "FK_AbpUsers_DepartmentInfo_DepartmentId",
                table: "AbpUsers",
                column: "DepartmentId",
                principalTable: "DepartmentInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_AbpRoles_IOTWxConfig_CompanyId",
                table: "AbpRoles");

            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_IOTWxConfig_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropForeignKey(
                name: "FK_AbpUsers_DepartmentInfo_DepartmentId",
                table: "AbpUsers");

            migrationBuilder.DropTable(
                name: "DepartmentInfo");

            migrationBuilder.DropTable(
                name: "IOTWxConfig");

            migrationBuilder.DropIndex(
                name: "IX_AbpUsers_CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropIndex(
                name: "IX_AbpUsers_DepartmentId",
                table: "AbpUsers");

            migrationBuilder.DropIndex(
                name: "IX_AbpRoles_CompanyId",
                table: "AbpRoles");

            migrationBuilder.DropColumn(
                name: "CompanyId",
                table: "AbpUsers");

            migrationBuilder.DropColumn(
                name: "DepartmentId",
                table: "AbpUsers");

            migrationBuilder.DropColumn(
                name: "IsEnable",
                table: "AbpUsers");

            migrationBuilder.DropColumn(
                name: "LoginId",
                table: "AbpUsers");

            migrationBuilder.DropColumn(
                name: "Remark",
                table: "AbpUsers");

            migrationBuilder.DropColumn(
                name: "CompanyId",
                table: "AbpRoles");

            migrationBuilder.DropColumn(
                name: "IsEnable",
                table: "AbpRoles");

            migrationBuilder.DropColumn(
                name: "Remark",
                table: "AbpRoles");
        }
    }
}
