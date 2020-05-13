package com.xywg.admin.modular.smz.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/5 9:11
 */
@Data
public class PersonMo {
	
	/**
	 * 主键
	 */
	private Long id;
    /**
     * 班组名称
     */
    private String className;

    /**
     * 公司id 0
     */
    private Long comId;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 人id
     */
    private Long personId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 加入状态
     */
    private Integer joinStatus;
    /**
     * 地址
     */
    private String address;

    /**
     * 性别
     */
    private Integer gender;



    /**
     * 暂住地址
     */
    private String currAddress;
    /**
     * 工种
     */
    private String kindCode;


    /**
     * 登录名称
     */
    private String loginName;
    /**
     * 图片
     */
    private String image;

    /**
     * 登录密码
     *
     */
    private String password;

    /**
     * 工人合同开始时间
     */
    private Date startDate;

    /**
     * 工人合同结束时间
     */
    private Date endDate;

    /**
     * 工人进场时间
     */
    private Date joinInDate;

    /**
     * 工人退场时间
     */
    private Date joinOutDate;
    //证件类型
    private int idCardType;
    //民族
    private String nation;
    //籍贯
    private String birthPlaceCode;
    //政治面貌
    private int politicsType;
    //是否加入工会
    private  int unJoined;
    //加入工会时间
    private Date joinedTime;
    //文化程度
    private int cultureLevelType;
    //是否有重大病史
    private int noBadMedicalHistory;
    //紧急联系人
    private String urgentContractName;
    //紧急联系人电话
    private String urgentContractCellphone;
    //开始工作日期
    private Date workDate;
    //工人住宿类型
    private int workAccommodationType;
    
    private String workTypeName;
    
    //中如项目id
    private String zrId;
    
    //劳务通实名制id
    private Long lwtRelationId;
}
