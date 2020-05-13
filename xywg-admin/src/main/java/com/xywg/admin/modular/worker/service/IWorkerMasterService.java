package com.xywg.admin.modular.worker.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.worker.model.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人实名基础信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface IWorkerMasterService extends IService<WorkerMaster> {

	boolean insert(WorkerMasterVO entity);

	@Override
	boolean updateById(WorkerMaster entity);

	/**
	 * 导入
	 * @param entity
	 * @param i
	 * @return
	 */
	boolean importWorker(WorkerMasterImport entity,Integer i);

	/**
     * 获取工人列表
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String, Object>> selectWorkers(Page<WorkerMaster> page,Map<String, Object> map);

    /**
     * 获取工人列表(不分页)
     * @param map
     * @author yuanyang
     * @return
     */
    List<Map<String, Object>> selectWorkersList(Map<String, Object> map);


    /**
     * 根据证件类型和证件号查询工人基本信息
     * @param idCardNumber
     * @param idCardType
     * @author yuanyang
     * @return
     */
    WorkerMaster getWorkerByIdCard(String idCardNumber,Integer idCardType);


	/**
	 * 根据证件类型和证件号查询工人基本信息
	 * @param idCardNumber
	 * @param idCardType
	 * @author yuanyang
	 * @return
	 */
	List<Map<String, Object>> searchWorker(String idCardNumber,Integer idCardType);

    /**
     * 
     * @description 工人实名认证
     * @author chupp
     * @date 2018年5月28日
     * @param workerMaster
     *
     */
	void authPerson(WorkerMaster workerMaster);

	/**
	 * APP工人申请加入班组
	 * 2018年5月29日
	 *下午3:55:08
	 *@author wangshibo
	 *
	 */
	boolean addClass(AppAddClassDto addClassDto);


	/**
	 * 
	 * @description 人脸录入
	 * @author chupp
	 * @date 2018年5月29日
	 * @param workerMaster
	 *
	 */
	void input(WorkerMaster workerMaster);

	/**
	 * 工人同意加入班组
	 */
	void workerAgreeAddTeam(Integer id);

	/**
	 * 工人拒绝加入班组
	 */
	void workerDisagreeAddTeam(Integer id);

	/**
	 * 班组长同意加入班组
	 */
	void teamAgreeWorkerAdd(Integer id);

	/**
	 * 班组长拒绝加入班组
	 */
	void teamDisagreeWorkerAdd(Integer id);

	/**
	 * 
	 * @description 人脸图片查找信息
	 * @author chupp
	 * @date 2018年5月30日
	 * @param projectCode
	 * @param images
	 * @param sourceType
	 * @return 
	 *
	 */
	WorkerMaster findFace(String projectCode, String images, int sourceType);

	/**
	 * app工人进退场
	 * 2018年5月30日
	 *上午11:26:31
	 *@author wangshibo
	 *
	 */
	void changePersonJoinStatus(AppPersonJoinStatusDto personJoinStatusDto);

	/**
	 * 根据证件类型和证件编号更新
	 */
	void  updateByIdCardTypeAndIdCardNumber(WorkerMaster workerMaster);

	/**
	 * 根据用户ID 修改设备编号
	 */
	void  updateEquipmentById(Map<String,String> map);




	/**
	 * 
	 * @description 录入人脸、项目编号依赖关系（包含百度、上海大学）
	 * @author chupp
	 * @date 2018年5月30日
	 * @param workerId
	 * @param projectCode
	 *
	 */
	void inputFaceAndProjectCode(String workerId, String projectCode);

	/**
	 * 获取工人详细信息
	 * @param idCardType 证件类型
	 * @param idCardNumber 证件号
	 * @author yuanyang
	 * @return
	 */
	AppWorkerMasterDto getById(Integer idCardType, String idCardNumber);

	/**
	 * 获取工人详细信息
	 * @param projectCode 项目编号
	 * @param idCardType 证件类型
	 * @param idCardNumber 证件号
	 * @author yuanyang
	 * @return
	 */
	AppWorkerMasterDto v1111GetById(String projectCode ,Integer idCardType, String idCardNumber);

	/**
	 * 根据项目id班组编号查询出人员列表
	 * xieshuaishuai
	 */
	List<Map<String,Object>> getPersonListByTeamCode(Map<String,Object> map,Page page);

	/**
	 * 班组长邀请工人加入班组（app）
	 * 2018年6月5日
	 *下午2:19:58
	 *@author wangshibo
	 *
	 */
	boolean invitationAddTeam(AppInvitationAddTeamDto addTeamDto);

	/**
	 * 查询项目下所有的工人
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getWorkerMasterByProjectCode(Page<Map<String,Object>> page,Map<String, Object> map);

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
	List<Map<String,Object>> v116GetAppWorkerMasterByProjectCode(String projectCode,Integer pageNo , Integer pageSize);

	/**
	 * 获取累发工资参数
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getWorkerMoneyTable(Map<String, Object> map);

	/**
	 * 根据手机号查询工人信息
	 * @param cellPhone
	 * @author yuanyang
	 * @return
	 */
	WorkerMaster  getByCellPhone(String cellPhone);

	/**
	 * hujingyun
	 * 根据工人ID  删除工人基本信息以及登录信息
	 * @param map
	 * @edit yuanyang
	 */
	void  deleteWorkerInfoAndLoginInfo(Map<String,Object> map);

	/**
	 * 修改工种
	 * 2018年6月19日
	 *下午2:24:43
	 *@author wangshibo
	 *
	 */
	void updateWorkKind(AppUpdateWorkerKindDto appUpdateWorkerKindDto);

	/**
	 * 
	 * @description 录入本地人脸库
	 * @author chupp
	 * @date 2018年6月21日
	 * @param workerId
	 * @param projectId
	 * @param idImage
	 * @param name
	 * @param actionType
	 * @return
	 *
	 */
	boolean insertLocalFace(String workerId, String projectId, String idImage, String name, String actionType);

	/**
	 * 新增工人资格证书
	 * @param appAddCertificationsDto
	 * @return
	 */
	boolean addCertifications(AppAddCertificationsDto appAddCertificationsDto);

    /**
     * 导入
     * @param excelFile
     * @param request
     * @param response
     * @return
     */
//    String excelUpload(MultipartFile excelFile, HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 获取所有工人 不分公司
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getAll(Page<Map<String, Object>> page, Map<String, Object> map);

	/**
	 * 
	 * @description 获取实名制工人数据（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月4日
	 *
	 */
	void savePersonnelFromSMZYC(Map<String, String> myc);


/*    *//**
     *@param m 
     * @Description: 发送劳务通工人数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/11 14:54
     */
   /* boolean getPersonFromLabor(List<Long> ids, Map<String, String> m);*/

	/**
	 *@param m
	 * @Description: 发送劳务通工人数据到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/11 14:54
	 */
	List<Long> getPersonFromLabor(Map<String, String> m);
	/**
	 * 导入
	 * @param excelFile
	 * @param request
	 * @param response
	 * @return
	 */
	String Import(MultipartFile excelFile,HttpServletRequest request) throws Exception;

	/**
	 * 
	 * @description 获取实名制工人数据（南通）
	 * @author chupp
	 * @param mnt 
	 * @date 2018年7月26日
	 *
	 */
	void savePersonnelFromSMZNT(Map<String, String> mnt);
	/**
	 * 导出
	 * @param res
	 * @param req
	 * @param params
	 */
	void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);
	/**
	 * 
	 * @description 二维码消息推送
	 * @author chupp
	 * @date 2018年7月31日
	 * @param messageType
	 * @param idCardType
	 * @param idCardNumber
	 *
	 */
	void sendMessageFromQr(String messageType, String idCardType, String idCardNumber);

	/**
	 * 班组长拉工人
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamId
	 */
	void addWorkerByQr(String projectCode, Integer idCardType, String idCardNumber, Integer teamId);

	/**
	 *
	 * @description 人脸对比
	 * @author chupp
	 * @date 2018年8月2日
	 * @param workerMaster
	 * @return
	 *
	 */
	Object compareFace(WorkerMaster workerMaster);

	/**
	 * 根据项目id班组编号查询出人员列表
	 * 蔡伟
	 */
    List<Map<String,Object>> getPersonListByTeamCodeNoPage(Map<String, Object> map);
    
    /**
     * 发送工人图片到实名制
     * @param ids
     * @param m
     * @return
     */
