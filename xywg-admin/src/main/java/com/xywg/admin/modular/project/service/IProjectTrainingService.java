package com.xywg.admin.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.project.model.ProjectTraining;
import com.xywg.admin.modular.project.model.ProjectTrainingVo;
import com.xywg.admin.modular.wages.model.dto.PictureDto;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 项目中的培训记录 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
public interface IProjectTrainingService extends IService<ProjectTraining> {
    int insertProjectTrain(ProjectTrainingVo projectTrainingVo);

    List<Map<String,Object>> list(Page page,Map<String,Object> map);

    List<Map<String,Object>> getTrainRecordList(Integer idCardType, String idCardNumber,Page page);

    ProjectTrainingVo getOneById(Integer id);

    List<Map<String,Object>> getWorkerList(Integer id,Page page);

    /**
     * 
     * @description 获取实名制培训信息（盐城）
     * @author chupp
     * @param myc 
     * @date 2018年7月4日
     *
     */
	void saveTrainFromSMZYC(Map<String, String> myc);
	
	/**
	 * 
	 * @description 获取实名制培训附件信息（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月13日
	 *
	 */
	void saveTrainAttachmentFromSMZYC(Map<String, String> myc);

	Boolean getProjectTrainFromLabor(List<Long> ids, Map<String, String> m);

	Boolean getProjectTrainFileFromLabor(List<Long> ids, Map<String, String> m);

	Boolean getProjectTrainWorkerFromLabor(List<Long> ids, Map<String, String> m);

	void deleteByIds(Map<String, Object> map);

	/**
	 * 得到附件图片集
	 *
	 * @return
	 */
	PictureDto getAccessoryPicture(Long projectTrainId , String title , String tableName);

	List<Map<String,Object>> getProjectFileList(Long id);

	/**
	 * 
	 * @description 获取实名制培训信息（南通）
	 * @author chupp
	 * @param mnt 
	 * @date 2018年7月26日
	 *
	 */
	void saveTrainFromSMZNT(Map<String, String> mnt);

	/**
	 * 
	 * @description 获取实名制培训附件信息（南通）
	 * @author chupp
	 * @param mnt 
	 * @date 2018年7月26日
	 *
	 */
	void saveTrainAttachmentFromSMZNT(Map<String, String> mnt);
	
	/**
	 * 上传培训附件
	 * @param files
	 * @param settlementCodeId
	 * @return Object
	 */
    Object upload(MultipartFile[] files,String settlementCodeId);
	
}
