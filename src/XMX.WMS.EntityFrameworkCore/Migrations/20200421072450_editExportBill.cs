using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace XMX.WMS.Migrations
{
    public partial class editExportBill : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "warehousing_creat_datetime",
                table: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "warehousing_creat_uid",
                table: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "warehousing_is_delete",
                table: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "warehousing_modify_datetime",
                table: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "warehousing_modify_uid",
                table: "StrategyWarehousing");

            migrationBuilder.DropColumn(
                name: "monitor_creat_datetime",
                table: "StrategyMonitor");

            migrationBuilder.DropColumn(
                name: "monitor_creat_uid",
                table: "StrategyMonitor");

            migrationBuilder.DropColumn(
                name: "monitor_is_delete",
                table: "StrategyMonitor");

            migrationBuilder.DropColumn(
                name: "monitor_modify_datetime",
                table: "StrategyMonitor");

            migrationBuilder.DropColumn(
                name: "monitor_modify_uid",
                table: "StrategyMonitor");

            migrationBuilder.DropColumn(
                name: "distribution_creat_datetime",
                table: "StrategyDistribution");

            migrationBuilder.DropColumn(
                name: "distribution_creat_uid",
                table: "StrategyDistribution");

            migrationBuilder.DropColumn(
                name: "distribution_is_delete",
                table: "StrategyDistribution");

            migrationBuilder.DropColumn(
                name: "distribution_modify_datetime",
                table: "StrategyDistribution");

            migrationBuilder.DropColumn(
                name: "distribution_modify_uid",
                table: "StrategyDistribution");

            migrationBuilder.DropColumn(
                name: "exporder_creat_datetime",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_creat_uid",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_is_delete",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_modify_datetime",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exporder_modify_uid",
                table: "ExportOrder");

            migrationBuilder.DropColumn(
                name: "exphead_creat_datetime",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "exphead_creat_uid",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "exphead_is_delete",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "exphead_modify_datetime",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "exphead_modify_uid",
                table: "ExportBillhead");

            migrationBuilder.DropColumn(
                name: "expbody_creat_datetime",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "expbody_creat_uid",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "expbody_is_delete",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "expbody_modify_datetime",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "expbody_modify_uid",
                table: "ExportBillbody");

            migrationBuilder.DropColumn(
                name: "code_creat_datetime",
                table: "EncodingRule");

            migrationBuilder.DropColumn(
                name: "code_creat_uid",
                table: "EncodingRule");

            migrationBuilder.DropColumn(
                name: "code_is_delete",
                table: "EncodingRule");

            migrationBuilder.DropColumn(
                name: "code_modify_datetime",
                table: "EncodingRule");

            migrationBuilder.DropColumn(
                name: "code_modify_uid",
                table: "EncodingRule");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "warehousing_creat_datetime",
                table: "StrategyWarehousing",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "warehousing_creat_uid",
                table: "StrategyWarehousing",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "warehousing_is_delete",
                table: "StrategyWarehousing",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "warehousing_modify_datetime",
                table: "StrategyWarehousing",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "warehousing_modify_uid",
                table: "StrategyWarehousing",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "monitor_creat_datetime",
                table: "StrategyMonitor",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "monitor_creat_uid",
                table: "StrategyMonitor",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "monitor_is_delete",
                table: "StrategyMonitor",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "monitor_modify_datetime",
                table: "StrategyMonitor",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "monitor_modify_uid",
                table: "StrategyMonitor",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "distribution_creat_datetime",
                table: "StrategyDistribution",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "distribution_creat_uid",
                table: "StrategyDistribution",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "distribution_is_delete",
                table: "StrategyDistribution",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "distribution_modify_datetime",
                table: "StrategyDistribution",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "distribution_modify_uid",
                table: "StrategyDistribution",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "exporder_creat_datetime",
                table: "ExportOrder",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "exporder_creat_uid",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "exporder_is_delete",
                table: "ExportOrder",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "exporder_modify_datetime",
                table: "ExportOrder",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "exporder_modify_uid",
                table: "ExportOrder",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "exphead_creat_datetime",
                table: "ExportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "exphead_creat_uid",
                table: "ExportBillhead",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "exphead_is_delete",
                table: "ExportBillhead",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "exphead_modify_datetime",
                table: "ExportBillhead",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "exphead_modify_uid",
                table: "ExportBillhead",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "expbody_creat_datetime",
                table: "ExportBillbody",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "expbody_creat_uid",
                table: "ExportBillbody",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "expbody_is_delete",
                table: "ExportBillbody",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "expbody_modify_datetime",
                table: "ExportBillbody",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "expbody_modify_uid",
                table: "ExportBillbody",
                nullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "code_creat_datetime",
                table: "EncodingRule",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "code_creat_uid",
                table: "EncodingRule",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "code_is_delete",
                table: "EncodingRule",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "code_modify_datetime",
                table: "EncodingRule",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<string>(
                name: "code_modify_uid",
                table: "EncodingRule",
                nullable: true);
        }
    }
}
