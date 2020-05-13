using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class equipmentloginfo : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            /**
            migrationBuilder.DropColumn(
                name: "warehouse_is_delete",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "warehouse_modify_uid",
                table: "HistoryTaskMainInfo",
                newName: "main_modify_uid");

            migrationBuilder.RenameColumn(
                name: "warehouse_modify_datetime",
                table: "HistoryTaskMainInfo",
                newName: "main_modify_datetime");

            migrationBuilder.RenameColumn(
                name: "warehouse_creat_uid",
                table: "HistoryTaskMainInfo",
                newName: "main_creat_uid");

            migrationBuilder.RenameColumn(
                name: "warehouse_creat_datetime",
                table: "HistoryTaskMainInfo",
                newName: "main_creat_datetime");

            migrationBuilder.AddColumn<int>(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0);
    **/
            migrationBuilder.CreateTable(
                name: "EquipmentLogInfo",
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
                    equipment_code = table.Column<string>(nullable: true),
                    equipment_name = table.Column<string>(nullable: true),
                    equipment_log_content = table.Column<string>(nullable: true),
                    equipment_log_remark = table.Column<string>(nullable: true),
                    opt_user_name = table.Column<string>(nullable: true),
                    equipment_type = table.Column<int>(nullable: false),
                    equipment_log_type = table.Column<int>(nullable: false),
                    equipment_execution_state = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_EquipmentLogInfo", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            
            migrationBuilder.DropTable(
                name: "EquipmentLogInfo");
            /*
            migrationBuilder.DropColumn(
                name: "main_is_delete",
                table: "HistoryTaskMainInfo");

            migrationBuilder.RenameColumn(
                name: "main_modify_uid",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_modify_uid");

            migrationBuilder.RenameColumn(
                name: "main_modify_datetime",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_modify_datetime");

            migrationBuilder.RenameColumn(
                name: "main_creat_uid",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_creat_uid");

            migrationBuilder.RenameColumn(
                name: "main_creat_datetime",
                table: "HistoryTaskMainInfo",
                newName: "warehouse_creat_datetime");

            migrationBuilder.AddColumn<int>(
                name: "warehouse_is_delete",
                table: "HistoryTaskMainInfo",
                nullable: false,
                defaultValue: 0);
                */
        }
    }
}
