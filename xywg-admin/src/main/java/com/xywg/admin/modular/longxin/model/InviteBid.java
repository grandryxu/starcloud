package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuehao.shi
 * 2019/07/11
 */


@Data
public class InviteBid extends Model<InviteBid> {

    private String pingbiao1;
    private String pingbiao2;

    private String pingbiao3;

    private String flag;
    /**
	 * 
	 */
	private static final long serialVersionUID = 6692188727805222947L;

	/**
     * 招标邀请id
     */
    private String id ;

    /**
     * 招标信息id
     */
    private String tenderId;
    
    /**
     * 招标信息name
     */
    private String tenderName;

    /**
     * 企业id
     */
    private String companyId;
    
    /**
     * 企业name
     */
    private String companyName;
    
    /**
     * 项目id
     */
    private String projectId;
    
    /**
     * 项目name
     */
    private String projectName;


    /**
     * 状态
     */
    private String status;
    
    /**
     * 流程状态
     */
    private String flowStatus;
    
    /**
     * 备注
     */
    private String remark;

    /**
             *竞标价格
     */
    private String bidPrice;
    
    /**
              * 注册资金
     */
    private BigDecimal registrationCapital;
    
    /**
     * 联系人
     */
    private String contactPeopleName;

    /**
 	* 联系人手机号码
 	*/
    private String contactPeopleCellPhone;
    
    /**
          *资质等级
     */
    private String qualifyLevel;
    
    private Date startTime;
    
    private Date deadline;
    
    /**
              * 中标单位地址
     */
    private String bidAddress;


    private Date  date;

    private Date createTime;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
