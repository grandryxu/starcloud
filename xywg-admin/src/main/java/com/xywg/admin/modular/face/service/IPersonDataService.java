package com.xywg.admin.modular.face.service;

import com.xywg.admin.modular.face.model.PersonData;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 人脸模型表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface IPersonDataService extends IService<PersonData> {

	/**
	 * 
	 * @param @param data 
	 * @Description: 保存PersonData
	 * @author cxl  
	 * @date 2018年1月4日 上午8:59:06
	 */
	void savePersonData(PersonData data,String userId);

}
