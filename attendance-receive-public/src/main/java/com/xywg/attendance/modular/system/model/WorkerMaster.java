/*
package com.xywg.attendance.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

*/
/**
 * <p>
 * 工人实名基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 *//*

@TableName("buss_worker_master")
@Data
public class WorkerMaster extends Model<WorkerMaster> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    */
/**
     * 头像
     *//*

    @TableField("head_image")
    private String headImage;
    */
/**
     * 工人姓名
     *//*

    @TableField("worker_name")
    private String workerName;

    */
/**
     * 手机号码
     *//*

    @TableField("cell_phone")
    private String cellPhone;

    */
/**
     * 出生日期 身份证上获取的出生日期，格式：1990-04-08
     *//*

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    */
/**
     * 参见工种字典表中工种编号
     *//*

    @TableField("work_type_code")
    private String workTypeCode;

    */
/**
     * 证件类型 引用附录字典表
     *//*

    @TableField("id_card_type")
    private Integer idCardType;
    */
/**
     * 证件编号
     *//*

    @TableField("id_card_number")
    private String idCardNumber;


    */
/**
     * 性别
     *//*

    private Integer gender;
    */
/**
     * 民族
     *//*

    private String nation;

    */
/**
     * 籍贯
     *//*

    @TableField("birth_place_code")
    private String birthPlaceCode;
    */
/**
     * 地址
     *//*

    private String address;
    */
/**
     * 政治面貌
     *//*

    @TableField("politics_type")
    private Integer politicsType;
    */
/**
     * 文化程度,
     *//*

    @TableField("culture_level_type")
    private Integer cultureLevelType;
    */
/**
     * 是否加入工会 0=未加入,
     * 1=已加入
     *//*

    @TableField(value="is_joined")
    private Integer isJoined;
    */
/**
     * 加入工会时间
     *//*

    @TableField("joined_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinedTime;

    */
/**
     * 是否有重大病史
     *//*

    @TableField("has_bad_medical_history")
    private Integer hasBadMedicalHistory;
    */
/**
     * 开始工作日期 精确到月即可
     *//*

    @TableField("work_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    */
/**
     * 紧急联系人姓名
     *//*

    @TableField("urgent_contract_name")
    private String urgentContractName;
    */
/**
     * 紧急联系人联系电话
     *//*

    @TableField("urgent_contract_cellphone")
    private String urgentContractCellphone;
    */
/**
     * 是否人脸录入
     *//*

    @TableField("is_face")
    private Integer isFace;
    */
/**
     * 是否认证  1 已认证  0未认证  3黑名单
     *//*

    @TableField("is_auth")
    private Integer isAuth;

    */
/**
     * 用户图像压缩
     *//*


    @TableField("icon_image")
    private String iconImage;
    */
/**
     * 身份证照片
     *//*

    @TableField("id_image")
    private String idImage;
    */
/**
     * 录入人脸
     *//*

    private String face;
    */
/**
     * 添加人
     *//*

    //@TableField("create_date")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    */
/**
     * 添加时间
     *//*

    //@TableField("create_user")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    */
/**
     * 修改时间
     *//*

    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    */
/**
     * 修改人
     *//*

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;
    */
/**
     * 备注
     *//*

    private String note;
    */
/**
     * 是否删除 1：是 0:否
     *//*

    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private String image;

    @TableField(exist = false)
    private String aliId;
    
    */
/**
     * 项目编号
     *//*

    @TableField(exist = false)
    private String projectCode;
    */
/**
     * 组织机构代码
     *//*

    @TableField(exist = false)
    private String organizationCode;
    
    */
/**
     * 班组
     *//*

    @TableField(exist = false)
    private String teamSysNo;
    
    */
/**
     * 进退场状态
     *//*

    @TableField(exist = false)
    private Integer joinStatus;
    
    */
/**
     * 是否黑名单
     *//*

    @TableField(exist = false)
    private Integer isBlack;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
*/
