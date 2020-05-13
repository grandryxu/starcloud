package com.xywg.admin.modular.longxin.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * <p>
 * 企业录入表
 * </p>
 *
 * @author xuehao.shi
 * @since 2019-07-10
 */
@TableName("buss_material")
@Data
public class BussMaterial extends Model<BussMaterial> {
	private static Logger log = LoggerFactory.getLogger(BussMaterial.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 企业id
     */
    @TableField("bussiness_id")
    private Long bussinessId;
    /**
     * 企业材料地址
     */
    @TableField("material_url")
    private String materialUrl;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    
	@Override
	protected Serializable pkVal() {
		 return this.id;
	}
    
	public BussMaterial(Long bussinessId,String materialUrl){
		this.bussinessId = bussinessId;
		this.materialUrl = materialUrl;
	}

}
