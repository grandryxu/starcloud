package com.xywg.admin.modular.worker.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.message.model.Message;
import com.xywg.admin.modular.smz.model.PersonMo;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.worker.model.AppAddCertificationsDto;
import com.xywg.admin.modular.worker.model.AppUpdateWorkerKindDto;
import com.xywg.admin.modular.worker.model.AppWorkerMasterDto;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人实名基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface WorkerMasterMapper extends BaseMapper<WorkerMaster> {
    /**
     * 获取工人基本信息列表
     *
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String, Object>> selectWorkers(@Param("page") Page<WorkerMaster> page, @Param("map") Map<String, Object> map);

    /**
     * 通过id获取固定格式 智慧工地
     * @param id
     * @return
     */
    List<Map<String, Object>> findById(long id);

    /**
     * 根据证件号码查询工人呢 默认取第一条
     * @param idCardNumber
     * @return
     */
    WorkerMaster getWorkerByIdCardNumber(String idCardNumber);

    /**
     * 获取工人基本信息列表(不分页)
     *
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String, Object>> selectWorkersList(Map<String, Object> map);


    /**
     * 根据证件类型和证件号查询工人基本信息
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    WorkerMaster getWorkerByIdCard(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);

    /**
     * 根据证件类型和证件号查询工人基本信息
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    List<Map<String, Object>> searchWorker(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);

    WorkerMaster searchWorkerByIdCardTypeAndIdCardNumber(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);
    /**
     * app工人申请加入班组
     * 2018年5月29日
     *下午3:55:28
     *@author wangshibo
     *
     */
	int addClass(Message message);

    /**
     * 根据证件类型和证件号码查询这个人的工种代码
     * @param map
     * @return
     */
    String getWorkTypeById(@Param("map") Map<String,Object> map);

    /**
     * 根据证件类型和证件编号更新
     */
    void  updateByIdCardTypeAndIdCardNumber(WorkerMaster workerMaster);

    void  updateEquipmentById(Map<String,String> map);

    /**
     * 获取工人详细信息
     * @param idCardType
     * @param idCardNumber
     * @author yuanyang
     * @return
     */
    AppWorkerMasterDto getById(@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

    /**
     * 获取工人详细信息
     * @param projectCode
     * @param idCardType
     * @param idCardNumber
     * @author yuanyang
     * @return
     */
    AppWorkerMasterDto v1111GetById(@Param("projectCode") String projectCode,@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

    /**
     * 根据项目id班组编号查询出人员列表
     * xieshuaishuai
     */
    List<Map<String,Object>> getPersonListByTeamCode(@Param("map") Map<String,Object> map,@Param("page") Page page);

    /**
     * 班组长邀请工人加入班组
     * 2018年6月5日
     *下午2:33:07
     *@author wangshibo
     *
     */
  void  invitationAddTeam(Message message);

    /**
     * 查询项目下所有的工人
     * @param map
     * @return
     */
    List<Map<String,Object>> getWorkerMasterByProjectCode(@Param("page")Page<Map<String,Object>> page,@Param("map")Map<String, Object> map);

    /**
     * 查询项目下所有的工人(不带分页)
     * @param projectCode
     * @return
     */
    List<Map<String,Object>> getAppWorkerMasterByProjectCode(String projectCode);

    /**
     * 查询项目下所有的工人(带分页)
     * @param projectCode
     * @return
     */
    List<Map<String,Object>> v116GetAppWorkerMasterByProjectCode(@Param("projectCode") String projectCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    List<Map<String, Object>> getWorkerMoneyTable(Map<String, Object> map);

    /**
     * 根据手机号查询工人基本信息
     * @param cellPhone
     * @return
     */
    WorkerMaster getByCellPhone(String cellPhone);

    /**
     * @param @param  paramList
     * @param @return
     * @Description: TODO
     * @author cxl
     * @date 2017年12月26日 下午5:18:00
     */
	List<WorkerMaster> getValidUser(@Param("list") List<DeviceRecord> record);
	
	/**
	 * 查询班组长是否邀请过工人加入班组
	 * 2018年6月14日
	 *下午5:36:32
	 *@author wangshibo
	 *
	 */
	List<Map<String,Object>> searchInvitation(Message message);
	
	/**
	 * 查询工人是否申请加入过班组
	 * 2018年6月14日
	 *下午5:36:47
	 *@author wangshibo
	 *
	 */
	List<Map<String,Object>> searchAddClass(Message message);

    /**
     * 删除工人企业关系表
     * @param map
     * @author yuanyang
     */
    void delWorker(@Param("map") Map<String,Object> map);

    /**
     * 修改工种
     * 2018年6月19日
     *下午2:25:57
     *@author wangshibo
     *
     */
	void updateWorkKind(AppUpdateWorkerKindDto appUpdateWorkerKindDto);
	
	/**
	 * 查询工人是否在黑名单
	 * 2018年6月19日
	 *下午4:44:16
	 *@author wangshibo
	 *
	 */
	List<Map<String,Object>> searchWorkerBlack(@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

    /**
     * 新增工人资格证书
     * @param appAddCertificationsDto
     * @return
     */
    boolean addCertifications(AppAddCertificationsDto appAddCertificationsDto);

    /**
     * 根据userid获取登录设备
     */
    String getEquipmentByUserId(@Param("id")Long id);

    List<Map<String,Object>> getAll(@Param("page") Page<Map<String, Object>> page, @Param("map") Map<String, Object> map, @Param("switchType") SwitchType switchType);

    /**
     *@Description:发送劳务通工人数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/11 14:51
     */
 /*   List<PersonMo> getPersonFromLabor(List<Long> ids);*/
    List<PersonMo> getPersonFromLabor();

    /**
     * 更新工人手机号
     * @param oldPhone
     * @param newPhone
     */
    void updateWorkerPhone(@Param("oldPhone")String oldPhone,@Param("newPhone")String newPhone);

    /**
     * 根据项目id班组编号查询出人员列表
     * 蔡伟
     */
    List<Map<String,Object>> getPersonListByTeamCodeNoPage(@Param("map") Map<String, Object> map);
    
    
    /**
     * 查询需要用不人员的头像发送到实名制
     * @param number
     * @return
     */
    List<PersonMo> getPersonImageFromLabor(@Param("number")int number);
    
 
    List<PersonMo> getPersonImageToLabor(@Param("number")int number);

    List<PersonMo> getPersonImageToLabor();
    
    
    /**
     * 根据身份证修改工人的号码
     * @param phone
     * @param idCardNumber
     */
    void updateAccountByIdCardNumber(@Param("phone") String phone,@Param("idCardNumber")String idCardNumber,@Param("idCardType")int idCardType);
    
    /**
     * 查询权限内所能看到的人员
     * @param map
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryWorkerByPosition(@Param("map") Map<String, Object> map,@Param("page") Page page);
    
    /**
     * 查询工人是否存在
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    Long getWorkerCountByIdCard(@Param("idCardNumber")String idCardNumber,@Param("idCardType")int idCardType);
    
    
    List<WorkerMaster> getWorkerListByProjectCode(String projectCode);

    /**
     * 查询发送中如数据
     * @param id
     * @return
     */
	List<PersonMo> getZrUserInfo(@Param("id") Long id);
//	List<WorkerMaster> getZrUserInfo();

    /**
     * 查询工人详情 及 其项目下的进场卡
     * @param idCardNumber
     * @param idCardType
     * @param projectCode
     * @return
     */
    WorkerMaster getWorkerByProjectWorker(@Param("idCardNumber") String idCardNumber,@Param("idCardType") Integer idCardType,@Param("projectCode") String projectCode);

    /**
     * 修改人员照片信息
     * @param workerMaster
     */
    void updateWorkerFace(WorkerMaster workerMaster);
    
    /**
     * 查询景澜数据R
     * @param number
     * @return
     */
    List<PersonMo> sendPersonToSmzForJl(@Param("number")Long number);
    
    /**
     *@Description:发送劳务通工人数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/11 14:51
     */
    List<PersonMo> getPersonFromLaborToOne(@Param("id")Long id);

    Map<String, Long> selectIdByIdcard(@Param("idCardNumber") String idCardNumber);

    List<WorkerMaster> queryWorkerMasterById(@Param("organizationCode") String organizationCode, @Param("id") Long id);

}
