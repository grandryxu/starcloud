package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tcw on 2019/7/9.
 */


@Data
public class LxTender extends Model<LxTender> {

    /**
     * 招标文件id
     */
    private String id ;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 文件名
     */
    private String fileName;


    /**
     * 文件描述
     */
    private String resume;


    /**
     * 审核状态 0 审核中，1 审核完成,2 驳回
     */
    private String status;
    
    /**
     * 价格模板
     */
    private String priceTemp;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 截止日期
     */
    private Date deadline;


    /**
     * 预计招标日期
     */
    private Date startTime;
    
    /**
     * 流程状态
     */
    private String flowStatus;


    /**
     * createUser
     * @return
     */

    private String createUser;
    
    private String tenderCode;


    private String projectName;

    /**
     * 0邀标 1 公开招标
     */
    private String tenderType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
