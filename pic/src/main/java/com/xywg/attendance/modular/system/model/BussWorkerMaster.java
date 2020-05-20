package com.xywg.attendance.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备下发命令
 * </p>
 *
 * @author z
 * @since 2019-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("buss_worker_master")
public class BussWorkerMaster extends Model<BussWorkerMaster> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;

	@TableField("id_card_type")
	private Integer idCardType;
    /**
     * 证件编号
     */
	@TableField("id_card_number")
	private String idCardNumber;




	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
