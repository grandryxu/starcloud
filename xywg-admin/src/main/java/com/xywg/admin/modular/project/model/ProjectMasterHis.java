package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 项目基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public class ProjectMasterHis extends Model<ProjectMasterHis> {
	private static Logger log = LoggerFactory.getLogger(ProjectMasterHis.class);
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     	* 项目名称
     */
    private String projectName;
    
    /**
     	* 招标code
     */
    private String tenderCode;
    /**
   	 * 中标时间
     */
    private Date date;
    
    /**
     	* 中标金额
     */
    private String bidPrice;
    
    
    
    public Object getDate() {
		return date == null?"":f.format(date);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProjectName() {
		return projectName == null ? "":projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTenderCode() {
		return tenderCode == null ? "":tenderCode;
	}

	public void setTenderCode(String tenderCode) {
		this.tenderCode = tenderCode;
	}

	public Object getBidPrice() {
		Integer count = 0;
		if(bidPrice!=null) {
			String[] arr = bidPrice.split("\\|");
			for (String str : arr) {
				count += Integer.parseInt(str);
			}
		}
		return bidPrice == null ? "":count;
	}

	public void setBidPrice(String bidPrice) {
		this.bidPrice = bidPrice;
	}

	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
