package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tcw on 2019/7/10.
 */
@Data
public class TenderResultBean  extends Model<TenderResultBean> {

    /**
     * 招标文件id
     */
    private String id ;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 提交人id
     */
    private String subUserId;

    /**
     * 项目地点
     */
    private String projectAddress;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * createUser
     * @return
     */
    private String createUser;

    /**
     *
     */
    private String deadline;

    /**
     *
     */
    private String createTime;
    
    private String tenderStatus;
    
    private String tenderManStatus;
    
    private String tenderCode;

    public String getCreateTime(){
        if(createTime!=null){
            return this.createTime.substring(0,19);
        }else{
            return this.createTime;
        }

    }

    public String getDeadline(){
        if(deadline!=null){
            return this.deadline.substring(0,19);
        }else{
            return this.deadline;
        }

    }



    public String getStartTime(){
        if(startTime!=null){
            return this.startTime.substring(0,19);
        }else{
            return this.startTime;
        }

    }
    public String getEndTime(){
        if(endTime!=null){
            return this.endTime.substring(0,19);
        }else{
            return this.endTime;
        }
    }


    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 中标单位数量
     */
    private String bidNumb;

    /**
     *
     */
    private String tenderContext;

    private String tenderResume;

    private String tenderFiles;


    private String fileName;
    private String resume;

    private String status;

    private String priceTemp;

    private List<LxTenderFile>  files;


    private String tenderType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