/*    boolean getPersonImageFromLabor(int number, Map<String, String> m);*/
	boolean getPersonImageFromLabor(Map<String, String> m);
    
    /**
     * 发送工人图片到实名制
     * @param ids
     * @param m
     * @return
     */
//    boolean getPersonImageToLabor(int number, Map<String, String> m); 
    
    
    
    /**
     * 查询权限内所能看到的人员
     * @param map
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryWorkerByPosition(Map<String, Object> map,Page page);
    
    
	/**
	 * 获取项目人员
	 * @param projectCode
	 * @return
	 */
	List<WorkerMaster> getWorkerListByProjectCode(String projectCode);

	/**
	 * 查询工人详情 及 其项目下的进场卡
	 * @param idCardNumber
	 * @param idCardType
	 * @param projectCode
	 * @return
	 */
	WorkerMaster getWorkerByProjectWorker(String idCardNumber, Integer idCardType, String projectCode);

	/**
	 * 下发人员信息
	 * @param entity
	 */
	void dispatchUser(WorkerMaster entity);


	void deletes(List<String> ids);
	
	
    /**
     *@param m 
     * @Description: 发送景澜数据
     *@Author duanfen
     *@Date 2017/7/03 
     */
    boolean sendPersonToSmzForJl(Map<String, String> m);

    void addWorkerMaster(List<Object> addList);

	/**
	 * 根据社会统一信用代码和id查询数据
	 * @param organizationCode
	 * @param id
	 * @return
	 */
    List<WorkerMaster> queryWorkerMasterById(String organizationCode, Long id);
}
