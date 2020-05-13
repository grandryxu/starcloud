using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class movemodel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "MoveModelMenu",
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
                    menu_url = table.Column<string>(nullable: true),
                    menu_type = table.Column<int>(nullable: false),
                    menu_order = table.Column<int>(nullable: false),
                    menu_function_name = table.Column<string>(nullable: true),
                    menu_icon = table.Column<string>(nullable: true),
                    menu_is_enable = table.Column<int>(nullable: false),
                    menu_remark = table.Column<string>(nullable: true),
                    menu_parent_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MoveModelMenu", x => x.Id);
                    table.ForeignKey(
                        name: "FK_MoveModelMenu_MoveModelMenu_menu_parent_id",
                        column: x => x.menu_parent_id,
                        principalTable: "MoveModelMenu",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_MoveModelMenu_menu_parent_id",
                table: "MoveModelMenu",
                column: "menu_parent_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "MoveModelMenu");
        }
    }
}
