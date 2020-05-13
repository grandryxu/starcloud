using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class addgoods_company_id : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<Guid>(
                name: "goods_company_id",
                table: "GoodsInfo",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_GoodsInfo_goods_company_id",
                table: "GoodsInfo",
                column: "goods_company_id");

            migrationBuilder.AddForeignKey(
                name: "FK_GoodsInfo_CompanyInfo_goods_company_id",
                table: "GoodsInfo",
                column: "goods_company_id",
                principalTable: "CompanyInfo",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GoodsInfo_CompanyInfo_goods_company_id",
                table: "GoodsInfo");

            migrationBuilder.DropIndex(
                name: "IX_GoodsInfo_goods_company_id",
                table: "GoodsInfo");

            migrationBuilder.DropColumn(
                name: "goods_company_id",
                table: "GoodsInfo");
        }
    }
}
