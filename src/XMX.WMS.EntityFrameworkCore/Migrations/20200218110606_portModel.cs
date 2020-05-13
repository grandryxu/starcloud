using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class portModel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "PortInfo",
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
                    port_code = table.Column<string>(nullable: true),
                    port_name = table.Column<string>(nullable: true),
                    port_type = table.Column<int>(nullable: false),
                    port_creat_uid = table.Column<string>(nullable: true),
                    port_creat_datetime = table.Column<DateTime>(nullable: false),
                    port_modify_uid = table.Column<string>(nullable: true),
                    port_modify_datetime = table.Column<DateTime>(nullable: false),
                    port_is_enable = table.Column<int>(nullable: false),
                    port_is_delete = table.Column<int>(nullable: false),
                    port_warehouse_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PortInfo", x => x.Id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "PortInfo");
        }
    }
}
