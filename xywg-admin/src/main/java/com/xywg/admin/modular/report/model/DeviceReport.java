package com.xywg.admin.modular.report.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 考勤统计表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
@TableName("buss_device_report")
public class DeviceReport extends Model<DeviceReport> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 企业名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 证件类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 工人姓名
     */
    @TableField("worker_name")
    private String workerName;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 班组编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 班组名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 统计所在月份
     */
    private String time;
    /**
     * 总工时
     */
    @TableField("total_count")
    private Double totalCount;
    /**
     * 出勤天数
     */
    @TableField("total_date")
    private Integer totalDate;

    /**
     * 总工数量
     */
    @TableField("total_time")
    private Double totalTime;
    /**
     * 加班工时
     */
    @TableField("over_time")
    private Double overTime;

    private Double day01;
    private Double day02;
    private Double day03;
    private Double day04;
    private Double day05;
    private Double day06;
    private Double day07;
    private Double day08;
    private Double day09;
    private Double day10;
    private Double day11;
    private Double day12;
    private Double day13;
    private Double day14;
    private Double day15;
    private Double day16;
    private Double day17;
    private Double day18;
    private Double day19;
    private Double day20;
    private Double day21;
    private Double day22;
    private Double day23;
    private Double day24;
    private Double day25;
    private Double day26;
    private Double day27;
    private Double day28;
    private Double day29;
    private Double day30;
    private Double day31;

    private Double rain;  //雨天

    @TableField("fall_ill")
    private Double fallIll;  //病假

    @TableField("compassionate_leave")
    private Double compassionateLeave;  //事假

    private Double absent;  //缺席

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getFallIll() {
        return fallIll;
    }

    public void setFallIll(Double fallIll) {
        this.fallIll = fallIll;
    }

    public Double getCompassionateLeave() {
        return compassionateLeave;
    }

    public void setCompassionateLeave(Double compassionateLeave) {
        this.compassionateLeave = compassionateLeave;
    }

    public Double getAbsent() {
        return absent;
    }

    public void setAbsent(Double absent) {
        this.absent = absent;
    }

    public Double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Double totalTime) {
        this.totalTime = totalTime;
    }

    public Double getOverTime() {
        return overTime;
    }

    public void setOverTime(Double overTime) {
        this.overTime = overTime;
    }

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

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
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

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
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
        ", organizationCode=" + organizationCode +
        ", companyName=" + companyName +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", workerName=" + workerName +
        ", projectName=" + projectName +
        ", teamSysNo=" + teamSysNo +
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
