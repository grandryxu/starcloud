using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editBodyHeadId : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportBillbody_ImportBillhead_impbody_imphead_id",
                table: "ImportBillbody");

            migrationBuilder.RenameColumn(
                name: "impbody_imphead_id",
                table: "ImportBillbody",
                newName: "impbody_head_id");

            migrationBuilder.RenameIndex(
                name: "IX_ImportBillbody_impbody_imphead_id",
                table: "ImportBillbody",
                newName: "IX_ImportBillbody_impbody_head_id");

            migrationBuilder.RenameColumn(
                name: "expbody_imphead_id",
                table: "ExportBillbody",
                newName: "expbody_head_id");

            migrationBuilder.RenameIndex(
                name: "IX_ExportBillbody_expbody_imphead_id",
                table: "ExportBillbody",
                newName: "IX_ExportBillbody_expbody_head_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_head_id",
                table: "ExportBillbody",
                column: "expbody_head_id",
                principalTable: "ExportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportBillbody_ImportBillhead_impbody_head_id",
                table: "ImportBillbody",
                column: "impbody_head_id",
                principalTable: "ImportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_head_id",
                table: "ExportBillbody");

            migrationBuilder.DropForeignKey(
                name: "FK_ImportBillbody_ImportBillhead_impbody_head_id",
                table: "ImportBillbody");

            migrationBuilder.RenameColumn(
                name: "impbody_head_id",
                table: "ImportBillbody",
                newName: "impbody_imphead_id");

            migrationBuilder.RenameIndex(
                name: "IX_ImportBillbody_impbody_head_id",
                table: "ImportBillbody",
                newName: "IX_ImportBillbody_impbody_imphead_id");

            migrationBuilder.RenameColumn(
                name: "expbody_head_id",
                table: "ExportBillbody",
                newName: "expbody_imphead_id");

            migrationBuilder.RenameIndex(
                name: "IX_ExportBillbody_expbody_head_id",
                table: "ExportBillbody",
                newName: "IX_ExportBillbody_expbody_imphead_id");

            migrationBuilder.AddForeignKey(
                name: "FK_ExportBillbody_ExportBillhead_expbody_imphead_id",
                table: "ExportBillbody",
                column: "expbody_imphead_id",
                principalTable: "ExportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_ImportBillbody_ImportBillhead_impbody_imphead_id",
                table: "ImportBillbody",
                column: "impbody_imphead_id",
                principalTable: "ImportBillhead",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
