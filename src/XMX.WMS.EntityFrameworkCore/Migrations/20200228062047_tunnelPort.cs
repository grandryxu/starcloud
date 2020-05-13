using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class tunnelPort : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "TunnelPort",
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
                    tunnelPort_creat_uid = table.Column<string>(nullable: true),
                    tunnelPort_creat_datetime = table.Column<DateTime>(nullable: false),
                    tunnelPort_modify_uid = table.Column<string>(nullable: true),
                    tunnelPort_modify_datetime = table.Column<DateTime>(nullable: false),
                    tunnelPort_is_enable = table.Column<int>(nullable: false),
                    tunnelPort_is_delete = table.Column<int>(nullable: false),
                    tunnelPort_company_id = table.Column<Guid>(nullable: true),
                    tunnelPort_tunnel_id = table.Column<Guid>(nullable: true),
                    tunnelPort_port_id = table.Column<Guid>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TunnelPort", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TunnelPort_CompanyInfo_tunnelPort_company_id",
                        column: x => x.tunnelPort_company_id,
                        principalTable: "CompanyInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_TunnelPort_PortInfo_tunnelPort_port_id",
                        column: x => x.tunnelPort_port_id,
                        principalTable: "PortInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                    table.ForeignKey(
                        name: "FK_TunnelPort_TunnelInfo_tunnelPort_tunnel_id",
                        column: x => x.tunnelPort_tunnel_id,
                        principalTable: "TunnelInfo",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateIndex(
                name: "IX_TunnelPort_tunnelPort_company_id",
                table: "TunnelPort",
                column: "tunnelPort_company_id");

            migrationBuilder.CreateIndex(
                name: "IX_TunnelPort_tunnelPort_port_id",
                table: "TunnelPort",
                column: "tunnelPort_port_id");

            migrationBuilder.CreateIndex(
                name: "IX_TunnelPort_tunnelPort_tunnel_id",
                table: "TunnelPort",
                column: "tunnelPort_tunnel_id");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "TunnelPort");
        }
    }
}
