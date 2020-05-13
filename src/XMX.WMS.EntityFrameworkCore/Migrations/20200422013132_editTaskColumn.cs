using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editTaskColumn : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "main_creat_datetime",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_creat_uid",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_is_delete",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_modify_datetime",
                table: "TaskMainInfo");

            migrationBuilder.DropColumn(
                name: "main_modify_uid",
                table: "TaskMainInfo");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "main_creat_datetime",
                table: "TaskMainInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "main_creat_uid",
                table: "TaskMainInfo",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "main_is_delete",
                table: "TaskMainInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "main_modify_datetime",
                table: "TaskMainInfo",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "main_modify_uid",
                table: "TaskMainInfo",
                nullable: true);
        }
    }
}
