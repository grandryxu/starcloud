using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class tunnel : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "row_in_state",
                table: "RowInfo");

            migrationBuilder.DropColumn(
                name: "row_out_state",
                table: "RowInfo");

            migrationBuilder.CreateTable(
                name: "TunnelInfo",
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
                    tunnel_name = table.Column<string>(nullable: true),
                    tunnel_in_state = table.Column<int>(nullable: false),
                    tunnel_out_state = table.Column<int>(nullable: false),
                    tunnel_creat_uid = table.Column<string>(nullable: true),
                    tunnel_creat_datetime = table.Column<DateTime>(nullable: false),
                    tunnel_modify_uid = table.Column<string>(nullable: true),
                    tunnel_modify_datetime = table.Column<DateTime>(nullable: false),
                    tunnel_is_enable = table.Column<int>(nullable: false),
                    tunnel_is_delete = table.Column<int>(nullable: false),
                    tunnel_company_id = table.Column<Guid>(nullable: true),
                    slot_row_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TunnelInfo", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TunnelInfo_RowInfo_slot_row_id",
                        column: x => x.slot_row_id,
                        principalTable: "RowInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_TunnelInfo_CompanyInfo_tunnel_company_id",
                        column: x => x.tunnel_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_RowInfo_row_tunnel_id",
                table: "RowInfo",
                column: "row_tunnel_id");

            migrationBuilder.CreateIndex(
                name: "IX_TunnelInfo_slot_row_id",
                table: "TunnelInfo",
                column: "slot_row_id");

            migrationBuilder.CreateIndex(
                name: "IX_TunnelInfo_tunnel_company_id",
                table: "TunnelInfo",
                column: "tunnel_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo",
                column: "row_tunnel_id",
                principalTable: "TunnelInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_RowInfo_TunnelInfo_row_tunnel_id",
                table: "RowInfo");

            migrationBuilder.DropTable(
                name: "TunnelInfo");

            migrationBuilder.DropIndex(
                name: "IX_RowInfo_row_tunnel_id",
                table: "RowInfo");

            migrationBuilder.AddColumn<int>(
                name: "row_in_state",
                table: "RowInfo",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "row_out_state",
                table: "RowInfo",
                nullable: false,
                defaultValue: 0);
        }
    }
}
