package com.xywg.admin.modular.report.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.core.excel.annotation.ExcelField;

import java.io.Serializable;

/**
 * <p>
 * 考勤导出
 * </p>
 *
 * @author zhuzhiwei
 * @since 2019-06-11
 */
public class DeviceReportExportDto extends Model<DeviceReportExportDto> {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String projectCode;
 
    @ExcelField(title = "证件编号", order = 3)
    private String idCardNumber;
    @ExcelField(title = "工人姓名", order = 4)
    private String workerName;
    @ExcelField(title = "项目名称", order = 5)
    private String projectName;

    @ExcelField(title = "班组名称", order = 7)
    private String teamName;
    @ExcelField(title = "统计所在月份", order =8)
    private String time;
    @ExcelField(title = "总共数", order =9)
    private String totalTime;
    @ExcelField(title = "总工时", order = 10)
    private Double totalCount;
    @ExcelField(title = "出勤天数", order = 11)
    private Integer totalDate;
    @ExcelField(title = "01", order = 12)
    private Double day01;
    @ExcelField(title = "02", order = 13)
    private Double day02;
    @ExcelField(title = "03", order = 14)
    private Double day03;
    @ExcelField(title = "04", order = 15)
    private Double day04;
    @ExcelField(title = "05", order = 16)
    private Double day05;
    @ExcelField(title = "06", order = 17)
    private Double day06;
    @ExcelField(title = "07", order = 18)
    private Double day07;
    @ExcelField(title = "08", order = 19)
    private Double day08;
    @ExcelField(title = "09", order = 20)
    private Double day09;
    @ExcelField(title = "10", order = 21)
    private Double day10;
    @ExcelField(title = "11", order = 22)
    private Double day11;
    @ExcelField(title = "12", order = 23)
    private Double day12;
    @ExcelField(title = "13", order = 24)
    private Double day13;
    @ExcelField(title = "14", order = 25)
    private Double day14;
    @ExcelField(title = "15", order = 26)
    private Double day15;
    @ExcelField(title = "16", order = 27)
    private Double day16;
    @ExcelField(title = "17", order = 28)
    private Double day17;
    @ExcelField(title = "18", order = 29)
    private Double day18;
    @ExcelField(title = "19", order = 30)
    private Double day19;
    @ExcelField(title = "20", order = 31)
    private Double day20;
    @ExcelField(title = "21", order = 32)
    private Double day21;
    @ExcelField(title = "22", order = 33)
    private Double day22;
    @ExcelField(title = "23", order = 34)
    private Double day23;
    @ExcelField(title = "24", order = 35)
    private Double day24;
    @ExcelField(title = "25", order =36)
    private Double day25;
    @ExcelField(title = "26", order = 37)
    private Double day26;
    @ExcelField(title = "27", order = 38)
    private Double day27;
    @ExcelField(title = "28", order = 39)
    private Double day28;
    @ExcelField(title = "29", order = 40)
    private Double day29;
    @ExcelField(title = "30", order = 41)
    private Double day30;
    @ExcelField(title = "31", order = 42)
    private Double day31;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }


    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Integer totalDate) {
        this.totalDate = totalDate;
    }

    public Double getDay01() {
        return day01;
    }

    public void setDay01(Double day01) {
        this.day01 = day01;
    }

    public Double getDay02() {
        return day02;
    }

    public void setDay02(Double day02) {
        this.day02 = day02;
    }

    public Double getDay03() {
        return day03;
    }

    public void setDay03(Double day03) {
        this.day03 = day03;
    }

    public Double getDay04() {
        return day04;
    }

    public void setDay04(Double day04) {
        this.day04 = day04;
    }

    public Double getDay05() {
        return day05;
    }

    public void setDay05(Double day05) {
        this.day05 = day05;
    }

    public Double getDay06() {
        return day06;
    }

    public void setDay06(Double day06) {
        this.day06 = day06;
    }

    public Double getDay07() {
        return day07;
    }

    public void setDay07(Double day07) {
        this.day07 = day07;
    }

    public Double getDay08() {
        return day08;
    }

    public void setDay08(Double day08) {
        this.day08 = day08;
    }

    public Double getDay09() {
        return day09;
    }

    public void setDay09(Double day09) {
        this.day09 = day09;
    }

    public Double getDay10() {
        return day10;
    }

    public void setDay10(Double day10) {
        this.day10 = day10;
    }

    public Double getDay11() {
        return day11;
    }

    public void setDay11(Double day11) {
        this.day11 = day11;
    }

    public Double getDay12() {
        return day12;
    }

    public void setDay12(Double day12) {
        this.day12 = day12;
    }

    public Double getDay13() {
        return day13;
    }

    public void setDay13(Double day13) {
        this.day13 = day13;
    }

    public Double getDay14() {
        return day14;
    }

    public void setDay14(Double day14) {
        this.day14 = day14;
    }

    public Double getDay15() {
        return day15;
    }

    public void setDay15(Double day15) {
        this.day15 = day15;
    }

    public Double getDay16() {
        return day16;
    }

    public void setDay16(Double day16) {
        this.day16 = day16;
    }

    public Double getDay17() {
        return day17;
    }

    public void setDay17(Double day17) {
        this.day17 = day17;
    }

    public Double getDay18() {
        return day18;
    }

    public void setDay18(Double day18) {
        this.day18 = day18;
    }

    public Double getDay19() {
        return day19;
    }

    public void setDay19(Double day19) {
        this.day19 = day19;
    }

    public Double getDay20() {
        return day20;
    }

    public void setDay20(Double day20) {
        this.day20 = day20;
    }

    public Double getDay21() {
        return day21;
    }

    public void setDay21(Double day21) {
        this.day21 = day21;
    }

    public Double getDay22() {
        return day22;
    }

    public void setDay22(Double day22) {
        this.day22 = day22;
    }

    public Double getDay23() {
        return day23;
    }

    public void setDay23(Double day23) {
        this.day23 = day23;
    }

    public Double getDay24() {
        return day24;
    }

    public void setDay24(Double day24) {
        this.day24 = day24;
    }

    public Double getDay25() {
        return day25;
    }

    public void setDay25(Double day25) {
        this.day25 = day25;
    }

    public Double getDay26() {
        return day26;
    }

    public void setDay26(Double day26) {
        this.day26 = day26;
    }

    public Double getDay27() {
        return day27;
    }

    public void setDay27(Double day27) {
        this.day27 = day27;
    }

    public Double getDay28() {
        return day28;
    }

    public void setDay28(Double day28) {
        this.day28 = day28;
    }

    public Double getDay29() {
        return day29;
    }

    public void setDay29(Double day29) {
        this.day29 = day29;
    }

    public Double getDay30() {
        return day30;
    }

    public void setDay30(Double day30) {
        this.day30 = day30;
    }

    public Double getDay31() {
        return day31;
    }

    public void setDay31(Double day31) {
        this.day31 = day31;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DeviceReport{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", idCardNumber=" + idCardNumber +
        ", workerName=" + workerName +
        ", projectName=" + projectName +
        ", teamName=" + teamName +
        ", time=" + time +
        ", totalCount=" + totalCount +
        ", totalDate=" + totalDate +
        ", day01=" + day01 +
        ", day02=" + day02 +
        ", day03=" + day03 +
        ", day04=" + day04 +
        ", day05=" + day05 +
        ", day06=" + day06 +
        ", day07=" + day07 +
        ", day08=" + day08 +
        ", day09=" + day09 +
        ", day10=" + day10 +
        ", day11=" + day11 +
        ", day12=" + day12 +
        ", day13=" + day13 +
        ", day14=" + day14 +
        ", day15=" + day15 +
        ", day16=" + day16 +
        ", day17=" + day17 +
        ", day18=" + day18 +
        ", day19=" + day19 +
        ", day20=" + day20 +
        ", day21=" + day21 +
        ", day22=" + day22 +
        ", day23=" + day23 +
        ", day24=" + day24 +
        ", day25=" + day25 +
        ", day26=" + day26 +
        ", day27=" + day27 +
        ", day28=" + day28 +
        ", day29=" + day29 +
        ", day30=" + day30 +
        ", day31=" + day31 +
        "}";
    }
}
